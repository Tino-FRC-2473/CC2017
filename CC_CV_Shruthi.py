import numpy as np
import cv2
import math

#find screenWidth: getImage then getWidth
#find midX, midY: getImage then get center point
#find pegX, pegY: get centers of rectangles, average points

CONSTANT = 0.1; # use real calibration
FOV = 70.42 # degrees
camera = cv2.VideoCapture(0)

def calcDistance(rectHeight, caliConstant) :
	#hd=k
	if(rectHeight > 0):
		return (caliConstant / rectHeight)

def calcAngle(fov, screenWidth, midX, midY, pegX, pegY) :
	bisectAngle = FOV / 2.0 #bisect the field of view angle
	halfScreen = screenWidth / 2.0
	vertLength = halfScreen / math.tan(bisectAngle)

	distFromCentToPeg = math.sqrt(((pegX-midX) ** 2) + ((pegY - midY) ** 2))
	bearing = math.atan(vertLength / distFromCentToPeg) * (180/math.pi)

	return bearing

while True:
	_, frame = camera.read()


	hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
	# lower_green = np.array([0, 127, 76])
	# upper_green = np.array([0, 221, 51])
	#
	# mask = cv2.inRange(hsv, lower_green, upper_green)
	zero_red = np.array([0, 100, 100])
	low_red = np.array([10, 255, 255])
	high_red = np.array([160, 100, 100])
	pi_red = np.array([180, 255, 255])

    #make mask
	maskLow = cv2.inRange(hsv, zero_red, low_red)
	maskHigh = cv2.inRange(hsv, high_red, pi_red)
	mask = maskLow + maskHigh

	vari, contours, hier = cv2.findContours(mask, 1, 2)

	#imgWidth = frame.getWidth()

	maxArea = 0
	secondArea = 0

	x1, y1, w1, h1 = 0, 0, 0, 0
	x2, y2, w2, h2 = 0, 0, 0, 0

	indexMax = 0
	for c in range(0, len(contours)) :
		x, y, w, h = cv2.boundingRect(contours[c])
		if(w*h >= maxArea):
			indexMax = c
			maxArea = w * h
			x1 = x
			y1 = y
			w1 = w
			h1 = h

	for c in range(0, len(contours)) :
		x, y, w, h = cv2.boundingRect(contours[c])
		if(w*h >= secondArea and c != indexMax):
			# bRX = x + w
			# bRY = y + h
			#
			# cX = (x+)

			# if((x >= x1 and  x <= x1 + w1) or (bRX >= x1 and bRX <= x1))
			secondArea = w * h
			x2 = x
			y2 = y
			w2 = w
			h2 = h

	cv2.rectangle(frame, (x1, y1), (x1+w1, y1+h1), (255, 0, 0), thickness=5)
	cv2.rectangle(frame, (x2, y2), (x2+w2, y2+h2), (0, 255, 0), thickness=6)
	pegx = (x1 + x2)/2.0
	pegy = (y1 + y2 + h2)/2.0

	dist1 = calcDistance(h1, CONSTANT)
	dist2 = calcDistance(h2, CONSTANT)

	#print "dist1:" + str(dist1)
	#print "dist2:" + str(dist2)

	distance = (dist1 + dist2) / 2.0

	SCREENHEIGHT, SCREENWIDTH = frame.shape[:2];

	angle = calcAngle(FOV, SCREENWIDTH, 0, 0, pegx, pegy)

	print "DISTANCE: " + str(distance)
	print "ANGLE: " + str(angle)

	cv2.imshow("Mask", mask)
	cv2.imshow("Frame", frame)
	cv2.waitKey(1)
