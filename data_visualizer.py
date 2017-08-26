import csv 
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import matplotlib.patches as patches
import matplotlib.transforms as transforms
import numpy as np
import time

#arrays for csv data values
timestamp = []
angle = []
distance = []
gyroAngle = []

#scale graph according to xy values
scaleGraph = False

#tracking indexes of scans
lastAngle = 360
indexes = []
i = 0

#reading csv value
with open('scans.csv', newline='') as csvfile:
	dataReader = csv.reader(csvfile)
	first = True
	for row in dataReader:
		if i == 0 or i == 1:
			first = False
		else:
			timestamp.append(float(row[0]))
			a = float(row[1]) - float(row[3])
			if (a >= 360):
				a -= 360
			elif (a<0):
				a+=360
			angle.append(a)
			distance.append(float(row[2]))
			if (float(row[1]) - lastAngle < 0):
				gyroAngle.append(float(row[3]))
				indexes.append(i)
			lastAngle = float(row[1])
		i+=1
indexes.append(i)

#xy values
x = []
y = []

j = 1
index = 1

#scale graph
xmax = 0
xmin = 0
ymax = 0
ymin = 0

while index < len(angle):
	xdata = []
	ydata = []
	i = index
	while i < len(angle) and (i == index or (j<len(indexes) and i < indexes[j]-1)):
		dx = distance[i]*np.cos(angle[i]*np.pi/180.0)
		dy = distance[i]*np.sin(angle[i]*np.pi/180.0)
		xdata.append(dx)
		ydata.append(dy)
		if (scaleGraph):
			if dx > xmax:
				xmax = dx
			elif dx < xmin:
				xmin = dx
			if dy > ymax:
				ymax = dy
			elif dy < ymin:
				ymin = dy
		i+=1
	index = i
	j+=1
	x.append(xdata)
	y.append(ydata)

#setting up the plot
fig, ax = plt.subplots()
sct = ax.scatter(x[0],y[0],s=5,c='b', animated=True)
rect = patches.RegularPolygon((0,0),4,radius=25.5,orientation = (-gyroAngle[0]+45)*np.pi/180, facecolor="red",animated=True)
tri =  patches.RegularPolygon((0,0),3,radius=15, orientation=(-gyroAngle[0])*np.pi/180, facecolor = "white",animated=True)
ax.add_patch(rect)
ax.add_patch(tri)

#axes ranges from min to max values
if (scaleGraph):
	plt.xlim(xmin,xmax)
	plt.ylim(ymin,ymax)
#hardcoded xy axes ranges
else:
	plt.xlim(-250,250)
	plt.ylim(-250,250)

#main function for animation
def main():
	ani = animation.FuncAnimation(fig, updatePlot, frames=len(gyroAngle), interval = 1000, repeat=False,fargs=(sct,rect,tri,gyroAngle),blit=True)
	plt.show()

#function for updating animation
def updatePlot(i,sct,rect,tri,gyroAngle):
	sct = ax.scatter(x[i],y[i],s=5,c='b',animated=True)
	rect = patches.RegularPolygon((0,0),4,radius=25.5,orientation = (-gyroAngle[i]+45)*np.pi/180, facecolor="red",animated=True)
	tri =  patches.RegularPolygon((0,0),3,radius=15, orientation=(-gyroAngle[i])*np.pi/180, facecolor = "white",animated=True)
	ax.add_patch(rect)
	ax.add_patch(tri)
	return sct, rect, tri,

main()

	
