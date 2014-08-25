def parent(ind) { return i/2 }
def left(ind) { return 2 * ind +1}
def right(ind) { return 2*ind+2 }

def min_heapify(arr,hi,i) { // sweep arr[i] down
  int leftIndex = left(i)
  int rightIndex = right(i)
  int minIndex = i
  if (leftIndex <= hi && arr[leftIndex] < arr[i]) {
    minIndex = leftIndex
  }
  if (rightIndex <= hi && arr[rightIndex] < arr[minIndex]) {
    minIndex = rightIndex  
  }
  if (minIndex != i) { // exchange arr[i] with smaller child 
    def hand = arr[minIndex]
    arr[minIndex] = arr[i]
    arr[i] = hand // arr[minIndex,i] = arr[i,minIndex]
    
    min_heapify(arr, hi, minIndex)
  }
}  

def buildMinHeap(arr) { 
  for (i in arr.size()-1..0) { // actually, a.size()/2..0
    min_heapify(arr,arr.size()-1,i)
  }
}

def heapminsort(arr) { 
  buildMinHeap(arr) // build minHeap
  for (lastInd in arr.size()-1..1) {
    def hand = arr[lastInd]
    arr[lastInd] = arr[0]
    arr[0] = hand

    min_heapify(arr,lastInd-1,0)
  }
}

def a = [4,6,7,3,1,4,6,7,9,0]
println "Initial array:"+a
heapminsort(a)
println "sorted:" + a