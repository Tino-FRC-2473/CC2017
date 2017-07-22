from sweeppy import Sweep
import matplotlib.pyplot as plt
import itertools
import numpy as np

angle = []
distance = []

angleFile = open("angle.txt","w+")
distanceFile = open("distance.txt", "w+")

SMALL_CUTOFF = 1
BIG_CUTOFF = 3000

BAR_MIN = 3
BAR_MAX = 20



# GET RAW LIDAR DATA AND ADD TO "angle" and "distance" arrays

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

                    # CUT OUT TOO BIG AND TOO LITTLE DISTANCE VALUES
                    if(distanceReading > SMALL_CUTOFF and distanceReading < BIG_CUTOFF):
                        angle.append(dataSample[0]/1000.0)
                        distance.append(dataSample[1])
                    
                break
            first = False

        sweep.stop_scanning()

dLen = len(distance) # length of the data



# CONVERT POLAR TO CARTESIAN

xd = []
yd = []

for i in range(dLen):
        xd.append(distance[i]*np.cos(angle[i]*np.pi/180.0))
        yd.append(distance[i]*np.sin(angle[i]*np.pi/180.0))

barIdxs = []

for i, dist in enumerate(distance):
        if dist >= BAR_MIN and dist <= BAR_MAX:
                barIdxs.append(angle[i])

print("before outlier removal")
print(barIdx)
barIdxs = rejectOutliers(barIdxs)
print("after outlier removal")
print(barIdxs)

def rejectOutliers(data, m=2):
    return data[abs(data - np.mean(data)) < m * np.std(data)]



# RECORD DATA IN FILES

for i in angle:
        angleFile.write(str(float(i)))
        angleFile.write("\n")

for i in distance:          
        distanceFile.write(str(float(i)))
        distanceFile.write("\n")

angleFile.close()
distanceFile.close()



# GRAPH DATA USING MATPLOTLIB

plt.scatter(xd, yd)
plt.axhline(0)
plt.axvline(0)
plt.show()
