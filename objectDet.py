import numpy as np
import matplotlib.pyplot as plt
import csv
import math


robotDisplacement = [100,-100]

angle = []
distance = []

read = csv.reader(open("scans.csv",'r'))

c = 0
for r in read:
	c+=1
	if c>138:
		angle.append(float(r[1]))
		distance.append(float(r[2]))
	if(c>317):break

x = []
y = []
cutoffDistFromLidar = 0

for i in range(len(angle)):
	d = distance[i]
	a = angle[i]
	if(d>cutoffDistFromLidar):
		x.append(d*math.cos(a*math.pi/180))
		y.append(d*math.sin(a*math.pi/180))

origX = x
origY = y

cutoffAngleBetweenPoints = 10
changed = True

while changed:
	changed = False
	for i in range(1,len(x)-1):
		ldy = y[i]-y[i-1]
		ldx = x[i]-x[i-1]
		lan = math.atan(ldy/ldx)
		rdy = y[i+1]-y[i]
		rdx = x[i+1]-x[i]
		ran = math.atan(rdy/rdx)
		diff = abs(lan-ran)
		diff = min(diff,180-diff)
		if(diff>cutoffAngleBetweenPoints):
			x.remove(i)
			y.remove(i)
			changed=True

cutoffDistBetweenPoints = 20
objects = []
inObject = False
thisObject = []
minObjRange = 50
maxObjRange = 115

objCD = []

def dist(i):
	xD = x[i+1]-x[i]
	yD = y[i+1]-y[i]
	return math.sqrt(xD*xD+yD*yD)

for i in range(len(x)-1):
	if(dist(i)<cutoffDistBetweenPoints):
		if(not inObject):
			thisObject=[[x[i],y[i]],[x[i+1],y[i+1]]]
			inObject=True
		else:
			thisObject.append([x[i+1],y[i+1]])
	else:
		if(inObject):
			xD = thisObject[0][0]-thisObject[len(thisObject)-1][0]
			yD = thisObject[0][1]-thisObject[len(thisObject)-1][1]
			objectRange = math.sqrt(xD*xD+yD*yD)

			if(objectRange>minObjRange and objectRange<maxObjRange):
				objects.append(thisObject)
				xCenter, yCenter = thisObject[int(len(thisObject)/2)]
				dista = math.sqrt(xCenter*xCenter+yCenter*yCenter)
				ang = math.atan(yCenter/xCenter)*180/math.pi
				if(xCenter<0):ang+=180
				objCD.append([dista,ang])
			thisObject = []
			inObject = False

centX=[]
centY=[]
for i in objCD:
	centX.append(i[0]*math.cos(i[1]*math.pi/180))
	centY.append(i[0]*math.sin(i[1]*math.pi/180))

objectX = []
objectY = []


for obj in objects:
	for coord in obj:
		objectX.append(coord[0])
		objectY.append(coord[1])

plt.scatter(origX,origY,color='b')
plt.scatter(objectX,objectY,color='r')
plt.scatter([0],[0],color='g')
plt.scatter(centX,centY,color='y')
plt.show()




