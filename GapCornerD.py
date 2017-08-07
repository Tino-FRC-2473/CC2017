import numpy as np
import matplotlib.pyplot as plt
import math
#from sweeppy import Sweep
import itertools

# TRUE=USING LIDAR, FALSE=USING TEXT FILES
# savedata.py saves data to "angle.txt" and "distance.txt"
USING_LIDAR = False
# SET TO TRUE DURING COMPETITION TO SUPPRESS ALL DEBUG MESSAGES TO SAVE TIME
NO_OUTPUT = False

DEBUG = True
DEBUG2 = True # High level debug messages that are very important

GRAPHXY = False

FT_TO_IN = 12;
IN_TO_CM = 2.54;

# DISTANCES THAT ARE TOO FAR OR TOO CLOSE THAT ARE CUT OUT OF ORIGINAL DATA (>= and <=)
LIDAR_D_SMALL_CUTOFF = 1
LIDAR_D_BIG_CUTOFF = 4500

# ANGLE LIDAR IS FACING RELATIVE TO THE ALLIANCE WALL
BEARING_TO_WALL = 90;

# ALL MEASUREMENTS TO BE IN CM
LIDAR_DISTANCE = 5*IN_TO_CM;                # FULL FIELD AXIS DISTANCE:     FROM LIDAR CENTER TO ALLIANCE WALL
ROBOT_IDEAL_Y = 13.5*FT_TO_IN*IN_TO_CM;     # ALLIANCE WALL AXIS DISTANCE:  FROM ROBOT CENTER TO BOILER CORNER
LIDAR_POSITION = 0                          # ALLIANCE WALL AXIS DISTANCE:  FROM LIDAR CENETER TO ROBOT CENETER

IDEAL_Y = ROBOT_IDEAL_Y - LIDAR_POSITION;   # ALLIANCE WALL AXIS DISTANCE:  FROM LIDAR CENTER TO BOILER CORNER

# ANGLE CORNER SHOULD BE FROM LIDAR
EXPECTED_THETA = (360 - math.degrees(math.atan2(IDEAL_Y, LIDAR_DISTANCE))+5)%360
if(not NO_OUTPUT and DEBUG):
        print("\nEXPECTED THETA: " + str(EXPECTED_THETA))



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
        if(not NO_OUTPUT and DEBUG2):
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

if(not NO_OUTPUT and DEBUG):
        plt.title("Raw X/Y")
        plt.scatter(xd, yd)
        plt.axhline(0)
        plt.axvline(0)
        plt.show()



# TRIM FULL LIDAR DATA TO WHERE THE ALLIANCE WALL AND THE BOILER ARE SUPPOSED TO BE

wallAngle = []
wallDistance = []
boilerAngle = []
boilerDistance = []

def within(a, startAngle, endAngle):
        if(startAngle<endAngle):
                return a>startAngle and a<endAngle
        if(startAngle>endAngle):
                return a<startAngle or a>endAngle

for i in range(1, len(originalAngle)):
        if (np.absolute(xd[i]) < 20 and yd[i] < -0.15*IDEAL_Y and yd[i] > -0.8*IDEAL_Y):
                wallAngle.append(originalAngle[i])
                wallDistance.append(originalDistance[i])
        elif (xd[i] < 0 and within(originalAngle[i]%360, (EXPECTED_THETA-18.5)%360, (EXPECTED_THETA-2.5)%360)):
                boilerAngle.append(originalAngle[i])
                boilerDistance.append(originalDistance[i])



# CONVERT TRIMMED LIDAR DATA TO X/Y

wallX = []
wallY = []
fullBoilerX = []
fullBoilerY = []

for i in range(len(wallDistance)):
        wallX.append(wallDistance[i]*np.cos(wallAngle[i]*np.pi/180.0))
        wallY.append(wallDistance[i]*np.sin(wallAngle[i]*np.pi/180.0))

for i in range(len(boilerDistance)):
        fullBoilerX.append(boilerDistance[i]*np.cos(boilerAngle[i]*np.pi/180.0))
        fullBoilerY.append(boilerDistance[i]*np.sin(boilerAngle[i]*np.pi/180.0))



# REMOVE THE LOW BOILER NOISE TO KEEP ONLY THE OUTER WALLS OF THE BOILER

# OUTER POINT TO MEASURE THE DISTANCE TO EACH POINT ON THE BOILER 
pX, pY = -IDEAL_Y/2.0, 0

