from sweeppy import Sweep
import itertools
import time
from threading import Thread

angle = []
distance = []

def getData(i):
        for scan in i:
                print("scan")
                s = scan[0]
                for dataSample in s:
                        angle.append(dataSample[0]/1000.0)
                        distance.append(dataSample[1])
                        print(str(dataSample[0]/1000.0) + " " + str(dataSample[1]))
                break
        time.sleep(2)


with Sweep('/dev/ttyUSB0') as sweep:
        sweep.set_motor_speed(1)
        sweep.set_sample_rate(2000)
        sweep.start_scanning()
        t = Thread(target=getData(itertools.islice(sweep.get_scans(),3)))
        t.start()

        
