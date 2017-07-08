from sweeppy import Sweep
import matplotlib.pyplot as plt
import itertools
import numpy as np

IN_TO_CM = 2.54
FT_TO_IN = 12

expectedFeet = 5
booksLength = 1.5

targetAngle = 0
angleRange = .4*180.0/np.pi*np.arctan(booksLength/2.0 / expectedFeet)

angle = []
distance = []

with Sweep('/dev/ttyUSB0') as sweep:
        sweep.set_motor_speed(1)
        sweep.set_sample_rate(2000)
        sweep.start_scanning()

        first = True
        for scan in itertools.islice(sweep.get_scans(),3):
                if(not first):
                        s = scan[0]
                        for dataSample in s:
                                if (dataSample[0]/1000.0 < targetAngle+angleRange or dataSample[0]/1000.0 > 360-targetAngle - angleRange):
                                        angle.append(dataSample[0]/1000.0)
                                        distance.append(dataSample[1])
                        break
                first = False

        sweep.stop_scanning()

x = []
y = []

for i in range(len(distance)):
        x.append(distance[i]*np.cos(angle[i]*np.pi/180.0))
        y.append(distance[i]*np.sin(angle[i]*np.pi/180.0))

measured = float(sum(x))/len(x)
expected = expectedFeet*12*2.54
pError = 100.0*np.absolute(measured-expected)/expected


with open("test"+str(expectedFeet)+".txt","a") as text_file:
        text_file.write("Measured: " + str(measured) + "\n")
        text_file.write("Expected: " + str(expected) + "\n")
        text_file.write("% Error: " + str(pError) + "\n\n")

print("Testing for " + str(expectedFeet) + "ft")
print("Measured: " + str(measured))
print("Expected: " + str(expected))
print("% Error: " + str(pError))

plt.scatter(x, y)
plt.axhline(0)
plt.axvline(0)
plt.show()


