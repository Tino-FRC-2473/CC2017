#ADD IMPORTS
import math
import cv2
import numpy as np
import os
#import CVClass

#CVClass.final_calcDistAng().runCV()

class PegDetector:

    

    def __init__(self):
        #for distance
        self.distance = -1
        self.DIST_CONSTANT = 3234.375; #
        #rectSize = 10; # set the rext size

        #For angle
        #calc the constant
        self.FIELD_OF_VIEW_RAD = 70.42 * math.pi / 180.0;
        #note: total not just half of screen
        #edges always distorted
        self.SCREEN_WIDTH = 0;
        self.ANGLE_CONST = 0;

        #calc angle
        self.angle = -1
        self.SCREEN_HEIGHT = 0;
        self.pinX = 0;
        self.pinY = 0;
        self.pinDistToCenter = 0;

        self.TOP = False
        self.BOTTOM = True
        # angle;

        #self.os.system('sudo sh camerasettings.sh')
        self.camera = cv2.VideoCapture(0)
        # camera = cv2.VideoCapture(1)
        _, frame = self.camera.read()
        self.SCREEN_HEIGHT, self.SCREEN_WIDTH = frame.shape[:2]
        self.ANGLE_CONST = (self.SCREEN_WIDTH / 2.0) / math.tan(self.FIELD_OF_VIEW_RAD / 2.0)



    def calcDist(self, length): #the length of the rectangle
        #the distance and size is inversely proportional
        #dist * rectSize = constant
        if(length > 0):
            return self.DIST_CONSTANT / length;
        return -1;



    """def getRectPos(y1, h1, y3, h2):
        rectOnBottom = False
        if(y1 < y3):
            
            if( ((y1 + h1) - (y3 + h2)) < (y3 - y1) ):
                #deltaH = (y1 + h1) - (y3 + h2)
                rectOnBottom = True
            else:
                #deltaH = y3 - y1
                rectOnBottom = False


            #lengthRight = h1 - 2 * deltaH
            #print "deltaH: " + str(deltaH) + ", h1: " + str(h1)
            #length = (h1 + lengthRight) / 2.0
            #if(length == (h1 - deltaH)):
            #print "length: " + str(length)
            #else:
            #    print "fail"
        #if sec rect is higher
        else:
            #deltaH = y1 - y3
            if( ((y3 + h2) - (y1 + h1)) < (y1 - y3) ):
                #deltaH = (y3 + h2) - (y1 + h1)
                rectOnBottom = True
            else:
                #deltaH = y1 - y3
                rectOnBottom = False

        return rectOnBottom"""


    def calcAngleDeg(self, pinX):
        return self.calcAngleRad(pinX) * 180.0 / math.pi

    def calcAngleRad(self, pinX):
        pinDistToCenter = self.calcPinDist(pinX)
        #returns it in radians
        return math.atan(pinDistToCenter / self.ANGLE_CONST)

    #helper method
    def calcPinDist(self, pinX):
        #SOMEHOW GET PINX and PINY
        return (pinX - self.SCREEN_WIDTH / 2);
        #return math.fabs(pinX - SCREEN_WIDTH / 2);

    #only need top left and bottom right!!
    def pinPosition(x1, y1, x2, y2, x3, y3, x4, y4):
        x = (x1 + x2 + x3 + x4) / 4.0;
        y = (y1 + y2 + y3 + y4) / 4.0;
        return (int(x), int(y))

    #must find and modify height beforehand!!!
    """def crossPinPos(x1, y1, w1, h1, x3, y3, w3, h3):
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

        return (int(x), int(y))"""

    def runCV(self):
        while True:
            _, frame = self.camera.read()

            hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

            # zero_red = np.array([0, 100, 100]);
            # low_red = np.array([10, 255, 255]);
            # high_red = np.array([160, 100, 100]);
            # pi_red = np.array([180, 255, 255]);

            # #make mask
            # maskLow = cv2.inRange(hsv, zero_red, low_red)
            # maskHigh = cv2.inRange(hsv, high_red, pi_red)
            # mask = maskLow + maskHigh

            #low_white = np.array([118 - 10,22.95 - 20,181.05 - 30]);
            #high_white = np.array([123 + 10, 22.95 + 20, 181.05 + 30]);
            #mask = cv2.inRange(hsv, low_white, high_white);

            #green thresholds
            #180, 17, 100
            #180, 1, 100
            #low_green = np.array([50, 50, 50])
            #high_green = np.array([90, 63.75, 255])
            low_green = np.array([75, 90.0, 100.0]) 
            high_green = np.array([87, 255, 229.0])

            # #make mask
            mask = cv2.inRange(hsv, low_green, high_green)

            #show mask
            cv2.imshow("Mask", mask)

            #find contours based on mask
            _, contour,_ = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

            #finds the two biggest rectangles
            max_area = 0
            mx,my,mw,mh = 0, 0, 0, 0

            secmax_area = 0
            sx,sy,sw,sh = 0, 0, 0, 0

            thirdmax_area = 0
            tx,ty,tw,th = 0, 0, 0, 0 

            modmx, modmy, modmw, modmh = mx, my, mw, mh
            modsx, modsy, modsw, modsh = sx, sy, sw, sh

            for cnt in contour:
                x,y,w,h = cv2.boundingRect(cnt)
                #cv2.rectangle(frame,(x,y),(x+w,y+h),(0,150,255),thickness=3)

                area = cv2.contourArea(cnt)
                #print str(area)

                if(area >= max_area):
                    thirdmax_area = secmax_area
                    secmax_area = max_area
                    max_area = area
                    
                    tx = sx
                    ty = sy
                    tw = sw
                    th = sh

                    sx = mx
                    sy = my
                    sw = mw
                    sh = mh

                    mx = x
                    my = y
                    mw = w
                    mh = h
                elif(area >= secmax_area):
                    thirdmax_area = secmax_area
                    secmax_area = area
                    
                    tx = sx
                    ty = sy
                    tw = sw
                    th = sh

                    sx = x
                    sy = y
                    sw = w
                    sh = h
                elif(area >= thirdmax_area):
                    thirdmax_area = area

                    tx = x
                    ty = y
                    tw = w
                    th = h



            sideCase = False

            #draw rectangles on two biggest green part found, draws green rectangles
            if(max_area > 0):
                cv2.rectangle(frame,(mx,my),(mx+mw,my+mh),(0,255,0),thickness=3)
                if(secmax_area > 0):
                    cv2.rectangle(frame,(sx,sy),(sx+sw,sy+sh),(0,0,255),thickness=3)

                    modmx, modmy, modmw, modmh = mx, my, mw, mh
                    modsx, modsy, modsw, modsh = sx, sy, sw, sh
                    #calculate the modified coordinates
                    
                    lengthThres = 0.905
                    if(sh < lengthThres * mh or mh < lengthThres * sh):

                        #if third rectangle exists
                        if(thirdmax_area > 0):
                            errorThres = 3
                            cv2.rectangle(frame,(tx,ty),(tx+tw,ty+th),(255,255,0),thickness=3)
                            

                            #check if it is in the secmax rect area (should be 100% of time)
                            if(tx >= sx-errorThres and tx+tw <= sx+sw+errorThres and
                                ty >= my-errorThres and ty+th <= my+mh+errorThres and
                                (ty <= sy+errorThres or ty+th >= sy+sh-errorThres)):
                                sideCase = True
                                
                                #third rect on top of the max rect
                                if(ty < sy):
                                    modsy = ty
                                    modsh = sy+sh-ty
                                #third rect is below the max rect
                                else:
                                    modsh = ty+th-sy

                            #check if it is in the max rect area
                            elif(tx >= mx-errorThres and tx+tw <= mx+mw+errorThres and
                                ty >= sy-errorThres and ty+th <= sy+sh+errorThres and
                                (ty <= my+errorThres or ty+th >= my+mh-errorThres)):
                                sideCase = True
                                
                                #third rect on top of the max rect
                                if(ty < my):
                                    modmy = ty
                                    modmh = my+mh-ty
                                #third rect is below the max rect
                                else:
                                    modmh = ty+th-my
                        

                        #if side case is not true yet
                        if(not sideCase):
                            if(mh > sh): #probs 100% of the time
                                #change sh
                                modsh = int(float(sw) / mw * mh)
                                sideCase = True
                            else:
                                #change mh
                                modmh = int(float(mw) / sw * sh)
                                sideCase = True
                            
                            print "smaller than threshold but not third rect"


                            """if(my < sy): #is my is higher up than sy (probably 100% of the time)
                                #change the modsh
                                modsh = int(calcLengthSideCase(my, mh, sy, sh) * 2 - mh)

                                if(sh / float(modsh) < .8):
                                    sideCase = True
                                
                            else:
                                #change the modmh
                                modmh = int(calcLengthSideCase(my, mh, sy, sh) * 2 - sh)

                                if(mh / float(modmh) < .8):
                                    sideCase = True"""
                        
                    #rectPos = getRectPos(my, mh, sy, sh)
                    #print "rectPos: " + rectPos

                    """if(modsh <= 0 or modmh <= 0):
                        print "modsh or modmh 0"
                    elif(sideCase):
                    #else:
                        cv2.putText(frame, "modmh: " + str(modmh) + ", modsh: " + str(modsh), (0, 200), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
                        if(rectPos == TOP):
                            cv2.rectangle(frame,(modsx,modsy),(modsx+modsw,modsy+modsh),(255,0,255),thickness=1)
                            cv2.rectangle(frame,(modmx,modmy),(modmx+modmw,modmy+modmh),(255,0,255),thickness=1)
                            print "top"


                        else:
                            modsy = sy + sh - modsh
                            modmy = my + mh - modmh
                            cv2.rectangle(frame,(modsx,modsy),(modsx+modsw,modsy+modsh),(255,0,255),thickness=1)
                            cv2.rectangle(frame,(modmx,modmy),(modmx+modmw,modmy+modmh),(255,0,255),thickness=1)
                            #cv2.putText(frame, "modmh: " + str(modmh) + ", modsh: " + str(modsh), (50, 200), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
                            print "bottom"

                        #distance = calcDistSideCase(my, mh, sy, sh)
                    """
                    cv2.putText(frame, "width: " + str(mw) + ", " + str(sw), (0, 150), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)

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
                        cv2.line(frame, (mx+mw, my), (sx, sy+sh), (0, 255, 255), thickness=5)"""


                    self.pinX, self.pinY = self.pinPosition(mx, my, mx+mw, my+mh, sx, sy, sx+sw, sy+sh)
                    cv2.circle(frame, (self.pinX, self.pinY), 1, (255, 0, 0), thickness=5)

                    #diagPinX, diagPinY = crossPinPos(modmx, modmy, modmw, modmh, modsx, modsy, modsw, modsh)
                    #cv2.circle(frame, (diagPinX, diagPinY), 1, (0, 0, 0), thickness=5)

            print "Side case:" + str(sideCase)
            if(sideCase):
                distance = self.calcDist((modmh + modsh)/2.0)
            else:
                distance = self.calcDist((mh + sh) / 2.0)

            angle = self.calcAngleDeg(self.pinX)


            cv2.putText(frame, "ANG: " + str(angle), (0, 50), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
            #cv2.putText(frame, "BANG: " + str(calcAngleDeg(diagPinX)), (0, 250), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
            cv2.putText(frame, "DIST: " + str(distance), (0, 100), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)

            #displays the lengths of the original rectangles
            cv2.putText(frame, "mh: " + str(mh) + ", sh: " + str(sh), (0, 300), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
            print "mh: " + str(mh) + ", sh: " + str(sh)
            print "modmh: " + str(modmh) + ", modsh: " + str(modsh)
            print "Distance: " + str(distance)
            print "Angle: " + str(angle)
            print "--------------------"


            cv2.imshow("Contours", mask)

            cv2.imshow("Frame", frame)

            cv2.waitKey(300)

        self.camera.release()
        cv2.destroyAllWindows()

foo = PegDetector()
foo.runCV()
