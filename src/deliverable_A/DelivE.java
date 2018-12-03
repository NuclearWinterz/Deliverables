package deliverable_A;

import java.io.*;

import DelivD_GA.GA;
import DelivD_GA.Population;
import DelivD_GA.TourManager;

// Class DelivE does the work for deliverable DelivE of the Prog340

public class DelivE {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivE( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For retests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		geneticSearch(gr);
	}
	public void geneticSearch(Graph G) {
		//Declare a manager
		TourManager manager = new TourManager();
		//make a nodelist array
		Node[] nodeList = g.getNodeList().toArray(new Node[g.getNodeList().size()]);
		//add the cities from the nodelist to the manager
		TourManager.addCity(nodeList);
		
		Population pop = new Population(50, true);
		System.out.println("Initial distance: " + pop.getFittest().getDistance());
		System.out.println("Solution:");
		System.out.println(pop.getFittest());
		pop = GA.evolvePopulation(pop);
		
		for(int i = 0; i < 5000; i++) {
			pop = GA.evolvePopulation(pop);
		}
		
		System.out.println("Finished");
		System.out.println("Final distance " + pop.getFittest().getDistance());
		System.out.println("Solution:");
		System.out.print(nodeList[1].getName());
		System.out.println(pop.getFittest());
		
	}
}
