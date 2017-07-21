# import the necessary packages
import numpy as np
import cv2
import math

#For distance
DIST_CONSTANT = 1000; #assigned a random number
#rectSize = 10; # set the rext size

#For angle
#calc the constant
FIELD_OF_VIEW_RAD = 70.42 * math.pi / 180.0;
#note: total not just half of screen
#edges always distorted
SCREEN_WIDTH = 0; # gets initialized
ANGLE_CONST = 0;  # gets initialized

#calc angle
SCREEN_HEIGHT = 0; # gets initialized
global pinX;
global pinY;
pinX, pinY = 0, 0
pinDistToCenter = 0;

# angle;

def intersect(x1, y1, x2, y2, x3, y3, x4, y4):
	return ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4)),((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4))
def calcDist(height):
	#the distance and size is inversely proportional
	#dist * rectSize = constant
	if(height != 0):
		return DIST_CONSTANT / height;
	return 0;

def calcAngleDeg(x):
	return calcAngleRad(x) * 180.0 / math.pi

def calcAngleRad(x):
	pinDistToCenter = calcPinDist(x)
	#returns it in radians
	if(ANGLE_CONST != 0):

		return math.atan(pinDistToCenter / ANGLE_CONST)
	return 0;

#helper method
def calcPinDist(x):
	#SOMEHOW GET PINX and PINY
	#print(x)
	return (x - SCREEN_WIDTH / 2)#math.fabs(x - SCREEN_WIDTH / 2);

'''def pinPosition(x1, y1, x2, y2, x3, y3, x4, y4):
	x = (x1 + x2 + x3 + x4) / 4.0;
	y = (y1 + y2 + y3 + y4) / 4.0;
	return (int(x), int(y))'''


def doOverlap(l1x, l1y, r1x, r1y, l2x, l2y, r2x, r2y):

	if (l1x > r2x or l2x > r1x):
		return False;

	if (r1y < l2y or r2y < l1y):#(l1y < r2y or l2y < r1y):
		return False;

	return True;

def find_marker(image):

	hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV) # convert BGR to HSV

	lower_bound = np.array([0, 95 , 120])
	upper_bound = np.array([10, 255, 255])
	lower_bound2 = np.array([160, 95 , 120])
	upper_bound2 = np.array([180, 255, 255])

	mask_low = cv2.inRange(hsv, lower_bound, upper_bound)
	mask_high = cv2.inRange(hsv, lower_bound, upper_bound)
	mask = mask_low + mask_high

	meme, contours, hierarchy = cv2.findContours(mask, 1, 2)
	#print contours
	# find the contours in the edged image and keep the largest one;
	# we'll assume that this is our piece of paper in the image
	#ct

	if(len(contours) > 0):
		contours = sorted(contours, key=cv2.contourArea, reverse=True)
		ct = None
		ct = contours[0]
		i = 1
		#ct2 = contours[1]
		ct2 = None
		ct2 = ct


		x2,y2,w2,h2 = 0, 0, 0, 0
		x2,y2,w2,h2 = cv2.boundingRect(ct2)
		x1,y1,w1,h1 = 0, 0, 0, 0
		x1,y1,w1,h1 = cv2.boundingRect(ct)

		while (doOverlap(x1, y1, x1+w1, y1+h1, x2, y2, x2+w2, y2+h2) == True and i < len(contours)):#len(np.intersect1d(ct, ct2)) > 0 and i < len(contours):	#
			ct2 = contours[i]
			i+=1
			x2,y2,w2,h2 = cv2.boundingRect(ct2)
			x1,y1,w1,h1 = cv2.boundingRect(ct)


		#print i , " " , len(contours)

		if(doOverlap(x1, y1, x1+w1, y1+h1, x2, y2, x2+w2, y2+h2) != True):
			if(y2-y1 < y1+h1-(y2+h2)):
				h2 = h1-2*(y2-y1)
			else:
				h2 = h1-2*(y1+h1-(y2+h2))


			cv2.rectangle(image,(x2,y2),(x2+w2,y2+h2),(0,255,0),2)
			'''centx = 0
			if(x2 > x1):
				a1 = (x2-x1)/(1+h2/h1)
				centx = x1+a1#((x2+x2+w2)/2 + (x1+x1+w1)/2)/2
			else:
				a1 = (x1-x2)/(1+h2/h1)
				centx = x2+a1'''
			if(x1 < x2):
				cv2.line(image,(x1,y1),(x2+w2,y2+h2),(255,0,0),5)
				cv2.line(image,(x1,y1+h1),(x2+w2,y2),(255,0,0),5)
				#centy = ((y2+y2+h2)/2 + (y1+y1+h1)/2)/2
				(centx, centy) = intersect(x1, y1, x2+w2, y2+h2, x1, y1+h1, x2+w2, y2)
			else:
				cv2.line(image,(x2,y2),(x1+w1,y1+h1),(255,0,0),5)
				cv2.line(image,(x2,y2+h2),(x1+w1,y1),(255,0,0),5)
				#centy = ((y2+y2+h2)/2 + (y1+y1+h1)/2)/2
				(centx, centy) = intersect(x2, y2, x1+w1, y1+h1, x2, y2+h2, x1+w1, y1)
			pinX = centx
			pinY = centy
			cv2.circle(image, (centx, centy), 5, (360,255,0), 5)
			print("Angle: ", str(calcAngleDeg(centx)))
			print("Dist:  ", str(calcDist((h1+h2)/2.0)))



		'''cv2.putText(frame, "ANGLE: " + str(calcAngleDeg()), (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
		cv2.putText(frame, "DIST: " + str(calcDist((h1+h2) / 2.0)), (50, 100), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)'''
		#print "(", x2, ", ", y2 , ") " "(", x2+w2, ", ", y2+h2 , ") "


		#print "(", x1, ", ", y1 , ") " "(", x1+w1, ", ", y1+h1 , ") "

		cv2.rectangle(image,(x1,y1),(x1+w1,y1+h1),(0,255,0),2)



	cv2.imshow("image", image)
	'''big_area = 0;
	big_cent = (int(0), int(0))
	for c in contours:
		(x,y),radius = cv2.minEnclosingCircle(c)
		center = (int(x),int(y))
		radius = int(radius)
		if radius > big_rad:
			big_rad = radius
			big_cent = center


	cv2.circle(frame,big_cent,big_rad,(0,255,0),20)'''
	# compute the bounding box of the of the paper region and return it
	#return cv2.minAreaRect(contours)


capture = cv2.VideoCapture(0)
_, image = capture.read()
SCREEN_HEIGHT, SCREEN_WIDTH = image.shape[:2]
ANGLE_CONST = (SCREEN_WIDTH / 2.0) / math.tan(FIELD_OF_VIEW_RAD / 2.0);
while True:
	_, image = capture.read()
	#edged = image
	#marker = find_marker(image)
	find_marker(image)
	'''focalLength = (marker[1][0] * KNOWN_DISTANCE) / KNOWN_WIDTH

	inches = distance_to_camera(KNOWN_WIDTH, focalLength, marker[1][0])
	# draw a bounding box around the image and display it
	box = np.int0(cv2.BoxPoints(marker))
	#cv2.drawContours(image, [box], -1, (0, 255, 0), 2)
	cv2.putText(image, "%.2fft" % (inches / 12),
		(image.shape[1] - 200, image.shape[0] - 20), cv2.FONT_HERSHEY_SIMPLEX,
		2.0, (0, 255, 0), 3)'''



	cv2.waitKey(1)

camera.release()
cv2.destroyAllWindows()
