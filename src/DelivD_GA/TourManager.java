package DelivD_GA;

import java.util.ArrayList;

import deliverable_A.*;

public class TourManager {

    // Holds our cities
    private static ArrayList<Node> destinationCities = new ArrayList<Node>();

    // Adds a destination city
    public static void addCity(Node[] nodeList) {
        for(Node N: nodeList) {
        	destinationCities.add(N);
        }
    }
    
    // Get a city
    public static Node getCity(int index){
        return (Node)destinationCities.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfCities(){
        return destinationCities.size();
    }
}