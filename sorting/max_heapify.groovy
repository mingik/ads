def parent(ind) { return i/2 }
def left(ind) { return 2 * ind }
def right(ind) { return 2*ind+1 }

def max_heapify(arr,i) { // sweep arr[i] down
  //if (arr.size() <= 1) { return arr } // do nothing
  //if (i >= arr.size()) { return arr }
  int leftIndex = left(i)
  int rightIndex = right(i)
  int largestIndex = i
  if (leftIndex <= arr.size() && arr[leftIndex] > arr[i]) {
    largestIndex = leftIndex
  } else if (rightIndex <= arr.size() && arr[rightIndex] > arr[i]) {
    largestIndex = rightIndex  
  }
  if (largestIndex != i) { // exchange arr[i] with greater child 
    def hand = arr[largestIndex]
    arr[largestIndex] = arr[i]
    arr[i] = hand // arr[largestIndex,i] = arr[i,largestIndex]
    max_heapify(arr, largestIndex)
  }
}  

def a = [4,6,7,3,1,4,6,7,9,0]
for (i in a.size()-1..0) {
  max_heapify(a,i)
}
println "initial array:"+a
println "maxheap:" + a