from time import time

def contains(coll,target):
    return target in coll

def binary_search_contains(ordered_coll,target):
    # ALREADY SORTED collection!
    # returns index position of the target in ordered_coll
    low = 0
    high = len(ordered_coll)-1
    while low <= high:
        mid = (low+high)/2
        if target==ordered_coll[mid]:
            return mid
        elif target < ordered_coll[mid]:
            high = mid-1
        else:
            low = mid+1
    return -(low+1)

def insertInPlace(ordered_coll,target):
    idx = binary_search_contains(ordered_coll,target)
    if idx < 0:
        ordered_coll.insert( -(idx+1), target )
        return

    ordered_coll.insert(idx,target)
    

def performance1():
    n=1024
    sorted=range(n)
    while n<500000:
        now=time()
        contains(sorted,-1)
        done=time()
        print n, (done-now)*1000
        n*=2
    print "------------------"
    n=1024
    while n<500000000:
        now=time()
        binary_search_contains(sorted,-1)
        done=time()
        print n, (done-now)*1000
        n*=2
    
def performance2():
    n=1024
    while n<50000:
        sorted = range(n)
        now=time()
        insertInPlace(sorted,n+1)
        done=time()
        print n, (done-now)*1000
        n*=2
        
if (__name__=='__main__'):
    performance2()