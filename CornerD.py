import numpy as np
import matplotlib.pyplot as plt
import math
from sweeppy import Sweep
import itertools

#Smooth on XY Graph
XYSMOOTH = 0
#Smooth on Derivative
DSMOOTH = 1

GRAPHXY = True
#Graph Slope Change Totals
GRAPHST = False
#Graph Derivative
GRAPHD = False
#CornerST is current method
CORNERST = False

IN_PER_FT = 12;
CM_PER_IN = 2.54;

LIDAR_D_SMALL_CUTOFF = 1
LIDAR_D_BIG_CUTOFF = 3000

BEARING_TO_WALL = 80;
LIDAR_DISTANCE = 4; # IN CM, CENTER OF LIDAR TO ALLIANCE WALL

ROBOT_IDEAL_X = 13*IN_PER_FT*CM_PER_IN; # IN CM, CENTER OF THE ROBOT TO THE CORNER
LIDAR_Y = 4*CM_PER_IN; # IN CM, CENTER OF ROBOT TO CENTER OF LIDAR ON X LINE

LIDAR_POSITION = 5
IDEAL_X = ROBOT_IDEAL_X - LIDAR_POSITION; # CM X DISTANCE FROM THE CORNER THAT THE LIDAR SHOULD BE ALIGNED TO

EXPECTED_THETA = math.degrees(math.atan2(IDEAL_X,LIDAR_Y))
THETA_MARGIN = 30

print(EXPECTED_THETA)

# GET RAW LIDAR ANGLES AND DISTANCES

originalAngle = []
originalDistance = []

with Sweep('/dev/ttyUSB0') as sweep:
    sweep.set_motor_speed(2)
    sweep.set_sample_rate(1000)
    sweep.start_scanning()

    first = True
    for scan in itertools.islice(sweep.get_scans(),3):
        if(not first):
            s = scan[0]
            for dataSample in s:
                distanceReading = dataSample[1]

                if(distanceReading > LIDAR_D_SMALL_CUTOFF and distanceReading < LIDAR_D_BIG_CUTOFF):
                    originalAngle.append(dataSample[0]/1000.0)
                    originalDistance.append(distanceReading)
            break
        first = False

    sweep.stop_scanning()



# CONVERT LIDAR DATA TO X/Y AND GRAPH

xd = []
yd = []

for i in range(len(originalDistance)):
        xd.append(originalDistance[i]*np.cos(originalAngle[i]*np.pi/180.0))
        yd.append(originalDistance[i]*np.sin(originalAngle[i]*np.pi/180.0))

plt.scatter(xd, yd)
plt.axhline(0)
plt.axvline(0)
plt.show()



# TRIM LIDAR DATA TO AROUND WHERE THE BOILER CORNER IS EXPECTED TO BE

angle = []
distance = []

for i in range(len(originalAngle)):
        if(originalAngle[i] >= EXPECTED_THETA-THETA_MARGIN and originalAngle[i] <= EXPECTED_THETA+THETA_MARGIN):
                angle.append(originalAngle[i])
                distance.append(originalDistance[i])

xd = []
yd = []

for i in range(len(distance)):
        xd.append(distance[i]*np.cos(angle[i]*np.pi/180.0))
        yd.append(distance[i]*np.sin(angle[i]*np.pi/180.0))

plt.scatter(xd, yd)
plt.axhline(0)
plt.axvline(0)
plt.show()

#STUFF

# if(endAngle<startAngle):
#         for i in range(0,len(angle)):
#                 if(angle[i]>startAngle):
#                         angle = angle[i:]+angle[:i]
#                         distance = distance[i:]+distance[:i]
#                         break

# for i in range(0,len(angle)):
#         angle[i] = angle[i]+adjust
#         if(angle[i]>360):angle[i]-=360
#         if(angle[i]<0):angle[i]+=360

# l = len(distance)



# smooth = XYSMOOTH

# xdata = []
# ydata = []


