import numpy as np
import matplotlib.pyplot as plt
import math
#from sweeppy import Sweep
import itertools

USING_LIDAR = False;
DEBUG = True;

#Smooth on XY Graph
XYSMOOTH = 0
#Smooth on Derivative
DSMOOTH = 1

GRAPHXY = True
#Graph Slope Change Totals
GRAPHST = True
#Graph Derivative
GRAPHD = True
#CornerST is current method
CORNERST = True

IN_PER_FT = 12;
CM_PER_IN = 2.54;

LIDAR_D_SMALL_CUTOFF = 1
LIDAR_D_BIG_CUTOFF = 6000

BEARING_TO_WALL = 90;
LIDAR_DISTANCE = 5*CM_PER_IN; # IN CM, CENTER OF LIDAR TO ALLIANCE WALL

ROBOT_IDEAL_Y = 13.5*IN_PER_FT*CM_PER_IN; # IN CM, CENTER OF THE ROBOT TO THE CORNER
LIDAR_X = 4*CM_PER_IN; # IN CM, CENTER OF ROBOT TO CENTER OF LIDAR ON Y LINE

LIDAR_POSITION = 0
IDEAL_Y = ROBOT_IDEAL_Y - LIDAR_POSITION; # CM Y DISTANCE FROM THE CORNER THAT THE LIDAR SHOULD BE ALIGNED TO

EXPECTED_THETA = (360 - math.degrees(math.atan2(IDEAL_Y, LIDAR_DISTANCE))-7)%360
THETA_MARGIN = 7
print("THETA", EXPECTED_THETA-THETA_MARGIN, EXPECTED_THETA+THETA_MARGIN)

#Angle of corner we want to detect(for boiler corner set to 45)
CORNERDETECT = 45
#Buffer we allow for corner so if cornerdetect=45 cornerbuffer=5 we look for 40-50 deg
CORNERBUFFER = 5

def within(a, startAngle, endAngle):
        if(startAngle<endAngle):
                return a>startAngle and a<endAngle
        if(startAngle>endAngle):
                return a<startAngle or a>endAngle

# GET RAW LIDAR ANGLES AND DISTANCES

originalAngle = []
originalDistance = []

if(USING_LIDAR):
    with Sweep('/dev/ttyUSB0') as sweep:
        sweep.set_motor_speed(1)
        sweep.set_sample_rate(2000)
        sweep.start_scanning()

        first = True
        for scan in itertools.islice(sweep.get_scans(),3):
            if(not first):
                s = scan[0]
                for dataSample in s:
                    distanceReading = dataSample[1]

                    if(distanceReading > LIDAR_D_SMALL_CUTOFF and distanceReading < LIDAR_D_BIG_CUTOFF):
                        originalAngle.append((dataSample[0]/1000.0 - BEARING_TO_WALL)%360)
                        originalDistance.append(distanceReading)
                break
            first = False

        sweep.stop_scanning()
        print("data collecting done")
else: 
    goodInd = []
    fx = open("angle.txt","r")
    counter = 0
    for line in fx:
            ang = float(line)
            originalAngle.append(ang - BEARING_TO_WALL)
            goodInd.append(counter)
            counter+=1

    fy = open("distance.txt","r")
    counter = 0
    for line in fy:
            if(len(goodInd)==0):
                    break
            if(goodInd[0]==counter):
                    goodInd.pop(0)
                    originalDistance.append(float(line))
            counter+=1



# CONVERT RAW LIDAR DATA TO X/Y AND GRAPH

xd = []
yd = []

for i in range(len(originalDistance)):
        xd.append(originalDistance[i]*np.cos(originalAngle[i]*np.pi/180.0))
        yd.append(originalDistance[i]*np.sin(originalAngle[i]*np.pi/180.0))

if(DEBUG):
    plt.title("Raw X/Y")
    plt.scatter(xd, yd)
    plt.axhline(0)
    plt.axvline(0)
    plt.show()



# TRIM LIDAR DATA TO AROUND WHERE THE BOILER CORNER IS EXPECTED TO BE

# def polarDist(dist1, ang1, dist2, ang2):
#         return math.sqrt(dist1*dist1 + dist2*dist2 - 2*dist1*dist2*math.cos(math.radians(ang2-ang1)))

angle = []
distance = []
angleWall = []
distanceWall = []
angleBoiler = []
distanceBoiler = []
betweenDistances = []



