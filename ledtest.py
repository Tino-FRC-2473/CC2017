import RPi.GPIO as GPIO
import time

red = 21
white = 20

GPIO.setmode(GPIO.BCM)

GPIO.setup(red,GPIO.OUT)
GPIO.setup(white,GPIO.OUT)

GPIO.output(red,GPIO.LOW)
GPIO.output(white,GPIO.LOW)

try: 
	while 1:
		GPIO.output(red,GPIO.HIGH)
		GPIO.output(white,GPIO.LOW)
		time.sleep(0.5)
		GPIO.output(red,GPIO.LOW)
		GPIO.output(white,GPIO.HIGH)
		time.sleep(0.5)
except KeyboardInterrupt:
	GPIO.cleanup()