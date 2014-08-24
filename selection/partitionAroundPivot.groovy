def selectPivotIndex(arr, lo, hi) {
  return lo
}
  
def partitionAroundPivot(arr, lo, hi, pivotIndex) {
  // 1) swap pivot with last elemnt in array
  def pivotValue = arr[pivotIndex]
  arr[pivotIndex] = arr[hi]
  arr[hi] = pivotValue // arr[pivotIndex,hi] =  arr[hi,pivotIndex] also works

  // 2) all values <= pivotValue are moved to front of array
  // lessThanPivotSubarrayEndNext is the index of element right next to end of
  // subarray of elements <= pivotValue seen so far
  def lessThanPivotSubarrayEndNext = lo // lessThanPivotSubarry is empty now and to the left
  for (idx in lo..<hi) { // iterate from left to right excluding last (it's the pivot itself)
    if (arr[idx] <= pivotValue) { // found element <= pivotValue
      def hand = arr[idx] // swap it with arr[ lessThanPivotSubarrayEndNext ]
      arr[idx] = arr[lessThanPivotSubarrayEndNext]
      arr[lessThanPivotSubarrayEndNext] = hand
      lessThanPivotSubarrayEndNext++ // move index to the right by one
    }
  }
        
  // 3) finally put pivot itself at index of element right next to end of 
  // subarry of ALL elements <= pivotValue 
  def hand = arr[hi]
  arr[hi] = arr[lessThanPivotSubarrayEndNext]
  arr[lessThanPivotSubarrayEndNext] = hand
	
  return lessThanPivotSubarrayEndNext
}

arr = [10,34,99,11,1,4,5,5,4,3,2,1]

println "Original array: " + arr
println "Pivot index: 3, Pivot value: ${arr[3]}"
println "Pivot index in partitioned array: ${partitionAroundPivot(arr,0,arr.size()-1,3)}"
println "Partitioned array: ${arr}"