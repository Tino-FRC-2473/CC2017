from sweeppy import Sweep
import matplotlib.pyplot as plt
import itertools

angle = []
distance = []

angleFile= open("angle.txt","w+")
distanceFile = open("distance.txt", "w+")

barCutoff = 5

with Sweep('/dev/ttyUSB0') as sweep:
        sweep.set_motor_speed(2)
        sweep.set_sample_rate(1000)
        sweep.start_scanning()

        first = True
        for scan in itertools.islice(sweep.get_scans(),3):
            if(not first):
                s = scan[0]
                for dataSample in s:
                    ang = dataSample[0]/1000.0

                    if(within(angle)):
                        angle.append(ang)
                        distance.append(dataSample[1])
                break
            first = False

        sweep.stop_scanning()


l = len(distance)

xd = []
yd = []

for i in range(l):
        xd.append(distance[i]*np.cos(angle[i]*np.pi/180.0))
        yd.append(distance[i]*np.sin(angle[i]*np.pi/180.0))

plt.plot(xdata, ydata, 'r-', label='raw')
plt.show()

index = 0

for i, dist in enumerate(distance):
        if dist == barDistance:
                index = i

barAngle = angle[index]

#for i, ang in enumerate(angle):
#        angle[i] = angle[i] - barAngle
#        if angle[i] < 0:
#                angle[i] = angle[i] + 360


for i in angle:
        angleFile.write(str(float(i)))
        angleFile.write("\n")

for i in distance:
        distanceFile.write(str(float(i)))
        distanceFile.write("\n")

angleFile.close()
distanceFile.close()
