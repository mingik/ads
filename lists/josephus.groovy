class Node {
  int order
  Node next
  Node(o) {
    order = o
  }
}

N = 9
M = 5
Node firstNode = new Node(1)
Node curNode = firstNode
for(int i=2; i<=N; i++) {
  Node newNode = new Node(i)
  curNode.next = newNode
  curNode = newNode
}
curNode.next = firstNode // close the circle

Node movingNode = firstNode
while( movingNode.next != movingNode ) { // stop when refer to itself!
  // keep playing
  for(int i=0; i<M-2; i++) { // stop one node before
    movingNode = movingNode.next
  }
  // movingNode.next should be removed!
  println( "Node with order: " + movingNode.next.order + "is dead")
  movingNode.next = movingNode.next.next
  movingNode = movingNode.next
}
println "Winnder is:" + movingNode.order