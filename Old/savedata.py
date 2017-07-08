from sweeppy import Sweep
import itertools

SMALL_CUTOFF = 1
BIG_CUTOFF = 3000

angleFile = open("angle.txt","w+")
distanceFile = open("distance.txt", "w+")

angle = []
distance = []

with Sweep('/dev/ttyUSB0') as sweep:
    sweep.set_motor_speed(1)
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

for i in angle:
    angleFile.write(str(float(i)))
    angleFile.write("\n")

for i in distance:          
    distanceFile.write(str(float(i)))
    distanceFile.write("\n")

angleFile.close()
distanceFile.close()
