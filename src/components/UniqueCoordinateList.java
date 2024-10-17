package components;

import java.util.ArrayList;
import java.util.HashSet;

public class UniqueCoordinateList {
    private ArrayList<Coordinate> coordinates; // List to store coordinates
    private HashSet<Coordinate> coordinateSet; // Set to track unique coordinates

    // Inner class to represent a coordinate
    public static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
        	return x;
        }
        public int getY() {
        	return y;
        }

        // Override equals and hashCode to ensure uniqueness in HashSet
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Coordinate that = (Coordinate) obj;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y; // Simple hash function
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    // Constructor
    public UniqueCoordinateList() {
        this.coordinates = new ArrayList<>();
        this.coordinateSet = new HashSet<>();
    }

    // Method to add a coordinate
    public boolean addCoordinate(int x, int y) {
        Coordinate coord = new Coordinate(x, y);

        // Check if the coordinate is already in the set
        if (!coordinateSet.contains(coord)) {
            coordinates.add(coord); // Add to the list
            coordinateSet.add(coord); // Add to the set
            return true; // Indicate success
        } else {
            //System.out.println("Coordinate " + coord + " already exists. Not added.");
            return false; // Indicate failure
        }
    }

    // Method to retrieve coordinates
    public ArrayList<Coordinate> getCoordinates() {
        return coordinates; // Return the list of coordinates
    }
    
    //Method to get the size of the list
    public int getSize() {
    	return coordinateSet.size();
    }
    public void clear() {
    	coordinates.clear();
    	coordinateSet.clear();
    }

  
}

