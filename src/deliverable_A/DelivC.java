package deliverable_A;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.w3c.dom.NodeList;

// Class DelivC does the work for deliverable DelivC of the Prog340

public class DelivC {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;

	public DelivC(File in, Graph gr) {
		inputFile = in;
		g = gr;

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
		// Calls the search and gives it the graph that was created
		lowestCostFirstSearch(output);
	}

	/**
	 * Takes a graph and searches it from the starting node to its goal node then
	 * outputs the path taken.
	 * 
	 * @param Output
	 */

	public void lowestCostFirstSearch(PrintWriter Output) {

		// Creates our heap object, a goalNode and lastNode to keep track of important
		// stuff during execution, heap, visistedList, and foundGoal flag
		Heap heap = new Heap();
		ArrayList<Node> visitedList = new ArrayList<Node>();
		boolean foundGoal = false;

		// Get the graphs nodes
		Node[] nodeArray = g.getNodeList().toArray(new Node[g.getNodeList().size()]);

		// starts what will be our heap
		Node[] temp = new Node[2];

		// Iterate through the node array to find start node
		for (int index = 0; index < nodeArray.length; index++) {
			String value = nodeArray[index].getVal();

			if (value.equals("S")) {
				temp[1] = nodeArray[index];
				temp[1].setVal("0");
				// set the path of a node from its predecessor to it
				temp[1].setPath(temp[1].getAbbrev());
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
			}
			// Sets the value of the node and its path to it
			edgeHead.setVal(e.getLabel());
			edgeHead.setPath(edgeTail.getPath() + "-" + edgeHead.getAbbrev());

			// Inserts the node into the heap
			heap.minHeapInsert(heap.getNodeHeap(), edgeHead);

		}
		// initializes nodeHeap
		Node[] nodeHeap = heap.getNodeHeap();
		// Prints out the first node in nodeHeap
		System.out.println("Finished nodes");
		System.out.println(nodeHeap[1].getAbbrev() + " " + nodeHeap[1].getVal() + " " + nodeHeap[1].getAbbrev());

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
				// check to see if the heap contains an edgeHead or visitedList contains it
				if (arrayListOfNodeHeap.contains(edgeHead) == true || visitedList.contains(edgeHead)) {

					// Gets the index of the edge head from the array list and parses its new value
					int indexOfEdgeHead = arrayListOfNodeHeap.indexOf(edgeHead);
					int newValue = Integer.parseInt(edgeTail.getVal()) + Integer.parseInt(e.getLabel());

					// if the visited list contains edgeHead and the the old value is smaller than
					// the new value do nothing
					if (visitedList.contains(edgeHead)) {
						int oldValue = Integer.parseInt(visitedList.get(visitedList.indexOf(edgeHead)).getVal());
						if (oldValue < newValue) {
							// do nothing do not add it etc
						}
						// If it is not contained in the visited list decrease the key and set its new
						// path
					} else if (Integer.parseInt(nodeHeap[indexOfEdgeHead].getVal()) > newValue) {

						// set the path and value to this path/value
						heap.heapDecreaseKey(heap.getNodeHeap(), indexOfEdgeHead, newValue);
						edgeHead.setPath(edgeTail.getPath() + "-" + edgeHead.getAbbrev());
						// rebuild the heap after the change
						heap.buildMinHeap(heap.getNodeHeap());

					} // else if the edgeHead equals goal, set its goal flag and add it to the heap
						// with its new distance value
				} else if (edgeHead.getVal().equals("G")) {
					edgeHead.setGoal();
					int totalValue = Integer.parseInt(e.getLabel()) + Integer.parseInt(edgeTail.getVal());
					edgeHead.setVal(Integer.toString(totalValue));
					edgeHead.setPath(edgeTail.getPath() + "-" + edgeHead.getAbbrev());
					heap.minHeapInsert(heap.getNodeHeap(), edgeHead);

				} else {
					// if it is not in the heap insert it with a new value
					int totalValue = Integer.parseInt(e.getLabel()) + Integer.parseInt(edgeTail.getVal());
					edgeHead.setVal(Integer.toString(totalValue));
					edgeHead.setPath(edgeTail.getPath() + "-" + edgeHead.getAbbrev());
					heap.minHeapInsert(heap.getNodeHeap(), edgeHead);
				}
				// rebuilds the heap after a new iteration
				heap.buildMinHeap(heap.getNodeHeap());
			}

			// extract the minimum node and check if it is a goal. If so found goal is true
			// if not print its path and repeats
			Node minimalNode = heap.extractMin(heap.getNodeHeap());
			visitedList.add(minimalNode);
			if (minimalNode.isGoal == true || minimalNode.getVal().equals("G")) {
				foundGoal = true;
				System.out.println(minimalNode.getAbbrev() + " " + minimalNode.getVal() + " " + minimalNode.getPath());
			} else {
				System.out.println(minimalNode.getAbbrev() + " " + minimalNode.getVal() + " " + minimalNode.getPath());
			}

		} while (foundGoal == false);
	}
}
