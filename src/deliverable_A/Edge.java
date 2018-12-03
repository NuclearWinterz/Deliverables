package deliverable_A;

//import java.util.*;

// Edge between two nodes
public class Edge {
	
	String label;
	Node tail;
	Node head;
	
	public Edge( Node tailNode, Node headNode, String theLabel ) {
		setLabel( theLabel );
		setTail( tailNode );
		setHead( headNode );
	}
	
	public String getLabel() {
		return label;
	}
	
	public Node getTail() {
		return tail;
	}
	
	public Node getHead() {
		return head;
	}
	
	public void setLabel( String s ) {
		label = s;
	}
	
	public void setTail( Node n ) {
		tail = n;
	}
	
	public void setHead( Node n ) {
		head = n;
	}
}
