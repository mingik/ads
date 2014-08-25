def parent(ind) { return ind / 2 }
def left(ind) {
  return 2*ind+1    
}
def right(ind) {
  return 2*ind+2
}

def max_heapify(arr,i) { // sweep arr[i] down
  int leftIndex = left(i)
  int rightIndex = right(i)
  int largestIndex = i
  if (leftIndex < arr.size() && arr[leftIndex] > arr[i]) {
    largestIndex = leftIndex
  }
  if (rightIndex < arr.size() && arr[rightIndex] > arr[largestIndex]) {
    largestIndex = rightIndex  
  }
  if (largestIndex != i) { // exchange arr[i] with greater child 
    def hand = arr[largestIndex]
    arr[largestIndex] = arr[i]
    arr[i] = hand // arr[largestIndex,i] = arr[i,largestIndex]

    max_heapify(arr, largestIndex)
  }
}  

def buildMaxHeap(arr) {
  for (i in arr.size()-1..0) { // actually, a.size()/2..0
    max_heapify(arr,i)
  }
}

def a = [4,6,7,3,1,4,6,7,9,0]
println "Initial array:"+a
buildMaxHeap(a)
println "maxheap:"+a
