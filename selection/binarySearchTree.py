class BinaryNode:
    def __init__(self, value=None):
        self.value = value
        self.leftChild = None
        self.rightChild = None
    def add(self,val):
        if val <= self.value:
            if self.leftChild: # left child exists
                self.leftChild.add(val)
            else:
                self.leftChild = BinaryNode(val)
        else:
            if self.rightChild: 
                self.rightChild.add(val)
            else: # create right child if it didn't exist
                self.rightChild = BinaryNode(val)
    def delete(self):
        # replace the root with rightmost element 
        # in LEFT subtree
        if self.leftChild == self.rightChild == None:
            return None
        if self.leftChild is None:
            return self.rightChild
        if self.rightChild is None:
            return self.leftChild
        
        child = self.leftChild # root's left subtree
        # left subtree's root right child
        grandChild = child.rightChild 
        if grandChild:
            while grandChild.rightChild:
                child = grandChild
                grandChild = child.rightChild
            # put left subtree's rightmost value into root
            self.value = grandChild.value 
            # attach left subtree of left subtree's 
            # rightmost value as it's parent's right subtree
            child.rightChild = grandChild.leftChild
        else:
            self.leftChild = child.leftChild
            self.value = child.value
        return self
    
    
    

class BinaryTree:
    def __init__(self):
        "Create an empty binary tree"
        self.root = None
    def add(self,value):
        "Insert a value in the PROPER location in BST"
        if self.root is None:
            self.root = BinaryNode(value)
        else:
            self.root.add(value)
    def contains(self,target):
        node = self.root
        while node:
            if target == node.value:
                return True
            if target < node.value:
                node = node.leftChild
            else:
                node = node.rightChild
        return False
    def remove(self,value):
        if self.root:
            self.root = self.removeFromParent(self.root,value)
            
    def removeFromParent(self,parent,value):
        # returns modified subtree with element removed
        if parent is None:
            return None
        if value == parent.value:
            return parent.delete()
        elif value < parent.value:
            parent.leftChild = self.removeFromParent(parent.leftChild,value)
        else:
            parent.rightChild = self.removeFromParent(parent.rightChild,value)
        return parent


def addRange(bt,ordered,low,high):
    if low <= high:
        mid=(low+high)/2
        bt.add(ordered[mid])
        addRange(bt,ordered,low,mid-1)
        addRange(bt,ordered,mid+1,high)
        
def balancedTree(ordered):
    bt = BinaryTree()
    addRange(bt,ordered,0,len(ordered)-1)
    return bt
    
if __name__ == "__main__":
    bt = BinaryTree()
    bt.add(5)
    print bt.contains(5)
    bt.add(1)
    bt.add(10)
    print bt.contains(2)
    print "left child:", bt.root.leftChild.value
    print "right child:", bt.root.rightChild.value
    bt.remove(5)
    print bt.contains(1)
    print bt.contains(10)
    print bt.contains(5)

    import random
    from time import time
    n = 1024
    while n<6536:
        bt = BinaryTree()
        for i in range(n):
            bt.add(random.randint(1,n))
        now = time()
        bt.contains( random.randint(1,n))
        print n, (time() - now)*1000
        n *= 2
    
    sarr = [random.randint(1,100) for i in range(20)]
    sarr = sorted(sarr)
    print sarr
    bst = balancedTree(sarr)
    print "root:", bst.root.value
    print "left child:",bst.root.leftChild.value
    print "right child:",bst.root.rightChild.value
    
    
    