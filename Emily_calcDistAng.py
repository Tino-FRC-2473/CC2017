#ADD IMPORTS
import math
import cv2
import numpy as np

#For distance
DIST_CONSTANT = 1; #assigned a random number
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

rectOnBottom = False;

# angle;

def calcDist(length): #the length of the rectangle
    #the distance and size is inversely proportional
    #dist * rectSize = constant
    if(length > 0):
        return DIST_CONSTANT / length;
    return -1;

#do check for whether this is needed outside calcDistSideCase
#TODO make variable names better
def calcDistSideCase(y1, h1, y3, h2):
    return calcDist(calcLengthSideCase(y1, h1, y3, h2))

#calculates the so called "average length"
#CAN MAKE THIS METHOD MORE EFFICIENT!!!
def calcLengthSideCase(y1, h1, y3, h2):
    deltaH = 0;
    length = 0;

    print "y1: " + str(y1) + ", y3:" + str(y3)

    #if first rect is higher than second rect
    if(y1 < y3):
        
        if( ((y1 + h1) - (y3 + h2)) < (y3 - y1) ):
            deltaH = (y1 + h1) - (y3 + h2)
            rectOnBottom = True
        else:
            deltaH = y3 - y1
            rectOnBottom = False


        lengthRight = h1 - 2 * deltaH
        #print "deltaH: " + str(deltaH) + ", h1: " + str(h1)
        length = (h1 + lengthRight) / 2.0
        #if(length == (h1 - deltaH)):
        print "length: " + str(length)
        #else:
        #    print "fail"
    #if sec rect is higher
    else:
        deltaH = y1 - y3
        if( ((y3 + h2) - (y1 + h1)) < (y1 - y3) ):
            deltaH = (y3 + h2) - (y1 + h1)
            rectOnBottom = True
        else:
            deltaH = y1 - y3
            rectOnBottom = False


        lengthLeft = h2 - 2 * deltaH
        #print "deltaH: " + str(deltaH) + ", h2: " + str(h2)
        length = (h2 + lengthLeft) / 2.0
        #if(length == (h2 - deltaH)):
        print "length: " + str(length)
        #else:
        #    print "fail"

    return length

def calcAngleDeg():
    return calcAngleRad() * 180.0 / math.pi

def calcAngleRad():
    pinDistToCenter = calcPinDist()
    #returns it in radians
    return math.atan(pinDistToCenter / ANGLE_CONST)

#helper method
def calcPinDist():
    #SOMEHOW GET PINX and PINY
    return (pinX - SCREEN_WIDTH / 2);
    #return math.fabs(pinX - SCREEN_WIDTH / 2);

#only need top left and bottom right!!
def pinPosition(x1, y1, x2, y2, x3, y3, x4, y4):
    x = (x1 + x2 + x3 + x4) / 4.0;
    y = (y1 + y2 + y3 + y4) / 4.0;
    return (int(x), int(y))

#must find and modify height beforehand!!!
def crossPinPos(x1, y1, w1, h1, x3, y3, w3, h3):
    ############# MAKE SURE THE RECT CASES WORK!!!
    #Case x1 is on the left side
    x = 0
    y = 0

    #uses similar triangles to find the coordinate of the x and y
    #using the heights to find the proportions
    proportionConstant = float(h3) + h1
    #x1 is left of x3
    if(x1 < x3):
        x = x1 + h1 * (x3 + w3 - x1) / proportionConstant
        y = y1 + h1 * (y3 + h3 - y1) / proportionConstant
    else:
        x = x3 + h3 * (x1 + w1 - x3) / proportionConstant
        y = y3 + h3 * (y1 + h1 - y3) / proportionConstant

    return (int(x), int(y))



#camera = cv2.VideoCapture(0)
camera = cv2.VideoCapture(0)
_, frame = camera.read()
SCREEN_HEIGHT, SCREEN_WIDTH = frame.shape[:2]
ANGLE_CONST = (SCREEN_WIDTH / 2.0) / math.tan(FIELD_OF_VIEW_RAD / 2.0)

