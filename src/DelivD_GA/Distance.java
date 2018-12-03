package DelivD_GA;

import java.util.ArrayList;

import deliverable_A.*;

public class Distance {

	public int distanceTo(Node fromCity, Node toCity) {
		// get outgoing edge and its label to find distance
		ArrayList<Edge> outGoingEdges = fromCity.getOutgoingEdges();
		for (Edge e : outGoingEdges) {
			if (e.getHead() == toCity) {
				return Integer.parseInt(e.getLabel());
			}
		}
		// Something went very wrong
		return 0;
	}
}
