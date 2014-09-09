# KD Tree Implementation

maxValue = 21448934432982309
minValue = -23242349820398429

HORIZONTAL = 0
VERTICAL = 1

X_ = 0
Y_ = 1

class Region:
    """Represents a region"""
    def __init__(self,xmin,ymin,xmax,ymax):
        self.xmin = xmin
        self.ymin = ymin
        self.xmax = xmax
        self.ymax = ymax
    def copy(self):
        return Region(self.xmin,self.ymin,self.xmax,self.ymax)

# default maximum region
maxRegion = Region(minValue,minValue,maxValue,maxValue)

class KDNode:
    def __init__(self,pt,orient,region=maxRegion):
        """Create an empty KDNode"""
        self.point = pt
        self.orient = orient
        self.above = None
        self.below = None
    def add(self,pt):
        

class KDTree:
    def __init__(self):
        """create an empty kdtree"""
        self.root = None
    def add(self,pt):
        """Add point to KDTree"""
        if self.root:
            self.root.add(pt)
        else:
            self.root = KDNode(pt,VERTICAL)
