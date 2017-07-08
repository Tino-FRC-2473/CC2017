from sweeppy import Sweep
import itertools
import time
from threading import Thread

def loopGetData(sweep):
        print("\nBEGIN INFINITE LOOP")
        while(True):
                time.sleep(8)
                getData(itertools.islice(sweep.get_scans(), 3))
                

def getData(i):
        with open("scans.csv", "a") as f:
                print(".")
                f.write("NEW GETDATA")
                for scan in i:
                        f.write("\nscan" + "\n")
                        s = scan[0]
                        for dataSample in s:
                                f.write(str(dataSample[0]/1000.0) + " " + str(dataSample[1]) + "\n")


with Sweep('/dev/ttyUSB0') as sweep:
        sweep.set_motor_speed(1)
        sweep.set_sample_rate(2000)
        print("lidar connection being established")
        sweep.start_scanning()
        print("connection established")
        Thread(target=loopGetData(sweep)).start()

        
