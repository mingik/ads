class Node {
   Object data;
   Node next;
   public Node(Object data, Node next) {
      this.data = data;
      this.next = next;
   }
   @Override
   public String toString() {
      return "value: " + data + " --next-->: " + ((next == null) ? null : next.toString());
   }
   
}

public class ReverseLL {
   public static Node reverse(Node linkedList) {
      if (linkedList == null || linkedList.next == null) return linkedList;
      Node prevNode = null;
      Node head = linkedList;
      Node nextNode = head.next;
      while (nextNode != null) {
         head.next = prevNode;
         prevNode = head;
         head = nextNode;
         nextNode = nextNode.next;
      }
      head.next = prevNode;
      return head;
   }
   
   public static void main(String[] args) {
      Node linkedList = new Node("a", new Node("b", new Node("c", null)));
      Node reversed = reverse(linkedList);
      System.out.println( reversed );
   }
   
}