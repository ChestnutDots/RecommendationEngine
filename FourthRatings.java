
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import lib.Filter;

import java.util.*;
import java.lang.*;

public class FourthRatings {
    
    public double getAverageByID(String id, int minimalRaters){
        //return a movie average if there are at least minimal raters ratings, else return 0.0
        double ratingAverage=0.0;
        
        FirstRatings firstRate= new FirstRatings();
        int numberOfRatingsPerMovie=firstRate.numRatingsMovie(RaterDatabase.getRaters(), id);
        
        double sumForAverage=0;
        
        
        ArrayList<Rater> raterList=RaterDatabase.getRaters();
        //check if there are enough ratings for a movie:
        if(numberOfRatingsPerMovie>=minimalRaters){
            
            //if so, calculate average:
            for (Rater currentRat:raterList){
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
    
    private double dotProduct(Rater me, Rater r){
        
        
        ArrayList<String> myMovies=me.getItemsRated();
        ArrayList<String> rMovies= r.getItemsRated();
        
        double dotProduct=0;
        
        
        for(String movieID:myMovies){
            if(rMovies.contains(movieID)){
                double myRating=me.getRating(movieID);
                double rRating=r.getRating(movieID);
                double tempProduct=(myRating-5)*(rRating-5);
                
                dotProduct=dotProduct+tempProduct;
                
            }
        }
        
        return dotProduct;
    }
    
    private ArrayList<Rating> getSimilarities(String Rater_id){
        ArrayList<Rating> myList= new ArrayList<Rating>();
    
        ArrayList<Rater> ratersList= RaterDatabase.getRaters();
        Rater me= RaterDatabase.getRater(Rater_id);
        
        for(Rater currentRater:ratersList){
            // check that you are only comparing to everyone except yourself:
            if(currentRater!=me ){
                double similarity=dotProduct(me,currentRater);
                
                //only care about raters that had any shared movies:
                if(similarity>0){
                
                Rating currentRating= new Rating(currentRater.getID(), similarity);
                myList.add(currentRating);
                }
       
            }
        }
        //sort the list in a descending order:
        Collections.sort(myList,Collections.reverseOrder());
        
        return myList;
    }
    
    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters){
        ArrayList<Rating> myList= new ArrayList<Rating>();
        
        //for every rater, get their similarity rating:
        // get an array list of how all other raters compare to this rater:
        ArrayList<Rating> ratingList=getSimilarities(raterID);
        ArrayList<Rating> ratingListNumbered= new ArrayList<Rating>();
        
        for(int i=0; i<numSimilarRaters; i++){
            Rating currentRating=ratingList.get(i);
            ratingListNumbered.add(currentRating);
        }
        
        // loop through the movies and multiply their rating with the raters similarity:
        for(String movieID : MovieDatabase.filterBy(new TrueFilter())){
            int numRatings=0;
            double sumRatings=0.0;
            
            for(Rating currentRating:ratingListNumbered){
                
                double similarity=currentRating.getValue();
                //use rater id and weight in r to update running totals
                
                String currentRaterID=currentRating.getItem();
                Rater currentRater=RaterDatabase.getRater(currentRaterID);
                
                if(currentRater.hasRating(movieID)){
                    //double movieRating=currentRater.getRating(movieID);
                    double movieRating=currentRater.getRating(movieID);
                    double weighed_Rating=similarity*movieRating;
                    sumRatings=sumRatings+weighed_Rating;
                    numRatings++;
                }
            }
            // add rating for movieID to the return list
            
            if(numRatings>=minimalRaters){
            
                double averageRating=sumRatings/numRatings;
            
                myList.add(new Rating(movieID, averageRating));
            }
            
            
        }
            
        Collections.sort(myList,Collections.reverseOrder());
        
        
        return myList;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimalRaters, Filter filterCriteria){
        
        ArrayList<Rating> myList = new ArrayList <Rating>();
        
        //for every rater, get their similarity rating:
        // get an array list of how all other raters compare to this rater:
        ArrayList<Rating> ratingList=getSimilarities(raterID);
        ArrayList<Rating> ratingListNumbered= new ArrayList<Rating>();
        for(int i=0; i<numSimilarRaters; i++){
            Rating currentRating=ratingList.get(i);
            ratingListNumbered.add(currentRating);
        }
        
        
        // loop through the movies and multiply their rating with the raters similarity:
        for(String movieID : MovieDatabase.filterBy(filterCriteria)){
            int numRatings=0;
            double sumRatings=0.0;
            
            for(Rating currentRating:ratingListNumbered){
                
                double similarity=currentRating.getValue();
                //use rater id and weight in r to update running totals
                
                String currentRaterID=currentRating.getItem();
                Rater currentRater=RaterDatabase.getRater(currentRaterID);
                
                if(currentRater.hasRating(movieID)){
                    //double movieRating=currentRater.getRating(movieID);
                    double movieRating=currentRater.getRating(movieID);
                    double weighed_Rating=similarity*movieRating;
                    sumRatings=sumRatings+weighed_Rating;
                    numRatings++;
                }
            }
            // add rating for movieID to the return list
            
            if(numRatings>=minimalRaters){
            
                double averageRating=sumRatings/numRatings;
            
                myList.add(new Rating(movieID, averageRating));
            }
            
            
        }
            
        Collections.sort(myList,Collections.reverseOrder());
        
        
        return myList;
    }
}
