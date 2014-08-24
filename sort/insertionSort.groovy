def insert(arr,pos,hand) {
  int cur = pos - 1 // start with the end of the sorted subarray
  while (cur >= 0 && arr[cur] > hand) { // go to the left, stop when element at cur index is < hand 
    arr[cur+1] = arr[cur] // shift elements to the right by one
    cur = cur - 1 //  go one element to the left
  }
  arr[cur+1] = hand // put value at hand into next element 
}

def insertionSort(arr) {
  if (arr.size() <= 1)
    return arr

  for (int i in 1..<arr.size()) // arr[0] is sorted subarray at first
    insert(arr,i,arr[i]) // arr[0..i-1] is already sorted subarray
    
  return arr
}

def a = [4,2,1,3,5,6,1,0]
println insertionSort(a)