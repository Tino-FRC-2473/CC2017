from sweeppy import Sweep
import itertools
import time
import RPi.GPIO as GPIO
import csv

s = time.time()
c = 0
MOTOR_SPEED = 3

def writeCSV(arr):
    with open('scans.csv', 'a') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=' ',
                                quotechar='|', quoting=csv.QUOTE_MINIMAL)
        [str(i) for i in arr]
        spamwriter.writerow(arr)

def getData(i):
        print(".")
        with open('scans.csv', 'a') as f:
            f.write("NEW GETDATA")
        it = 0.0
        for scan in i:
                with open('scans.csv', 'a') as f:
                    f.write("\nscan" + "\n")
                s = scan[0]
                for dataSample in s:
                        ang = dataSample[0]/1000.0
                        t = c+(ang/360)*(1.0/MOTOR_SPEED)+(it/MOTOR_SPEED)

                        arr = ["o", "c", "n"]
                        #arr.append(t)
                        #arr.append(ang)
                        #arr.append(dataSample[1])
                        
                        writeCSV(arr)

                it +=1
        
def main():
        open('scans.csv', 'w').close()
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(10, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)

        with Sweep('/dev/ttyUSB0') as sweep:
                sweep.set_motor_speed(MOTOR_SPEED)
                sweep.set_sample_rate(2000)
                
                print("lidar connection being established")
                sweep.start_scanning()
                print("connection established, waiting for button press")
                a = 0
                while(GPIO.input(10) == 0):
                        print(a)
                        a+=1
                        time.sleep(1)
                print("button pressed")
                
                while(GPIO.input(10) == 1):
                        print("\n", GPIO.input(10))
                        time.sleep(1)
                        c = time.time()-s
                        print(c)
                        getData(itertools.islice(sweep.get_scans(), 3))

main()
