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

floatBarIdxs = []
intBarIdxs = []
barIdxs = []

for i, dist in enumerate(distance):
        if dist >= BAR_MIN and dist <= BAR_MAX:
                floatBarIdxs.append(angle[i])

# print("floats")
# print(floatBarIdxs)

for i in range(0, len(floatBarIdxs)):
        intBarIdxs.append(int(floatBarIdxs[i]*1000))
# print("int before")
# print(intBarIdxs)

def rejectOutliers(data):
    m = 2
    u = np.mean(data)
    s = np.std(data)
    filtered = [e for e in data if (u - 2 * s < e < u + 2 * s)]
    return filtered

intBarIdxs = rejectOutliers(intBarIdxs)

# print("int after")
# print(intBarIdxs)

for i in range(0, len(intBarIdxs)):
        barIdxs.append(intBarIdxs[i]/1000.0)

# print("floats")
print(barIdxs)


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