while True:
    _, frame = camera.read()

    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    zero_red = np.array([0, 100, 100]);
    low_red = np.array([10, 255, 255]);
    high_red = np.array([160, 100, 100]);
    pi_red = np.array([180, 255, 255]);

    #make mask
    maskLow = cv2.inRange(hsv, zero_red, low_red)
    maskHigh = cv2.inRange(hsv, high_red, pi_red)
    mask = maskLow + maskHigh

    #low_white = np.array([118 - 10,22.95 - 20,181.05 - 30]);
    #high_white = np.array([123 + 10, 22.95 + 20, 181.05 + 30]);
    #mask = cv2.inRange(hsv, low_white, high_white);

    #green thresholds
    #180, 17, 100
    #180, 1, 100
    #low_green = np.array([90, 2.55, 255])
    #high_green = np.array([90, 63.75, 255])
    # low_green = np.array([50, 100.0, 80.0]) 
    # high_green = np.array([92, 255, 244.6])

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
        if(w * h >= max_area):
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



    #draw rectangles on two biggest green part found, draws green rectangles
    if(max_area > 0):
        cv2.rectangle(frame,(mx,my),(mx+mw,my+mh),(0,255,0),thickness=5)
        if(secmax_area > 0):
            cv2.rectangle(frame,(sx,sy),(sx+sw,sy+sh),(0,0,255),thickness=5)

            modmx, modmy, modmw, modmh = mx, my, mw, mh
            modsx, modsy, modsw, modsh = sx, sy, sw, sh
            #calculate the modified coordinates
            if(my < sy): #is my is higher up than sy (probably 100% of the time)
                #change the modsh

                modsh = int(calcLengthSideCase(my, mh, sy, sh) * 2 - mh)
            else:
                #change the modmh
                modmh = int(calcLengthSideCase(my, mh, sy, sh) * 2 - sh)



            if(modsh <= 0 or modmh <= 0):
                cv2.putText(frame, "modmh: " + str(modmh) + ", modsh: " + str(modsh), (50, 200), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
            elif(not rectOnBottom):
                cv2.rectangle(frame,(modsx,modsy),(modsx+modsw,modsy+modsh),(255,0,255),thickness=3)
                cv2.rectangle(frame,(modmx,modmy),(modmx+modmw,modmy+modmh),(255,0,255),thickness=3)
                cv2.putText(frame, "DIST test: " + str(calcDistSideCase(my, mh, sy, sh)), (50, 150), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
            else:
                modsy = sy + sh - modsh
                modmy = my + mh - modmh

                cv2.rectangle(frame,(modsx,modsy),(modsx+modsw,modsy+modsh),(255,0,255),thickness=3)
                cv2.rectangle(frame,(modmx,modmy),(modmx+modmw,modmy+modmh),(255,0,255),thickness=3)
                cv2.putText(frame, "DIST test: " + str(calcDistSideCase(my, mh, sy, sh)), (50, 150), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
                cv2.putText(frame, "modmh: " + str(modmh) + ", modsh: " + str(modsh), (50, 200), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)


            #draws the new rectangles, purple


            """#draws diagonal lines, yellow lines
            #top left corner to bottom right corner
            #max rect is on left of secmax rect
            if(mx < sx):
                #top left corner to bottom right corner
                cv2.line(frame, (mx, my), (sx+sw, sy+sh), (0, 255, 255), thickness=5)
                #top right corner to bottm left corner
                cv2.line(frame, (sx+sw, sy), (mx, my+mh), (0, 255, 255), thickness=5)
            else: #secmax rect is on left of max rect
                #top left corner to bottom right corner
                cv2.line(frame, (sx, sy), (mx+mw, my+mh), (0, 255, 255), thickness=5)
                #top right corner to bottm left corner
                cv2.line(frame, (mx+mw, my), (sx, sy+sh), (0, 255, 255), thickness=5)


            pinX, pinY = pinPosition(mx, my, mx+mw, my+mh, sx, sy, sx+sw, sy+sh)
            cv2.circle(frame, (pinX, pinY), 1, (255, 0, 0), thickness=5)

            diagPinX, diagPinY = crossPinPos(mx, my, mw, mh, sx, sy, sw, sh)
            cv2.circle(frame, (diagPinX, diagPinY), 1, (0, 0, 0), thickness=5)"""


    cv2.putText(frame, "ANGLE: " + str(calcAngleDeg()), (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
    cv2.putText(frame, "DIST: " + str(calcDist((mh + sh) / 2.0)), (50, 100), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
    #displays the lengths of the original rectangles
    cv2.putText(frame, "Length (mh): " + str(mh) + ", Length (sh): " + str(sh), (50, 300), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)



    cv2.imshow("Mask", mask)

    cv2.imshow("Frame", frame)

    cv2.waitKey(1000)

camera.release()
cv2.destroyAllWindows()
