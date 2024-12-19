
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import lib.Filter;

import java.util.*;

public class ThirdRatings {

    
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile){
        
        FirstRatings firstRate = new FirstRatings();
        
        myRaters=firstRate.loadRaters(ratingsfile);
    
    }
    
    public double getAverageByID(String id, int minimalRaters){
        //return a movie average if there are at least minimal raters ratings, else return 0.0
        double ratingAverage=0.0;
        
        FirstRatings firstRate= new FirstRatings();
        int numberOfRatingsPerMovie=firstRate.numRatingsMovie(myRaters, id);
        
        double sumForAverage=0;
        
        //check if there are enough ratings for a movie:
        if(numberOfRatingsPerMovie>=minimalRaters){
            
            //if so, calculate average:
            for (Rater currentRat:myRaters){
                 ArrayList<String> ratedMovies=currentRat.getItemsRated();
                
                if(ratedMovies.contains(id)){
                    sumForAverage=sumForAverage+currentRat.getRating(id);
                }
            }
            
            ratingAverage=sumForAverage/numberOfRatingsPerMovie;
        
        }
        
        
        return ratingAverage;
        
    }
    
    public ArrayList<Rating>getAverageRatings(int minimalRaters){
        ArrayList<Rating> ratingList=new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());;
        //loop through all movies and find their averages:
        //for(Movie currentMovie:myMovies){
        for(String movieID: movies){
            double averageRating=getAverageByID(movieID, minimalRaters);
            
            //check that the minimal Raters were fulfilled and store a new rating in the list:
            if(averageRating>0.0){
            Rating newRating= new Rating(movieID, averageRating);
            ratingList.add(newRating);
            }
        }
        
        return ratingList;
    }
    
    public int getRaterSize(){
        return myRaters.size();
       
    }
    
    public ArrayList<Rating>getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> ratingsList= new ArrayList<Rating>();
        
        ArrayList<String> movieIDs=MovieDatabase.filterBy(filterCriteria);
        
        // now check if you arrayList has minimalRaters:
        for(String movie:movieIDs){
            double averageRating=getAverageByID(movie, minimalRaters);
            // check that getAverageByID actually got a movie that was rated:
            if(averageRating>0.0){
                ratingsList.add(new Rating(movie, averageRating));
            }
        }
        
        return ratingsList;
    }
    
}
