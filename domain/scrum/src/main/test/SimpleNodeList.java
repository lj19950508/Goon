public class SimpleNodeList {
    private Node head;
    private int size;

    private class Node{
        private int val;
        private Node next;

        public Node(int v){
            this.val = v;
        }
    }
    public SimpleNodeList(){
        this.head = null;
        this.size = 0;
    }

    public void add(int v,int index){
        Node preNode = this.head;
        for(int i = 0 ; i <index-1 ;i++){
            preNode = preNode.next;
        }
        Node node = new Node(v);
        node.next = preNode.next;
        preNode.next = node;
        this.size++;
    }

}
