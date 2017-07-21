"""returns true if the rectangle overlaps given the x and y coordinates
of the top left and bottom right corners"""
"""def overlaps(x1, y1, x2, y2, x3, y3, x4, y4):
    #starting on rectangle 1 and going left to right, top to bottom
    #then rectangle 2
    #then 0 case

    #first rectangle
    #check x1, y1
    if((x3 < x1 and x1 < x4) #x1 is between x3 and x4
     and (y3 < y1 and y1 < y4)): #y1 is between y3 and y4
    	return True
    #check x2, y1
    elif((x3 < x2 and x2 < x4) #x2 is between x3 and
     and (y3 < y1 and y1 < y4)): #y1 is between y3 and y4
    	return True
    #check x1, y2
    elif((x3 < x1 and x1 < x4) #x1 is between x3 and x4
	 and (y3 < y2 and y2 < y4)): #y2 is between y3 and y4
    	return True
    #check x2, y2
    elif((x3 < x2 and x2 < x4) #x2 is between x3 and x4
     and (y3 < y2 and y2 < y4)): #y1 is between y3 and y4
    	return True

    #second rectangle
	#check x3, y3
    elif((x1 < x3 and x3 < x2) #x3 is between x1 and x2
     and (y1 < y3 and y3 < y2)): #y3 is between y1 and y2
    	return True
	#check x4, y3
    elif((x1 < x4 and x4 < x2) #x4 is between x1 and x2
     and (y1 < y3 and y3 < y2)): #y3 is between y1 and y2
    	return True
	#check x3, y4
    elif((x1 < x3 and x3 < x2) #x3 is between x1 and x2
     and (y1 < y4 and y4 < y2)): #y4 is between y1 and y2
    	return True
    #check x4, y4
    elif((x1 < x4 and x4 < x2) #x4 is between x1 and x2
     and (y1 < y4 and y4 < y2)): #y4 is between y1 and y2
    	return True

    #the zero cases
    #first rect crosses vertically
    elif((x3 < x1 and x1 < x4) #x1 is between x3 and x4
     and (x3 < x2 and x2 < x4) #x2 is between x3 and x4
     and (y1 < y3 and y3 < y2) #y3 is between y1 and y2
     and (y1 < y4 and y4 < y2)): #y4 is between y1 and y2
    	return True
    #first rect crosses horizontally
    elif((x1 < x3 and x3 < x2) #x3 is between x1 and x2
     and (x1 < x4 and x4 < x2) #x4 is between x1 and x2
     and (y3 < y1 and y1 < y4)) #y1 is between y3 and y4
     and (y3 < y2 and y2 < y4)): #y2 is between y3 and y4
		return True

	#if alll the above fails
	return False
"""