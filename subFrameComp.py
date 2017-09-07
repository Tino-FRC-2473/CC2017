import numpy as np
import math

dTresh = 10
displacement = [0,-2]
dWithin = 2
robotRotation = 5*math.pi/180
adj = -robotRotation

def dist(i,j):
	xD = (i[0]-j[0])*(i[0]-j[0])
	yD = (i[1]-j[1])*(i[1]-j[1])
	return math.sqrt(xD+yD)

def difDisp(i):
	return abs(i[0]-displacement[0])+abs(i[1]-displacement[1])

c1 = [[0.0,0.0],[0.0,10.0],[20.0,10.0]]
c2 = [[22.0,4.0],[1.0,8.0]]

for x in range(len(c2)):
	i = c2[x]
	mag = dist(i,[0,0])
	direc = math.atan(i[1]/i[0])
	if(i[0]<0):direc+=math.pi
	direc+=adj
	c2[x]=[float('%.3f'%(mag*math.cos(direc))),float('%.3f'%(mag*math.sin(direc)))]



comboPairs = []
comboDist = []

for i in c1:
	for j in c2:
		comboPairs.append([i,j])

for i in comboPairs:
	comboDist.append(dist(i[0],i[1]))

combos = sorted(list(zip(comboDist,comboPairs)))

pairs = []
used1 = []
used2 = []

for i in combos:
	m1 = i[1][0]
	m2 = i[1][1]
	if(not(m1 in used1) and not(m2 in used2) and i[0]<=dTresh):
		dispList = [float('%.3f'%(i[1][1][0]-i[1][0][0])),float('%.3f'%(i[1][1][1]-i[1][0][1]))]
		pairs.append([dispList,i[1]])
		used1.append(m1)
		used2.append(m2)

moving = []

for i in pairs:moving.append(difDisp(i[0])>dWithin)

for i in pairs:print(i)
print(moving)