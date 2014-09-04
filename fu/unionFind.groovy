def quickFind( arr, elementToFind ) {
  if( arr[elementToFind] == elementToFind ) {
    return elementToFind
  } else {
    quickFind( arr, arr[elementToFind] )
  }
}

def quickUnion( arr, elementOne, elementTwo ) {
  int curIndex = elementOne
  int lengthOne = 1
  while ( arr[curIndex] != curIndex ) {
    arr[curIndex] = arr[ arr[curIndex] ] // path compression by halving 
    curIndex = arr[curIndex]
    lengthOne++
  }
  int componentOneIndex = curIndex

  curIndex = elementTwo
  int lengthTwo = 1
  while( arr[curIndex] != curIndex ) {
    arr[curIndex] = arr[ arr[curIndex] ] // path compression by halving
    curIndex = arr[curIndex]
    lengthTwo++
  }
  int componentTwoIndex = curIndex

  // smaller to larger connection
  if (lengthOne < lengthTwo) {
    arr[componentOneIndex] = componentTwoIndex
  } else {
    arr[componentTwoIndex] = componentOneIndex
  }
}

int[] ids = new int[10];

for(int i= 0; i < 10; i++) { ids[i] = i } // initially, all elements are components

println( ids )
quickUnion( ids, 3,4 )
println( "after union(3,4): " + ids )
println( "find 3: " + quickFind(ids, 3) )
quickUnion( ids, 4,9 )
println( "after union(4,9): " + ids )
println( "find 4: " + quickFind(ids, 4) )
quickUnion( ids, 8,0 )
println( "after union(8,0): " + ids )
println( "find 8: " + quickFind(ids, 8) )
quickUnion( ids, 2,3 )
println( "after union(2,3): " + ids )
println( "find 2: " + quickFind(ids, 2) )
quickUnion( ids, 5,6 )
println( "after union(5,6): " + ids )
println( "find 5: " + quickFind(ids, 5) )
quickUnion( ids, 2,9 )
println( "after union(2,9): " + ids )
println( "find 2: " + quickFind(ids, 2) )
quickUnion( ids, 5,9 )
println( "after union(5,9): " + ids )
println( "find 5: " + quickFind(ids, 5) )
quickUnion( ids, 7,3 )
println( "after union(7,3): " + ids )
println( "find 7: " + quickFind(ids, 7) )
quickUnion( ids, 4,8 )
println( "after union(4,8): " + ids )
println( "find 4: " + quickFind(ids, 4) )
quickUnion( ids, 5,6 )
println( "after union(5,6): " + ids )
println( "find 5: " + quickFind(ids, 5) )
quickUnion( ids, 0,2 )
println( "after union(0,2): " + ids )
println( "find 0: " + quickFind(ids, 0) )
quickUnion( ids, 6,1 )
println( "after union(6,1): " + ids )
println( "find 6: " + quickFind(ids, 6) )