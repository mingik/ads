def parent(ind) { return i/2 }
def left(ind) { return 2 * ind +1}
def right(ind) { return 2*ind+2 }

def min_heapify(arr,i) { // sweep arr[i] down
  int leftIndex = left(i)
  int rightIndex = right(i)
  int minIndex = i
  if (leftIndex < arr.size() && arr[leftIndex] < arr[i]) {
    minIndex = leftIndex
  }
  if (rightIndex <arr.size() && arr[rightIndex] < arr[minIndex]) {
    minIndex = rightIndex  
  }
  if (minIndex != i) { // exchange arr[i] with smaller child 
    def hand = arr[minIndex]
    arr[minIndex] = arr[i]
    arr[i] = hand // arr[minIndex,i] = arr[i,minIndex]
    
    min_heapify(arr, minIndex)
  }
}  

def buildMinHeap(arr) { 
  for (i in a.size()-1..0) { // actually, a.size()/2..0
    min_heapify(a,i)
  }
}

def a = [4,6,7,3,1,4,6,7,9,0]
println "Initial array:"+a
buildMinHeap(arr)
println "minheap:" + a