import numpy as np
import cv2
import math

#For distance
DIST_CONSTANT = 1000 #assigned a random number
#rectSize = 10; # set the rect size - Not know if this is needed

#For angle
#calc the constant
FIELD_OF_VIEW_RAD = 70.42 * math.pi / 180.0

#note: total not just half of screen
#edges always distorted
SCREEN_WIDTH = 0 # gets initialized
ANGLE_CONST = 0  # gets initialized

#calc angle
SCREEN_HEIGHT = 0 # gets initialized
'''global pinX;
global pinY;
pinX, pinY = 0, 0'''
pinDistToCenter = 0

# Intersection of two lines with L1 = (x1, y1) to (x2, y2)
#   						and  L2 = (x3, y3) to (x4, y4)
def intersect(x1, y1, x2, y2, x3, y3, x4, y4):
	if(((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4)) != 0 and ((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4)) != 0):
		return ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4)),((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4))
	return (0,0)

# Distance from rectangle given rectangle height
def calcDist(height):
	#the distance and size is inversely proportional
	#dist * rectSize = constant
	if(height != 0):
		return DIST_CONSTANT / height;
	return 0;

#Converts angle from radians to Degrees
def calcAngleDeg(x):
	return calcAngleRad(x) * 180.0 / math.pi

#Returns angle in Radians (Negative if turn left)
def calcAngleRad(x):
	pinDistToCenter = calcPinDist(x)
	if(ANGLE_CONST != 0):
		return math.atan(pinDistToCenter / ANGLE_CONST)
	return 0;

# Pin distance in pixels from center of screen
def calcPinDist(x):
	return (x - SCREEN_WIDTH / 2)#math.fabs(x - SCREEN_WIDTH / 2);

# Checks if two rectangles overlap given Rect1 = upper right (l1x. l1y) lower left (r1x, r1y)
#										 Rect2 = upper right (l2x, l2y) lower left (r2x, r2y)
def doOverlap(l1x, l1y, r1x, r1y, l2x, l2y, r2x, r2y):
	if (l1x > r2x or l2x > r1x):
		return False;
	if (r1y < l2y or r2y < l1y):
		return False;
	return True;

def find_marker(image):

	hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV) # convert BGR to HSV

	# Look for color Ranges
	'''lower_bound = np.array([0, 95 , 120])
	upper_bound = np.array([10, 255, 255])
	lower_bound2 = np.array([160, 95 , 120])
	upper_bound2 = np.array([180, 255, 255])
	mask_low = cv2.inRange(hsv, lower_bound, upper_bound)
	mask_high = cv2.inRange(hsv, lower_bound, upper_bound)
	mask = mask_low + mask_high'''

	#green thresholds
	#180, 17, 100
	#180, 1, 100
	low_green = np.array([50, 100.0, 80.0])
	high_green = np.array([92, 255, 244.6])
	mask = cv2.inRange(hsv, low_green, high_green)

	meme, contours, hierarchy = cv2.findContours(mask, 1, 2)


	if(len(contours) > 0):
		contours = sorted(contours, key=cv2.contourArea, reverse=True) #sort contours by areas
		ct = contours[0]
		i = 1
		ct2 = ct

		x2,y2,w2,h2 = cv2.boundingRect(ct2) # Get the bounding rectangles
		x1,y1,w1,h1 = cv2.boundingRect(ct)

		#Keep getting the next biggest rectangle if there are rectangles left and the rectangles overlap
		while (doOverlap(x1, y1, x1+w1, y1+h1, x2, y2, x2+w2, y2+h2) == True and i < len(contours)):
			ct2 = contours[i]
			i+=1
			x2,y2,w2,h2 = cv2.boundingRect(ct2)
			x1,y1,w1,h1 = cv2.boundingRect(ct)

		# If rectangles do not overlap
		if(doOverlap(x1, y1, x1+w1, y1+h1, x2, y2, x2+w2, y2+h2) != True):
			# Find whether the top or the bottom is closer to each other
			# which ever difference in the y values between the rectangles is less
			if(abs(y2-y1) < abs(y1+h1-(y2+h2))):
				h2 = h1-2*abs(y2-y1)
			else:
				#print y2
				diff = abs(y1+h1-(y2+h2))
				h2 = h1-2*diff
				y2 = y1+diff
				#print y2


				# Draw rectangle
			cv2.rectangle(image,(x2,y2),(x2+w2,y2+h2),(0,255,0),2)

			# Find lines from outer corners and peg position
			if(x1 < x2):
				cv2.line(image,(x1,y1),(x2+w2,y2+h2),(255,0,0),5)
				cv2.line(image,(x1,y1+h1),(x2+w2,y2),(255,0,0),5)
				(centx, centy) = intersect(x1, y1, x2+w2, y2+h2, x1, y1+h1, x2+w2, y2)
			else:
				cv2.line(image,(x2,y2),(x1+w1,y1+h1),(255,0,0),5)
				cv2.line(image,(x2,y2+h2),(x1+w1,y1),(255,0,0),5)
				(centx, centy) = intersect(x2, y2, x1+w1, y1+h1, x2, y2+h2, x1+w1, y1)

			pinX = centx
			pinY = centy

			cv2.circle(image, (centx, centy), 5, (360,255,0), 5)

			print("Angle: ", str(calcAngleDeg(centx)))
			print("Dist:  ", str(calcDist((h1+h2)/2.0)))
			cv2.putText(image, "ANGLE: " + str(calcAngleDeg(centx)), (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
    		cv2.putText(image, "DIST: " + str(calcDist((h1 + h2) / 2.0)), (50, 100), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)


		cv2.rectangle(image,(x1,y1),(x1+w1,y1+h1),(0,255,0),2)



	cv2.imshow("image", image)


# Camera
capture = cv2.VideoCapture(0)


_, image = capture.read()
#Initialize some constants
SCREEN_HEIGHT, SCREEN_WIDTH = image.shape[:2]
ANGLE_CONST = (SCREEN_WIDTH / 2.0) / math.tan(FIELD_OF_VIEW_RAD / 2.0);

while True:
	_, image = capture.read()
	#image = cv2.imread("/Users/anik/Google Drive/CHS/Robotics/CV/BuildSeasonSummer/test.png", 1)
	print image.shape
	find_marker(image)
	cv2.waitKey(1)

camera.release()
cv2.destroyAllWindows()
