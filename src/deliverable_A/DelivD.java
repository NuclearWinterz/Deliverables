package deliverable_A;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// Class DelivD does the work for deliverable DelivD of the Prog340

public class DelivD {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	Graph h;

	public DelivD(File in, Graph gr, Graph heuristic) {
		inputFile = in;
		g = gr;
		h = heuristic;

		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring(0, inputFileName.length() - 4); // Strip off ".txt"
		String outputFileName = baseFileName.concat("_out.txt");
		outputFile = new File(outputFileName);
		if (outputFile.exists()) { // For retests
			outputFile.delete();
		}

		try {
			output = new PrintWriter(outputFile);
		} catch (Exception x) {
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		aStarSearch(output);
	}

	public void aStarSearch(PrintWriter Output) {

		// Creates our heap object, a goalNode and lastNode to keep track of important
		// stuff during execution, heap, visistedList, and foundGoal flag
		Heap heap = new Heap();
		ArrayList<Node> visitedList = new ArrayList<Node>();
		boolean foundGoal = false;
		Node goalNode = null;
		// initializes the goal node distance value to a stupid large number till goal
		// is found
		double goalNodeTotalValue = 500000000;
		// Get the graphs nodes
		Node[] nodeArray = g.getNodeList().toArray(new Node[g.getNodeList().size()]);
		Node[] heuristicNodeArray = h.getNodeList().toArray(new Node[h.getNodeList().size()]);
		// starts what will be our heap
		Node[] temp = new Node[2];

		// Iterate through the node array to find start node
		for (int index = 0; index < nodeArray.length; index++) {

			String value = nodeArray[index].getVal();

			// find the start node and initialize it
			if (value.equals("S")) {
				temp[1] = nodeArray[index];
				temp[1].setVal("0");
				// set the path of a node from its predecessor to it
				temp[1].setPath(temp[1].getAbbrev());
			}
			// find the goal node and save it in goal node
			if (value.equals("G")) {
				goalNode = nodeArray[index];
			}
		}
		// for the nodes in the array get their edges and set the heuristic from them
		for (int index = 0; index < nodeArray.length; index++) {
			ArrayList<Edge> outgoingEdgesHeuristic = heuristicNodeArray[index].getOutgoingEdges();
			for (Edge e : outgoingEdgesHeuristic) {
				Node edgeHead = e.getHead();
				if (edgeHead.getName().equals(goalNode.getName())) {
					nodeArray[index].setHeuristic(e.getLabel());
				}
			}
		}
		// if there is no starting point throws an exception
		if (temp[1] == null) {
			throw new IllegalArgumentException("This graph lacks a start point");
		}
		// Builds our min heap with just the start node.
		heap.buildMinHeap(temp);

		// get the outgoing edges from the start node
		ArrayList<Edge> initialOutgoingEdges = temp[1].getOutgoingEdges();

		// Iterate through all the outgoing edges from start node to seed the tree
		for (Edge e : initialOutgoingEdges) {
			// gets the head of the given outgoing edge
			Node edgeHead = e.getHead();
			Node edgeTail = e.getTail();
			// sets the value equal to the edge value and marks a node as Goal if it was its
			// initial value
			if (edgeHead.getVal().equals("G")) {
				edgeHead.setGoal();
				edgeHead.setVal(e.getLabel());
				goalNodeTotalValue = Integer.parseInt(e.getLabel());
			}
			// Sets the value of the node to the heuristic + edge length and sets the
			// distance
			edgeHead.setDistance(e.getLabel());
			int value = Integer.parseInt(e.getLabel()) + Integer.parseInt(edgeHead.getHeuristic());
			edgeHead.setVal(Integer.toString(value));
			edgeHead.setPath(edgeTail.getPath() + "-" + edgeHead.getAbbrev());

			// Inserts the node into the heap
			heap.minHeapInsert(heap.getNodeHeap(), edgeHead);

		}
		// initializes nodeHeap
		Node[] nodeHeap = heap.getNodeHeap();
		// Prints out the first node in nodeHeap
		System.out.print(nodeHeap[1].getAbbrev() + ": ");
		for (int index = 2; index < nodeHeap.length; index++) {
			System.out.print(nodeHeap[index].getAbbrev() + " " + nodeHeap[index].getVal() + " "
					+ nodeHeap[index].getPath() + "  - ");
		}
		System.out.println("");

		// Extracts the start node from heap and adds it the visited list with its
		// value;
		visitedList.add(heap.extractMin(nodeHeap));
		// sets the nodeHeap to the internal Heap
		nodeHeap = heap.getNodeHeap();

		// Start do while to find the node goal
		do {
			// Gets the list of outgoing edges from the lowest cost node at head of heap
			nodeHeap = heap.getNodeHeap();
			ArrayList<Node> arrayListOfNodeHeap = new ArrayList<>(Arrays.asList(nodeHeap));
			ArrayList<Edge> outgoingEdges = nodeHeap[1].getOutgoingEdges();

			// Loop that goes through all the edges from the head node and adds it to heap
			for (Edge e : outgoingEdges) {
				// gets the head, and tail of the given outgoing edge
				Node edgeHead = e.getHead();
				Node edgeTail = e.getTail();
				// resets the heap information after every iteration
				arrayListOfNodeHeap = new ArrayList<>(Arrays.asList(heap.getNodeHeap()));
				nodeHeap = heap.getNodeHeap();

				if (edgeHead.getDistance() == null) {
					int newDistance = Integer.parseInt(edgeTail.getDistance()) + Integer.parseInt(e.getLabel());
					edgeHead.setDistance(Integer.toString(newDistance));
				}
				// check to see if the heap contains an edgeHead or visitedList contains it
				if (arrayListOfNodeHeap.contains(edgeHead) == true || visitedList.contains(edgeHead)) {

					// Gets the index of the edge head from the array list and parses its new value
					int indexOfEdgeHead = arrayListOfNodeHeap.indexOf(edgeHead);

					int newValue = Integer.parseInt(edgeTail.getDistance()) + Integer.parseInt(e.getLabel())
							+ Integer.parseInt(edgeHead.getDistance());

					// if the visited list contains edgeHead and the the old value is smaller than
					// the new value do nothing
					if (visitedList.contains(edgeHead)) {
						int oldValue = Integer.parseInt(visitedList.get(visitedList.indexOf(edgeHead)).getVal());
						if (oldValue < newValue) {
							// do nothing do not add it etc
						}
						// If it is not contained in the visited list decrease the key and set its new
						// path
					} else if (Integer.parseInt(nodeHeap[indexOfEdgeHead].getVal()) > newValue
							&& edgeHead != edgeTail) {

						// set the path and value to this path/value
						heap.heapDecreaseKey(heap.getNodeHeap(), indexOfEdgeHead, newValue);
						edgeHead.setPath(edgeTail.getPath() + "-" + edgeHead.getAbbrev());
						// rebuild the heap after the change
						heap.buildMinHeap(heap.getNodeHeap());

					} // else if the edgeHead equals goal, set its goal flag and add it to the heap
						// with its new distance value
				} else if (edgeHead.getVal().equals("G")) {
					edgeHead.setGoal();
					if (edgeHead.getHeuristic() == null) {
						edgeHead.setHeuristic("0");
					}
					int totalValue = Integer.parseInt(e.getLabel()) + Integer.parseInt(edgeTail.getDistance())
							+ Integer.parseInt(edgeHead.getHeuristic());
					edgeHead.setVal(Integer.toString(totalValue));
					edgeHead.setPath(edgeTail.getPath() + "-" + edgeHead.getAbbrev());
					goalNodeTotalValue = totalValue;
					heap.minHeapInsert(heap.getNodeHeap(), edgeHead);

				} else {
					// if it is not in the heap insert it with a new value
					int totalValue = Integer.parseInt(e.getLabel()) + Integer.parseInt(edgeTail.getDistance())
							+ Integer.parseInt(edgeHead.getHeuristic());
					edgeHead.setVal(Integer.toString(totalValue));
					edgeHead.setPath(edgeTail.getPath() + "-" + edgeHead.getAbbrev());
					if (totalValue < goalNodeTotalValue) {
						heap.minHeapInsert(heap.getNodeHeap(), edgeHead);
					}
				}
				// rebuilds the heap after a new iteration
				heap.buildMinHeap(heap.getNodeHeap());
			}

			// extract the minimum node and check if it is a goal. If so found goal is true
			// if not print its path and repeats
			Node minimalNode = heap.extractMin(heap.getNodeHeap());
			nodeHeap = heap.getNodeHeap();
			visitedList.add(minimalNode);
			if (minimalNode.isGoal == true || minimalNode.getVal().equals("G")) {
				foundGoal = true;
				System.out.println(minimalNode.getAbbrev() + ": ");
				System.out.println("Route: " + minimalNode.getPath());
				System.out.print("\n");
			} else {
				System.out.print(minimalNode.getAbbrev() + ": ");
				heap.printInOrder(1);
				//for (int index = 1; index < nodeHeap.length; index++) {
					//System.out.print(nodeHeap[index].getAbbrev() + " " + nodeHeap[index].getVal() + " "
							//+ nodeHeap[index].getPath() + " - ");

			//	}
				heap.buildMinHeap(nodeHeap);
				System.out.print("\n");
			}

		} while (foundGoal == false);
	}
}