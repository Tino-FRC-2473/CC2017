import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)
GPIO.setup(10, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
while(True):
    print(GPIO.input(10))
    

'''


GPIO.add_event_detect(10, GPIO.BOTH)
def my_callback():
    print("detect")
GPIO.add_event_callback(10, my_callback)
'''
