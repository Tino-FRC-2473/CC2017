#import statements below, add any others if you need
import cv2
import numpy as np


camera = cv2.VideoCapture(0)

while True:
	#Grab image, get basic data, figure out center coords
	grabbed, frame = camera.read()

	#print len(frame)
	#cv2.imshow(frame)
	#convert to hsv
	hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

	#Code threshold + mask for appropriate color values
	#these thresholds are the inverse values

	zero_red = np.array([0, 100, 100]);
	low_red = np.array([10, 255, 255]);
	high_red = np.array([160, 100, 100]);
	pi_red = np.array([180, 255, 255]);

	#make mask
	maskLow = cv2.inRange(hsv, zero_red, low_red)
	maskHigh = cv2.inRange(hsv, high_red, pi_red)
	mask = maskLow + maskHigh

	#Show mask so user can see threshold in action
	#cv2.imshow("res", mask)

	cv2.waitKey(1)

	#find contours based on mask
	_, contours,_ = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
	img = cv2.drawContours(frame, contours, -1, (0, 255, 0), 2)

	#Find appropriate cv2 methods to call and code to find circle, and draw it on the frame
	#You will only need the circle of greatest area and it should atleast be of some size
	#Use moments to extract information you need
	max_radius = 0
	center_of_max = (0, 0)

	for cnt in contours:
		(x, y), radius = cv2.minEnclosingCircle(cnt)
		
		center = (int(x), int(y))
		radius = int(radius)
		cv2.drawContours(mask, contours[0], -1, (0,255,0), 3)

		if(radius > max_radius):
			max_radius = radius
			center_of_max = center


	if(max_radius > 0):
		cv2.circle(frame, center_of_max, max_radius, (0, 255, 0), thickness=20)

	#display final frame below
	cv2.imshow("FRAME", frame)
	cv2.imshow("Mask", mask)
	
	#cv2.waitKey(1)

#Closes instances of videocapture object, removes live feed
camera.release()
cv2.destroyAllWindows()
