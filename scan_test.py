from sweeppy import Sweep
import itertools
import time
from threading import Thread

s = time.time()
c = 0
rotato = 3
open('scans.csv', 'w').close()
f = open("scans.csv", "a")
def getData(i):
        print(".")
        f.write("NEW GETDATA")
        it = 0.0
        for scan in i:
                f.write("\nscan" + "\n")
                s = scan[0]
                for dataSample in s:
                        ang = dataSample[0]/1000.0
                        t = c+(ang/360)*(1.0/rotato)+(it/rotato)
                        f.write(str(t) + ": " + str(ang) + " " + str(dataSample[1]) + "\n")
                it +=1
                
with Sweep('/dev/ttyUSB0') as sweep:
        sweep.set_motor_speed(rotato)
        sweep.set_sample_rate(2000)
        print("lidar connection being established")
        sweep.start_scanning()
        print("connection established")
        print("\nBEGIN INFINITE LOOP")
        while(True):
                time.sleep(1)
                c = time.time()-s
                print(c)
                getData(itertools.islice(sweep.get_scans(), 3))

        
