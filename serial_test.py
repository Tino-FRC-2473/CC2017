import itertools
import time
import serial

def loopGetData():
        print("\nBEGIN INFINITE LOOP")
        with serial.Serial('/dev/ttyUSB0', timeout=15) as ser:
                print("after with")
                ser.write(str(ord("D")) + str(ord("S")))
                print("after write")
                print(ser.read(10))
                
loopGetData()


def getData(i):
        with open("scans.csv", "a") as f:
                print(".")
                f.write("NEW GETDATA")
                for scan in i:
                        f.write("\nscan" + "\n")
                        s = scan[0]
                        for dataSample in s:
                                f.write(str(dataSample[0]/1000.0) + " " + str(dataSample[1]) + "\n")

'''
sweep.set_motor_speed(1)
sweep.set_sample_rate(2000)
sweep.start_scanning()
'''
