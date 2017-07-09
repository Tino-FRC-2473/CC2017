import LiDAR
import CV
import socket
import serial
import time
import logging
#Init Logging
ts = time.time()
st = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')
name = st+'.log'
logging.basicConfig(filename=name,level=logging.DEBUG)
#Init Socket and Serial
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = ''
port = 5811
x = 1
s.bind((host, port))
print ('Listening')
s.listen(1)
conn, addr = s.accept()
print 'Connected by:', addr
st = ""
ser = serial.Serial('/dev/ttyUSB0')
ser.isOpen()



while (1):
    returned = conn.recv(1024)
    logging.info('connected socket')
    if 'CV' in returned:
        print ('Running CV')
        sendstr = CV.CV()
        conn.send('CVData: ' + str(sendstr))
    elif 'LiDAR' in returned:
        print ('Running LiDAR')
        sendstr = LiDAR.LiDAR()
        conn.send('LiDARData: ' + str(sendstr))
    else:
        conn.send('invalid func received')

'''
while (1):
    time.sleep(.01)
    returned = ser.readline()
    returned = returned.strip()
    logging.info('connected serial')
    ser.flushInput()
    ser.flushOutput()
    time.sleep(.01)
    if 'CV' in returned:
        print ('Running CV')
        sendstr = CV.CV()
        ser.write('CVData: ' + str(sendstr) + '\n')
    elif 'LiDAR' in returned:
        print ('Running LiDAR')
        sendstr = LiDAR.LiDAR()
        ser.write('LiDARData: ' + str(sendstr) + '\n')
    else:
        ser.write('invalid func received')

'''
#s.close()
#ser.close()
