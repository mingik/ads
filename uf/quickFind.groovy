def quickFind( arr, elementToFind ) {
  return arr[ elementToFind ]
}

def slowUnion( arr, elementOne, elementTwo ) {
  int componentOne = arr[ elementOne ]
  int componentTwo = arr[ elementTwo ]
  for (int i = 0; i<arr.size(); i++) {
    if (arr[i] == componentOne) {
      arr[i] = componentTwo
    }
  }
}

int[] ids = new int[10];

for (int i= 0; i < 10; i++) { ids[i] = i } // initially, all elements are components

println( ids )
slowUnion( ids, 3,4 )
println( "after union(3,4): " + ids )
slowUnion( ids, 4,9 )
println( "after union(4,9): " + ids )
slowUnion( ids, 8,0 )
println( "after union(8,0): " + ids )
slowUnion( ids, 2,3 )
println( "after union(2,3): " + ids )
slowUnion( ids, 5,6 )
println( "after union(5,6): " + ids )
slowUnion( ids, 2,9 )
println( "after union(2,9): " + ids )
slowUnion( ids, 5,9 )
println( "after union(5,9): " + ids )
slowUnion( ids, 7,3 )
println( "after union(7,3): " + ids )
slowUnion( ids, 4,8 )
println( "after union(4,8): " + ids )
slowUnion( ids, 5,6 )
println( "after union(5,6): " + ids )
slowUnion( ids, 0,2 )
println( "after union(0,2): " + ids )
slowUnion( ids, 6,1 )
println( "after union(6,1): " + ids )