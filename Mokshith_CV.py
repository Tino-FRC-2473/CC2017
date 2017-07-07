import numpy as np
import cv2
import math

ANGLE_OF_VISION = 1.36136
CONSTANT = 0
camera = cv2.VideoCapture(0)

def distance_peg(rect_height, constant):
    return (constant / rect_height)

def get_distance_between_points(x, y, x1, y1):
    return math.sqrt(((x1 - x) ** 2) + ((y1 - y) ** 2))

def get_turn_angle(oframe):
    width, height, channels = oframe.shape
    hsv = cv2.cvtColor(oframe, cv2.COLOR_BGR2HSV)
    lower_bound = np.array([0, 95 , 120])
    upper_bound = np.array([10, 150, 230])
    mask = cv2.inRange(hsv, lower_bound, upper_bound)
    contours, hierarchy = cv2.findContours(mask , 1, 2)

    rect1_pixel_point = [0, 0]
    biggest_area_1 = 0;
    big_contour_1 = None
    for c in contours:
        M = cv2.moments(c)
        epsilon = 0.1*cv2.arcLength(c,True)
        approx = cv2.approxPolyDP(c,epsilon,True)
        if(M['m00']!=0 and M['m10']!=0):
            cx = int(M['m10']/M['m00'])
            cy = int(M['m01']/M['m00'])
            if cv2.contourArea(c) > biggest_area_1:
                biggest_area_1 = cv2.contourArea(c)
                rect1_pixel_point = [cx, cy]
                big_contour_1 = c

    rect2_pixel_point = [0, 0]
    biggest_area_2 = 0;
    for c in contours:
        if (c.all() != big_contour_1.all()):
            M = cv2.moments(c)
            epsilon = 0.1*cv2.arcLength(c,True)
            approx = cv2.approxPolyDP(c,epsilon,True)
            if(M['m00']!=0 and M['m10']!=0):
                cx = int(M['m10']/M['m00'])
                cy = int(M['m01']/M['m00'])
                if cv2.contourArea(c) > biggest_area_2:
                    biggest_area_2 = cv2.contourArea(c)
                    rect2_pixel_point = [cx, cy]
    peg_pixel_point = [((rect1_pixel_point[0]+rect2_pixel_point[0])/2), ((rect1_pixel_point[1]+rect2_pixel_point[1])/2)]
    center_pixel_point = [width/2, height/2]
    triangle_base_length = get_distance_between_points(peg_pixel_point[0], peg_pixel_point[1], center_pixel_point[0], center_pixel_point[1])
    triangle_base_height = (width/2)/math.tan(ANGLE_OF_VISION/2)
    VAL = math.atan(triangle_base_height / triangle_base_length)
    return VAL * 180/math.pi

while True:
    grabbed, frame = camera.read()
    frame = cv2.flip(frame, 1)
    try:
        print get_turn_angle(frame)
    except AttributeError:
        print "Yeup"
