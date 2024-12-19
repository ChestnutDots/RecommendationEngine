
/**
 * Write a description of EfficientRater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class EfficientRater implements Rater{

    private String myID;
    private HashMap <String, Rating> myMap;

    public EfficientRater(String id) {
        myID = id;
        myMap = new HashMap <String, Rating>();
    }

    public void addRating(String item, double rating) {
        myMap.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        
        if(myMap.containsKey(item)){
            return true;
        }
        
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        
        // check that the movie is in the hashmap:
        if(hasRating(item)){
            // then get the rating value by looking the rating up in the hashmap and reading out the value;
            double currentRating=myMap.get(item).getValue();
            return currentRating;
        }
        
        return -1;
    }

    public int numRatings() {
        return myMap.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        //System.out.println("The size of hashmap is "+myMap.size());
        for(String item : myMap.keySet()){
            // takes the kth rating from an array list
            //Rating r= myMap.get(item);
            // adds it to the arraylist
            list.add(item);
        }
        //System.out.println("The array list is "+list);
        return list;
    }
}
