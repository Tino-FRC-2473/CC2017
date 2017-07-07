from sweeppy import Sweep
import itertools

angle = []
distance = []

angleFile= open("angle.txt","w+")
distanceFile = open("distance.txt", "w+")

#Target: approx degree of corner
TARGET = 315
#angle buffer of search either way
TARGETBUFFER = 179

startAngle = TARGET-TARGETBUFFER
endAngle = TARGET+TARGETBUFFER
if(startAngle<0):startAngle+=360
if(endAngle>360):endAngle-=360
adjust = 315-TARGET


def within(a):
        if(startAngle<endAngle):
                return a>startAngle and a<endAngle
        if(startAngle>endAngle):
                return a>startAngle or a<endAngle

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
print("Angle:")
print(angle)
print("\nDistance:")
print(distance)

for i in angle:
        angleFile.write(str(float(i)))
        angleFile.write("\n")

for i in distance:
        distanceFile.write(str(float(i)))
        distanceFile.write("\n")

angleFile.close()
distanceFile.close()