if(not NO_OUTPUT and DEBUG):
        print("\n# of wall pts: " + str(len(wallX)))
        print("# of boiler pts: " + str(len(fullBoilerX)))
        plt.title("Wall/Boiler X/Y")
        plt.scatter(wallX, wallY, color="blue")
        plt.scatter(fullBoilerX, fullBoilerY, color="green")
        plt.scatter(pX, pY, color="red")
        plt.axhline(0)
        plt.axvline(0)
        plt.show()

def getDistance(x1, y1, x2, y2):
        return np.sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) )

fullDistances = []
for i in range(0, len(fullBoilerX)):
        fullDistances.append(getDistance(pX, pY, fullBoilerX[i], fullBoilerY[i]))

LOW_PERCENTILE = 3
HIGH_PERCENTILE = 40

lowDistance = np.percentile(fullDistances, LOW_PERCENTILE)
highDistance = np.percentile(fullDistances, HIGH_PERCENTILE)
if(not NO_OUTPUT and DEBUG):
        print("\nDISTANCE PERCENTILES\n  " + str(LOW_PERCENTILE) + "%: " + str(lowDistance) + "\n  " + str(HIGH_PERCENTILE) + "%: " + str(highDistance))

boilerX = []
boilerY = []
for i in range(len(fullBoilerX)):
        if(fullDistances[i] >= lowDistance and fullDistances[i] <= highDistance):
                boilerX.append(fullBoilerX[i])
                boilerY.append(fullBoilerY[i])

if(not NO_OUTPUT and DEBUG):
        print("\n# of cut boiler pts: " + str(len(boilerX)))



# CORNER DETECTION

wallSlope, wallIntercept = np.polyfit(wallX, wallY, 1)
boilerSlope, boilerIntercept = np.polyfit(boilerX, boilerY, 1)
if(not NO_OUTPUT and DEBUG2):
        print("\nSLOPES\n  Wall: " + str(wallSlope) + "\n  Boiler: " + str(boilerSlope))
        print("INTERCEPTS\n  Wall: " + str(wallIntercept) + "\n  Boiler:" + str(boilerIntercept))

x = (boilerIntercept - wallIntercept)/(wallSlope-boilerSlope)
y = wallSlope*x + wallIntercept

if(not NO_OUTPUT and DEBUG):
        print("\nCorner:")
        print(x, y)
        plt.title("With Corner")
        plt.scatter(wallX, wallY, color="green")
        plt.scatter(boilerX, boilerY, color="red")
        plt.scatter(x, y, color="blue")
        plt.axhline(0)
        plt.axvline(0)
        plt.savefig("CornerPlot.png")

'''
back = 38.1 #distance between lidar to back of robot in cm
side = 38.1 #disance between lidar to side of robot in cm
width = 76.2 #width of robot
blue = True #whether or not we are on blue 

def isclose(a, b, rel_tol=1e-09, abs_tol=0.0):
    	return abs(a-b) <= max(rel_tol * max(abs(a), abs(b)), abs_tol)

def aligned(direction, corner_distance):
		side_diff = width/2 - side
		in_to_cm = 2.54 #used to convert inches to centimeters
		y= (42/(2**.5)*in_to_cm)-back #aligned y-position of lidar
		x = (162*in_to_cm)-side_diff #aligned x-position of lidar
		angle = math.degrees(math.atan(y/x)) #aligned angle
		print (cornerX[0])
		distance = (x**2 + y**2)**0.5 #aligned distance
		print (cornerY[0])
		if (blue):
				return isclose(direction, 270+angle, abs_tol=0.0001) and isclose(corner_distance, distance, abs_tol=0.0001)
		else:
				return isclose(direction, 90-angle, abs_tol=0.0001) and isclose(corner_distance, distance, abs_tol=0.0001)

index = 0

if(blue):
        print(aligned(270 + math.degrees(math.atan(cornerY[0]/cornerX[0])),(cornerX[0]**2 + cornerY[0]**2)**0.5))
else:
        print(aligned(90 - math.degrees(math.atan(cornerY[0]/cornerX[0])),(cornerX[0]**2 + cornerY[0]**2)**0.5))

ang = math.degrees(math.atan(cornerY[0]/cornerX[0]))
if ang < 0:
        ang = ang + 360
dist = (cornerX[0]**2 + cornerY[0]**2)**0.5
print(ang)
print(dist)

cornerPlotX = []
cornerPlotY = []

for i, a in enumerate(angle):
        if (a >= (ang-20)) and (a <= (ang+20)):
                   cornerPlotX.append(distance[i]*np.cos(angle[i]*np.pi/180.0))
                   cornerPlotY.append(distance[i]*np.sin(angle[i]*np.pi/180.0))

plt.scatter(cornerPlotX,cornerPlotY)
plt.show()                   
'''