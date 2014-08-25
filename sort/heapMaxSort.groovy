def parent(ind) { return ind / 2 }
def left(ind) { return 2*ind+1 }
def right(ind) { return 2*ind+2 }

def max_heapify(arr,hi,i) { // sweep arr[i] down
  int leftIndex = left(i)
  int rightIndex = right(i)
  int largestIndex = i
  if (leftIndex <= hi && arr[leftIndex] > arr[i]) {
    largestIndex = leftIndex
  }
  if (rightIndex <= hi && arr[rightIndex] > arr[largestIndex]) {
    largestIndex = rightIndex  
  }
  if (largestIndex != i) { // exchange arr[i] with greater child 
    def hand = arr[largestIndex]
    arr[largestIndex] = arr[i]
    arr[i] = hand // arr[largestIndex,i] = arr[i,largestIndex]

    max_heapify(arr, hi, largestIndex)
  }
}  

def buildMaxHeap(arr) {
  for (i in arr.size()-1..0) { // actually, a.size()/2..0
    max_heapify(arr,arr.size()-1,i)
  }
}

def heapmaxsort(arr) {
  buildMaxHeap(arr)
  for (lastBound in arr.size()-1..1) {
    def hand = arr[0]
    arr[0] = arr[lastBound]
    arr[lastBound] = hand

    max_heapify(arr,lastBound-1,0)
  }
}

def a = [4,6,7,3,1,4,6,7,9,0]
println "Initial array:"+a
heapmaxsort(a)
println "heapmaxsorted:"+a
