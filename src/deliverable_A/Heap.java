package deliverable_A;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implements the methods needed for heap functions used in deliverable B.
 * Primary heap is a min heap.
 * 
 * @author Nathan C. Bishop
 *
 */

public class Heap {
	// creates the static index of the first node as one as well as instantiates the
	// local heap
	private static final int FIRST_NODE = 1;
	private int currentSize;
	Node[] nodeArray;
	// intermediateStrings holds the values printed out during heapsort at its
	// various stages
	private String intermediateStrings = "";

	/**
	 * Returns the values saved during heapsort
	 * 
	 * @return intermediateStrings
	 */
	public String getIntermediateStrings() {
		return intermediateStrings;
	}

	/**
	 * Sets the values saved during heapsort
	 * 
	 * @param intermediateStrings
	 */
	public void setIntermediateStrings(String intermediateStrings) {
		this.intermediateStrings = this.intermediateStrings + intermediateStrings + "\n";
	}

	/**
	 * Returns the parent index of childsIndex
	 * 
	 * @param childsIndex
	 * @return floorFunction(childsIndex/2)
	 */
	public int parent(int childsIndex) {

		return (int) Math.floor(childsIndex / 2);
	}

	/**
	 * Returns the left child of the given index
	 * 
	 * @param parentIndex
	 * @return 2 * parentIndex
	 */
	public int left(int parentIndex) {

		return 2 * parentIndex;
	}

	/**
	 * Returns the right child of the given index
	 * 
	 * @param parentIndex
	 * @return 2* parentIndex + 1
	 */
	public int right(int parentIndex) {

		return (2 * parentIndex) + 1;
	}

	/**
	 * builds a min heap then stores it locally in nodeArray
	 * 
	 * @param heapArray
	 */
	public void buildMinHeap(Node[] heapArray) {

		currentSize = heapArray.length;
		// loops through heaparray and creates the min trees
		for (double i = Math.floor(heapArray.length / 2); i >= 1; i--) {
			minHeapify((int) i, heapArray, heapArray.length);
		}
		nodeArray = heapArray;
	}

	/**
	 * takes a heap and sorts it by discerning which of its children should be
	 * switched. After the switch has happened it sorts the subtree so that it
	 * maintains the heap property.
	 * 
	 * @param index
	 * @param heap
	 * @param currentSize
	 */
	public void minHeapify(int index, Node[] heap, int currentSize) {
		// gets left node index, right node index, and creates int smallest
		int smallest = index;
		int left = left(index);
		int right = right(index);
									//getNodeValue(heap, right) < getNodeValue(heap, index)
		if (right < currentSize && heap[right].compareTo(heap[index]) == 1) {

			smallest = right;
		}							//getNodeValue(heap, left) < getNodeValue(heap, smallest)
		if (left < currentSize && heap[left].compareTo(heap[smallest]) == 1) {

			smallest = left;
		}

		if (smallest != index) {
			swapNodes(index, smallest);
			minHeapify(smallest, heap, currentSize);
			
		}
		
	}

	/**
	 * Takes a sorted heap and creates an array sorted greatest to least
	 * 
	 * @param heap
	 */
	public void heapsort(Node[] heap) {

		buildMinHeap(heap);

		for (int index = heap.length - 1; index >= 2; index--) {

			swapNodes(FIRST_NODE, index);
			minHeapify(1, heap, index);
			setIntermediateStrings(getHeapString());
		}

	}

	/**
	 * Swaps the position of the first node with the position of the second node in
	 * the array
	 * 
	 * @param firstNodePosition
	 * @param secondNodePosition
	 */
	private void swapNodes(int firstNodePosition, int secondNodePosition) {
		// creates a holder node
		Node positionHolder;
		// assigns the first position to the holder
		positionHolder = nodeArray[firstNodePosition];
		// assigns the second position to the position of the first
		nodeArray[firstNodePosition] = nodeArray[secondNodePosition];
		// assigns the first position to the second position
		nodeArray[secondNodePosition] = positionHolder;

	}

	/**
	 * Decreases the key in the array of a given node
	 * 
	 * @param heap
	 * @param index
	 * @param key
	 */
	public void heapDecreaseKey(Node[] heap, int index, int key) {
		heap[index].setVal(Integer.toString(key));
		nodeArray = heap;
		
	}
   
	/**
	 * Extracts the head of the minHeap and reHeapify's it to maintain its integrity
	 * 
	 * @param heap
	 * @return min
	 */
	public Node extractMin(Node[] heap) {
		// min value always at first node
		Node minimumNode = heap[1];
		// move last node to the head
		heap[1] = heap[heap.length - 1];
		
		heap = Arrays.copyOf(heap, heap.length - 1);
		nodeArray = heap;
		// Maintain structure of the heap
		minHeapify(1, heap, heap.length);
		
		return minimumNode;
	}

	/**
	 * inserts a key
	 * 
	 * @param heap
	 * @param key
	 */
	public void minHeapInsert(Node[] heap, Node key) {
		// make a new heap copy with +1 length
		heap = Arrays.copyOf(heap, heap.length + 1);
		heap[heap.length - 1] = key;
		nodeArray = heap;

	}

	/**
	 * A utility function that returns the value of a node parsing it from string to
	 * int
	 * 
	 * @param node
	 * @param index
	 * @return int index value
	 */
	
	/**
	 * Creates a string of the heap
	 * 
	 * @return heapString
	 */
	public String getHeapString() {
		String heapString = "";
		for (int index = nodeArray.length - 1; index >= 1; index--) {
			heapString = nodeArray[index].getAbbrev() + heapString;
		}
		return heapString;
	}

	/**
	 * Returns the nodeArray to the driver class
	 * 
	 * @return nodeArray
	 */
	public Node[] getNodeHeap() {
		return nodeArray;
	}
	
	public void printInOrder(int index) {
		if(index > nodeArray.length - 1) {
			return;
		}
		buildMinHeap(nodeArray);
		printInOrder(left(index));
		
		System.out.print(nodeArray[index].getAbbrev() + " " + nodeArray[index].getVal() + " "
				+ nodeArray[index].getPath() + " - ");
		
		printInOrder(right(index));
	}
}
