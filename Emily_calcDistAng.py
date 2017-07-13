#ADD IMPORTS
import math
import cv2
import numpy as np

#For distance
DIST_CONSTANT = 100; #assigned a random number
#rectSize = 10; # set the rext size

#For angle
#calc the constant
FIELD_OF_VIEW_RAD = 70.42 * math.pi / 180.0;
#note: total not just half of screen
#edges always distorted
SCREEN_WIDTH = 0;
ANGLE_CONST = 0;

#calc angle
SCREEN_HEIGHT = 0;
pinX = 0;
pinY = 0;
pinDistToCenter = 0;

# angle;

def calcDist(length): #the length of the rectangle
    #the distance and size is inversely proportional
    #dist * rectSize = constant
    if(length != 0):
        return DIST_CONSTANT / length;
    return 0;

def calcAngleDeg():
    return calcAngleRad() * 180.0 / math.pi

def calcAngleRad():
    pinDistToCenter = calcPinDist()
    #returns it in radians
    return math.atan(pinDistToCenter / ANGLE_CONST)

#helper method
def calcPinDist():
    #SOMEHOW GET PINX and PINY
    return math.fabs(pinX - SCREEN_WIDTH / 2);

#only need top left and bottom right!!
def pinPosition(x1, y1, x2, y2, x3, y3, x4, y4):
    x = (x1 + x2 + x3 + x4) / 4.0;
    y = (y1 + y2 + y3 + y4) / 4.0;
    return (int(x), int(y))


"""returns true if the rectangle overlaps given the x and y coordinates
of the top left and bottom right corners"""
"""def overlaps(x1, y1, x2, y2, x3, y3, x4, y4):
    #starting on rectangle 1 and going left to right, top to bottom
    #then rectangle 2
    #then 0 case

    #first rectangle
    #check x1, y1
    if((x3 < x1 and x1 < x4) #x1 is between x3 and x4
     and (y3 < y1 and y1 < y4)): #y1 is between y3 and y4
    	return True
    #check x2, y1
    elif((x3 < x2 and x2 < x4) #x2 is between x3 and
     and (y3 < y1 and y1 < y4)): #y1 is between y3 and y4
    	return True
    #check x1, y2
    elif((x3 < x1 and x1 < x4) #x1 is between x3 and x4
	 and (y3 < y2 and y2 < y4)): #y2 is between y3 and y4
    	return True
    #check x2, y2
    elif((x3 < x2 and x2 < x4) #x2 is between x3 and x4
     and (y3 < y2 and y2 < y4)): #y1 is between y3 and y4
    	return True

    #second rectangle
	#check x3, y3
    elif((x1 < x3 and x3 < x2) #x3 is between x1 and x2
     and (y1 < y3 and y3 < y2)): #y3 is between y1 and y2
    	return True
	#check x4, y3
    elif((x1 < x4 and x4 < x2) #x4 is between x1 and x2
     and (y1 < y3 and y3 < y2)): #y3 is between y1 and y2
    	return True
	#check x3, y4
    elif((x1 < x3 and x3 < x2) #x3 is between x1 and x2
     and (y1 < y4 and y4 < y2)): #y4 is between y1 and y2
    	return True
    #check x4, y4
    elif((x1 < x4 and x4 < x2) #x4 is between x1 and x2
     and (y1 < y4 and y4 < y2)): #y4 is between y1 and y2
    	return True

    #the zero cases
    #first rect crosses vertically
    elif((x3 < x1 and x1 < x4) #x1 is between x3 and x4
     and (x3 < x2 and x2 < x4) #x2 is between x3 and x4
     and (y1 < y3 and y3 < y2) #y3 is between y1 and y2
     and (y1 < y4 and y4 < y2)): #y4 is between y1 and y2
    	return True
    #first rect crosses horizontally
    elif((x1 < x3 and x3 < x2) #x3 is between x1 and x2
     and (x1 < x4 and x4 < x2) #x4 is between x1 and x2
     and (y3 < y1 and y1 < y4)) #y1 is between y3 and y4
     and (y3 < y2 and y2 < y4)): #y2 is between y3 and y4
		return True

	#if alll the above fails
	return False
"""
camera = cv2.VideoCapture(0)
_, frame = camera.read()
SCREEN_HEIGHT, SCREEN_WIDTH = frame.shape[:2]
ANGLE_CONST = (SCREEN_WIDTH / 2.0) / math.tan(FIELD_OF_VIEW_RAD / 2.0);

while True:
    _, frame = camera.read()

    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    # zero_red = np.array([0, 100, 100]);
    # low_red = np.array([10, 255, 255]);
    # high_red = np.array([160, 100, 100]);
    # pi_red = np.array([180, 255, 255]);
    #make mask
    # maskLow = cv2.inRange(hsv, zero_red, low_red)
    # maskHigh = cv2.inRange(hsv, high_red, pi_red)
    # mask = maskLow + maskHigh

    low_white = np.array([0,0,100]);
    high_white = np.array([360, 10, 255]);
    mask = cv2.inRange(hsv, low_white, high_white);
    
    # #green thresholds
    # low_green = np.array([40, 100, 100])
    # high_green = np.array([80, 255, 255])

    # #make mask
    # mask = cv2.inRange(hsv, low_green, high_green)

    #show mask

    #find contours based on mask
    _, contour,_ = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    #finds the two biggest rectangles
    max_area = 0
    mx,my,mw,mh = 0, 0, 0, 0

    secmax_area = 0
    sx,sy,sw,sh = 0, 0, 0, 0


    for cnt in contour:
        x,y,w,h = cv2.boundingRect(cnt)
        if(w * h > max_area):
            secmax_area = max_area
            max_area = w * h

            sx = mx
            sy = my
            sw = mw
            sh = mh

            mx = x
            my = y
            mw = w
            mh = h



    #draw rectangles on two biggest green part found
    if(max_area > 0):
        cv2.rectangle(frame,(mx,my),(mx+mw,my+mh),(0,255,0),thickness=20)
        if(secmax_area > 0):
            cv2.rectangle(frame,(sx,sy),(sx+sw,sy+sh),(0,0,255),thickness=20)
            pinX, pinY = pinPosition(mx, my, mx+mw, my+mh, sx, sy, sx+sw, sy+sh)
            cv2.circle(frame, (pinX, pinY), 1, (255, 0, 0), thickness=5)


    cv2.putText(frame, "ANGLE: " + str(calcAngleDeg()), (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
    cv2.putText(frame, "DIST: " + str(calcDist((mh + sh) / 2.0)), (50, 100), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)


    cv2.waitKey(1)

    cv2.imshow("Mask", mask)

    cv2.imshow("Frame", frame)


camera.release()
cv2.destroyAllWindows()
