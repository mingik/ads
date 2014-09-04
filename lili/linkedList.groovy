class Node {
  def data
  Node next
  Node( Object d ) {
    data = d
  }
}

n1 = new Node(3)
n2 = new Node(4)
n1.next = n2
println n2
println n1.next