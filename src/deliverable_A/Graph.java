package deliverable_A;

import java.util.*;

public class Graph {

	ArrayList<Node> nodeList;
	ArrayList<Edge> incommingEdgeList;

	public Graph() {
		nodeList = new ArrayList<Node>();
		incommingEdgeList = new ArrayList<Edge>();
	}

	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	public ArrayList<Edge> getEdgeList() {
		return incommingEdgeList;
	}

	public void addNode(Node n) {
		nodeList.add(n);
	}

	public void addEdge(Edge e) {
		incommingEdgeList.add(e);
	}

	public String toString() {
		String s = "Graph g.\n";
		if (nodeList.size() > 0) {
			for (Node n : nodeList) {
				// Print node info
				String t = "\nNode " + n.getName() + ", abbrev " + n.getAbbrev() + ", value " + n.getVal();
				s = s.concat(t);

				// sets the incoming edge array list to nodes incoming edges
				incommingEdgeList = n.getIncomingEdges();

				// Creates an outgoing edge array list and sets it to current node
				ArrayList<Edge> outgoingEdgeList = new ArrayList<Edge>();
				outgoingEdgeList = n.getOutgoingEdges();

				// loops through outgoing edges and add's them to string
				for (Edge e : outgoingEdgeList) {
					// print edge info
					Node nodeName = e.getHead();
					String name = nodeName.getName();
					t = "\n" + n.getName() + " has edge to " + name + " labeled " + e.getLabel();
					s = s.concat(t);
				}

				// loops through incoming edges and add's them to string
				for (Edge e1 : incommingEdgeList) {
					Node nodeName = e1.getTail();
					String name = nodeName.getName();
					t = "\n" + n.getName() + " has edge from " + name + " labeled " + e1.getLabel();
					s = s.concat(t);
				}
				s = s.concat("\n");
			}

			s = s.concat("\n");

		}

		s = s.concat("\n");

		return s;
	}

}
