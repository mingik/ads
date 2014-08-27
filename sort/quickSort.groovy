def choosePivotIndex(arr,lo,hi) {
  return lo
}

def partitionByPivot(arr,lo,hi,pivotIndexBefore) { 
  // put pivot at the right end
  def hand = arr[pivotIndexBefore]
  arr[pivotIndexBefore] = arr[hi]
  arr[hi] = hand
  // initialize travelers:
  def placeForPivot = lo-1
  // iterate and swap if less than pivot
  for( unknownIndex in lo..<hi ) { 
    if (arr[unknownIndex] <= arr[hi]) {
      placeForPivot++
      hand = arr[placeForPivot]
      arr[placeForPivot] = arr[unknownIndex]
      arr[unknownIndex] = hand
    }
  }
  // finally, put pivot after
  placeForPivot++
  hand = arr[placeForPivot]
  arr[placeForPivot] = arr[hi]
  arr[hi] = hand

  return placeForPivot
}

def qsort(arr,lo,hi) {
  if ( lo<hi) {
    int pivotIndexBefore = choosePivotIndex(arr,lo,hi) // choose and put at the end
    int pivotIndexAfter = partitionByPivot(arr,lo,hi,pivotIndexBefore) // partition by pivot
    qsort(arr,lo,pivotIndexAfter-1)
    qsort(arr,pivotIndexAfter+1,hi)
  }
}

a = [5,9,2,65,7,8,99,3,2,43,67]
println "before: " + a
qsort(a,0,a.size()-1)
println "after: " + a