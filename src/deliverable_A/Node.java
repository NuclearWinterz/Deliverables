package deliverable_A;

import java.util.*;

// A node of a graph for the Spring 2018 ICS 340 program

public class Node implements Comparable {

	String name;
	String val;  // The combined value of heuristic and distance of the node for D, the total distance in C
	String abbrev;  // The abbreviation for the Node
	String heuristic; //heuristic value to a goal node
	String distance;  //edge weight distance to the node

	ArrayList<Edge> outgoingEdges;  
	ArrayList<Edge> incomingEdges;
	//Create path that tracks the nodes it took to get to it and isGoal for searches
	String path;
	boolean isGoal = false;
	
	
	public Node( String theAbbrev ) {
		setAbbrev( theAbbrev );
		val = null;
		name = null;
		outgoingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
	}
	
	public String getAbbrev() {
		return abbrev;
	}
	
	public String getName() {
		return name;
	}
	
	public String getVal() {
		return val;
	}
	
	public ArrayList<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}
	
	public ArrayList<Edge> getIncomingEdges() {
		return incomingEdges;
	}
	
	public void setAbbrev( String theAbbrev ) {
		abbrev = theAbbrev;
	}
	
	public void setName( String theName ) {
		name = theName;
	}
	
	public void setVal( String theVal ) {
		val = theVal;
	}
	
	public void addOutgoingEdge( Edge e ) {
		outgoingEdges.add( e );
	}
	
	public void addIncomingEdge( Edge e ) {
		incomingEdges.add( e );
	}
	public void setGoal() {
		isGoal = true;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public String getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(String heuristic) {
		this.heuristic = heuristic;
	}


	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * Takes another node and compares it, if it is S,G, or null it does nothing
	 * Otherwise it parses the value of both and returns 1 if the current value is less
	 * than the value input. Otherwise it returns 0.
	 * @param Object comparativeNode
	 * @return 0 or 1
	 */
	@Override
	public int compareTo(Object comparativeNode) {
		
		//if this val is not an integer
		if(this.val.equals("G") || this.val.equals("S") || this.val.equals("~")) {
			return 0;
		}else {
			Node otherNode = (Node) comparativeNode;
			int currentVal = Integer.parseInt(this.val);
			int otherVal = Integer.parseInt(otherNode.getVal());
			if( currentVal == otherVal) {
				if(this.abbrev.compareTo(otherNode.getAbbrev()) < 0) {
					return 1;
				}else {
					return 0;
				}
			}
			
			if(currentVal < otherVal) {
				return 1;
			}
			return 0;
		}	
		
	}
	
}
