package deliverable_A;

import java.io.*;

//Class DelivF does the work for deliverable DelivE of the Prog340

public class DelivF {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivF( File in, Graph gr ) {
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
		System.out.println( "DelivF:  To be implemented");
	}
}
