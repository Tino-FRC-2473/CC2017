import csv 
import numpy as np
import time

timestamp = []
angle = []
distance = []
gyroAngle = []
encoderValue = []

def isFloat(value):
	try:
		float(value)
		return True
	except ValueError:
		return False

with open('scans.csv', newline='') as csvfile:
	dataReader = csv.reader(csvfile)
	first = True
	for row in dataReader:
		if not row[0]:
			continue
		elif not isFloat(row[0]):
			continue
		else:
			timestamp.append(float(row[0]))
			angle.append(float(row[1]))
			distance.append(float(row[2]))
			if bool(row[3]):
				gyroAngle.append(float(row[3]))
			if bool(row[4]):
				encoderValue.append(float(row[4]))

encoderSlope = []

for i in range(len(encoder)):
	if i == 0:
		encoderSlope.append(encoderValue[i+1]-encoderValue[i])
	elif i == len(encoder) - 1:
		encoderSlope.append(encoderValue[i]-encoderValue[i-1])
	else:
		encoderSlope.append((encoderValue[i+1]-encoderValue[i-1])/2)

x = []
y = []

while index < len(angle):
	xdata = []
	ydata = []
	i = index
	while i < len(angle) and (i == index or angle[i]-angle[i-1] > 0):
		dx = distance[i]*np.cos(angle[i]*np.pi/180.0)
		dy = distance[i]*np.sin(angle[i]*np.pi/180.0)
		xdata.append(dx)
		ydata.append(dy)
		i+=1
	index = i
	x.append(xdata)
	y.append(ydata)

	#use speed to calculate change of xy position of points in terms of distance
	#use difference of gyro angle to calculate change of xy position in terms of rotation