# for i in range(smooth,l-smooth):
#         sumX = 0
#         sumY = 0
#         for x in range(i-smooth,i+smooth+1):
#                 sumX+=xd[x]
#                 sumY+=yd[x]
#         xdata.append(sumX/(2*smooth+1))
#         ydata.append(sumY/(2*smooth+1))

# l = len(xdata)

# if GRAPHXY:plt.scatter(xd, yd)

# cartD = []
# d = 0;
# dist = []

# for i in range(0,l-1):
#         cX = xdata[i+1]-xdata[i]
#         cY = ydata[i+1]-ydata[i]
#         s=cY/cX
#         aTan = np.arctan(s)*180.0/np.pi
#         if i>0:
#                 last = cartD[len(cartD)-1]
#                 curDif = abs(aTan-last)
#                 altDif = abs(aTan+180-last)
#                 if(altDif<curDif):aTan += 180
#         cartD.append(aTan)
#         d += np.sqrt(cX*cX+cY*cY)
#         dist.append(d)


# def inVal(i):
#         return 0.5*(cartD[i]+cartD[i+1])*(dist[i+1]-dist[i])

# smooth = DSMOOTH

# length = len(dist)

# sD = []

# sumd = 0


# for i in range(0,smooth-1):
#                 sumd+=inVal(i)



# if(not DSMOOTH == 0):
#         for i in range(0,length):
#                 start = 0;
#                 if(i>smooth):
#                         sumd-=inVal(i-smooth-1)
#                         start = i-smooth
#                 end = length-1
#                 if(i<(length-smooth)):
#                         sumd+=inVal(i+smooth-1)
#                         end = i+smooth
#                 startX = dist[0]
#                 if(start>0):startX=dist[start]
#                 endX = dist[length-1]
#                 if end<length:
#                         endX = dist[end]
#                 totD = endX-startX
#                 sD.append(sumd/totD)

# if(DSMOOTH == 0):
#         sD = cartD

# if GRAPHD:
#         plt.plot(dist, cartD, 'r-', label='raw')
#         plt.plot(dist, sD, 'b-', label='smooth')

# slopeTotals = []
# sign = (sD[1]-sD[0])>0
# xSlope = []
# mx = 0

# thisSlopes = [sD[1]-sD[0]]
# maxThisSlope = 0
# maxCorner = 0

# for i in range(1,length-1):
#         thisSlope = sD[i+1]-sD[i]
#         thisSign = thisSlope>0
#         if(sign==thisSign):
#                 thisSlopes.append(thisSlope)
#                 if(abs(thisSlope)>maxThisSlope):
#                         maxThisSlope = abs(thisSlope)
#                         maxCorner = i+1
#         else:
#                 slopeTotals.append(sum(thisSlopes))
#                 aL = abs(sum(thisSlopes))
#                 if(aL>mx):
#                         mx = aL
#                 sign = thisSign
#                 xSlope.append(maxCorner)
#                 thisSlopes = [thisSlope]
#                 maxThisSlope = abs(thisSlope)
#                 maxCorner = i+1

# slopeTotals.append(sum(thisSlopes))
# aL = abs(sum(thisSlopes))
# if(aL>mx):
#         mx = aL
# xSlope.append(maxCorner)

# cornerX = []
# cornerY = []

# for i in range(0,len(slopeTotals)):
#         if(abs(slopeTotals[i]-CORNERDETECT)<CORNERBUFFER):
#                 cornerX.append(xdata[xSlope[i]])
#                 cornerY.append(ydata[xSlope[i]])


# plt.legend()
# plt.show()
# if CORNERST:plt.scatter(cornerX, cornerY)


# if GRAPHST:plt.plot(xSlope, slopeTotals, 'r-', label='raw')


# plt.xlabel('X')
# plt.ylabel('Y')
# plt.show()

# back = 38.1 #distance between lidar to back of robot in cm
# side = 38.1 #disance between lidar to side of robot in cm
# width = 76.2 #width of robot
# blue = True #whether or not we are on blue 

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
