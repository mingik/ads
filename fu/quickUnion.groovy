def slowFind( arr, elementToFind ) {
  if( arr[elementToFind] == elementToFind ) {
    return elementToFind
  } else {
    slowFind( arr, arr[elementToFind] )
  }
}

def quickUnion( arr, elementOne, elementTwo ) {
  int curIndex = elementOne
  while ( arr[curIndex] != curIndex ) {
    curIndex = arr[curIndex]
  }
  int componentOneIndex = curIndex

  curIndex = elementTwo
  while( arr[curIndex] != curIndex ) {
    curIndex = arr[curIndex]
  }
  int componentTwoIndex = curIndex
  
  arr[componentOneIndex] = componentTwoIndex
}

int[] ids = new int[10];

for(int i= 0; i < 10; i++) { ids[i] = i } // initially, all elements are components

println( ids )
quickUnion( ids, 3,4 )
println( "after union(3,4): " + ids )
println( "find 3: " + slowFind(ids, 3) )
quickUnion( ids, 4,9 )
println( "after union(4,9): " + ids )
println( "find 4: " + slowFind(ids, 4) )
quickUnion( ids, 8,0 )
println( "after union(8,0): " + ids )
println( "find 8: " + slowFind(ids, 8) )
quickUnion( ids, 2,3 )
println( "after union(2,3): " + ids )
println( "find 2: " + slowFind(ids, 2) )
quickUnion( ids, 5,6 )
println( "after union(5,6): " + ids )
println( "find 5: " + slowFind(ids, 5) )
quickUnion( ids, 2,9 )
println( "after union(2,9): " + ids )
println( "find 2: " + slowFind(ids, 2) )
quickUnion( ids, 5,9 )
println( "after union(5,9): " + ids )
println( "find 5: " + slowFind(ids, 5) )
quickUnion( ids, 7,3 )
println( "after union(7,3): " + ids )
println( "find 7: " + slowFind(ids, 7) )
quickUnion( ids, 4,8 )
println( "after union(4,8): " + ids )
println( "find 4: " + slowFind(ids, 4) )
quickUnion( ids, 5,6 )
println( "after union(5,6): " + ids )
println( "find 5: " + slowFind(ids, 5) )
quickUnion( ids, 0,2 )
println( "after union(0,2): " + ids )
println( "find 0: " + slowFind(ids, 0) )
quickUnion( ids, 6,1 )
println( "after union(6,1): " + ids )
println( "find 6: " + slowFind(ids, 6) )