for i in range(1, len(originalAngle)):
        # thisDist = polarDist(originalDistance[i], originalAngle[i], originalDistance[i-1], originalAngle[i-1])
        #if(within(originalAngle[i]%360, (EXPECTED_THETA-THETA_MARGIN)%360, (EXPECTED_THETA+THETA_MARGIN)%360+65)
                #and (len(betweenDistances) < 30 or thisDist > 10*sum(betweenDistances)/float(len(betweenDistances)) )
        #):
                #betweenDistances.append(thisDist)
        #        angle.append(originalAngle[i])
        #        distance.append(originalDistance[i])
        if (within(originalAngle[i]%360, (EXPECTED_THETA-THETA_MARGIN)%360, (EXPECTED_THETA-2)%360)):

                angleBoiler.append(originalAngle[i])
                distanceBoiler.append(originalDistance[i])
        elif (within(originalAngle[i]%360, (EXPECTED_THETA+1)%360, 275)):
                angleWall.append(originalAngle[i])
                distanceWall.append(originalDistance[i])
        

        #elif(within(originalAngle[i], (EXPECTED_THETA-THETA_MARGIN)%360, (EXPECTED_THETA+THETA_MARGIN)%360)):
            #print("cut ")
            #print(i)

#print(angleWall)

# CONVERT TRIMMED LIDAR DATA TO X/Y AND GRAPH

#xd = []
#yd = []
wallX = []
wallY = []
boilerX = []
boilerY = []

#for i in range(len(distance)):
        #xd.append(distance[i]*np.cos(angle[i]*np.pi/180.0))
        #yd.append(distance[i]*np.sin(angle[i]*np.pi/180.0))
for i in range(len(distanceWall)):
        wallX.append(distanceWall[i]*np.cos(angleWall[i]*np.pi/180.0))
        wallY.append(distanceWall[i]*np.sin(angleWall[i]*np.pi/180.0))

for i in range(len(distanceBoiler)):
        boilerX.append(distanceBoiler[i]*np.cos(angleBoiler[i]*np.pi/180.0))
        boilerY.append(distanceBoiler[i]*np.sin(angleBoiler[i]*np.pi/180.0))

if(DEBUG):
    plt.title("Cut X/Y")
    plt.scatter(wallX, wallY)
    plt.scatter(boilerX, boilerY)
    plt.axhline(0)
    plt.axvline(0)
    plt.show()

wallSlope, wallIntercept = np.polyfit(wallX, wallY, 1)
boilerSlope, boilerIntercept = np.polyfit(boilerX, boilerY, 1)
print("wall slope", wallSlope, "boiler slope", boilerSlope)
print("wall intercept", wallIntercept, "boiler intercept", boilerIntercept)

x = (boilerIntercept - wallIntercept)/(wallSlope-boilerSlope)
y = wallSlope*x + wallIntercept

if(DEBUG):
    print(x, y)
    plt.title("With Corner")
    plt.scatter(wallX, wallY)
    plt.scatter(boilerX, boilerY)
    plt.scatter(x, y, color="red")
    plt.axhline(0)
    plt.axvline(0)
    plt.show()


back = 38.1 #distance between lidar to back of robot in cm
side = 38.1 #disance between lidar to side of robot in cm
width = 76.2 #width of robot
blue = True #whether or not we are on blue 

# def isclose(a, b, rel_tol=1e-09, abs_tol=0.0):
#     	return abs(a-b) <= max(rel_tol * max(abs(a), abs(b)), abs_tol)

# def aligned(direction, corner_distance):
# 		side_diff = width/2 - side
# 		in_to_cm = 2.54 #used to convert inches to centimeters
# 		y= (42/(2**.5)*in_to_cm)-back #aligned y-position of lidar
# 		x = (162*in_to_cm)-side_diff #aligned x-position of lidar
# 		angle = math.degrees(math.atan(y/x)) #aligned angle
# 		print (cornerX[0])
# 		distance = (x**2 + y**2)**0.5 #aligned distance
# 		print (cornerY[0])
# 		if (blue):
# 				return isclose(direction, 270+angle, abs_tol=0.0001) and isclose(corner_distance, distance, abs_tol=0.0001)
# 		else:
# 				return isclose(direction, 90-angle, abs_tol=0.0001) and isclose(corner_distance, distance, abs_tol=0.0001)

# index = 0

# if(blue):
#         print(aligned(270 + math.degrees(math.atan(cornerY[0]/cornerX[0])),(cornerX[0]**2 + cornerY[0]**2)**0.5))
# else:
#         print(aligned(90 - math.degrees(math.atan(cornerY[0]/cornerX[0])),(cornerX[0]**2 + cornerY[0]**2)**0.5))

# ang = math.degrees(math.atan(cornerY[0]/cornerX[0]))
# if ang < 0:
#         ang = ang + 360
# dist = (cornerX[0]**2 + cornerY[0]**2)**0.5
# print(ang)
# print(dist)

# cornerPlotX = []
# cornerPlotY = []

# for i, a in enumerate(angle):
#         if (a >= (ang-20)) and (a <= (ang+20)):
#                    cornerPlotX.append(distance[i]*np.cos(angle[i]*np.pi/180.0))
#                    cornerPlotY.append(distance[i]*np.sin(angle[i]*np.pi/180.0))

# plt.scatter(cornerPlotX,cornerPlotY)
# plt.show()                   
