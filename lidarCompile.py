import csv
import random
import scipy.interpolate
import matplotlib.pyplot as plt
import numpy as np

dt = 0.2
ik = "cubic"

'''writer = csv.writer(open("lidata.csv","w"),quoting=csv.QUOTE_MINIMAL)

c=0
c2=0
a = 180.0
for i in range(100):
	arr = ['%.1f'%(dt*i),c,c2,float('%.1f'%(a))]
	writer.writerow(arr)
	change = 1
	if(random.random()<0.5):change+=1
	if(random.random()<0.3):change*=-1
	c+=change

	change = 1
	if(random.random()<0.5):change+=1
	if(random.random()<0.3):change*=-1
	c2+=change

	a+=10*(random.random()-0.5)'''


x = np.linspace(0,19,num=100,endpoint=True)

around = 3*dt
a=3

y1 = []
y2 = []
yg = []

read = csv.reader(open("lidata.csv",'r'))

for r in read:
	y1.append(int(r[1]))
	y2.append(int(r[2]))
	yg.append(float(r[3]))

sp1 = scipy.interpolate.interp1d(x, y1,kind=ik)
sp2 = scipy.interpolate.interp1d(x, y2,kind=ik)
spg = scipy.interpolate.interp1d(x, yg,kind=ik)

def dataVal(i):
	return([y1[i],y2[i],yg[i]])

def decInt(i):
	return int(i/dt)

def splineE(t):
	if t>=around and t<(len(x)*dt-around):
		floor = float('%.1f'%(int(t*(1/dt))*dt))
		points = np.linspace(floor-around,floor+around,num=2*decInt(around)+1)
		y1points = y1[decInt(floor)-a:decInt(floor)+a+1]
		smallSp1 = scipy.interpolate.interp1d(points,y1points,kind="cubic")
		y2points = y2[decInt(floor)-a:decInt(floor)+a+1]
		smallSp2 = scipy.interpolate.interp1d(points,y2points,kind="cubic")
		ygpoints = yg[decInt(floor)-a:decInt(floor)+a+1]
		smallSpg = scipy.interpolate.interp1d(points,ygpoints,kind="cubic")
		return [smallSp1(t),smallSp2(t),smallSpg(t)]
	else:
		return [sp1(t),sp2(t),spg(t)]

v = 7.94

print(dataVal(int(v*5)))
print(dataVal(int(v*5)+1))
print(splineE(v))


