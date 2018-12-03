package deliverable_A;

import java.io.*;

// Class DelivA does the work for deliverable DelivA of the Prog340

public class DelivA {

	File inputFile;
	Graph g;
	
	public DelivA( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		File outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For retests
			outputFile.delete();
		}
		
		try {
			PrintWriter output = new PrintWriter(outputFile);	
			writeGraphInfo( output );
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		
	}
	
	/** Read the file containing the Strings, line by line, then process each line as it is read.
	**/
	public void writeGraphInfo( PrintWriter output ) {
				
		try {
			// output the graph information.  
			// I chose to output it to the console as well as the file for debugging purposes.
			System.out.println( "\n\n=========================================================================\n\n");
			System.out.println( g );
			output.println( g );
		}
		catch (Exception x ) {
			System.err.format("ExceptionInner: %s%n", x);
			System.exit(0);
		}
		output.close();
	}		

}
