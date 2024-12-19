

import lib.Filter;
import java.util.*;

public class MovieRunnerWithFilters {
    
    public void printAverageRatings(){
        System.out.println("----------NewTest--------------");
        ThirdRatings thirdRate = new ThirdRatings("ratings.csv");
        
        System.out.println("Number of raters read in: "+thirdRate.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("The number of movies in database is "+MovieDatabase.size());
        //print a list of movies and their ratings ascending:
        int minRating=35;
        
        ArrayList<Rating> ratingList=thirdRate.getAverageRatings(minRating);

        System.out.println("Movies with over "+minRating+" ratings are "+ratingList.size());
        //sorts the list by value, because it is specified with the compareTo method in the Rating
        Collections.sort(ratingList);

    }
    
    public void printAverageRatingsByYear(){
        
        System.out.println("----------NewTest--------------");
        
        ThirdRatings thirdRate = new ThirdRatings("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        int year=2000;
        int minRaters=20;
        
        Filter f = new YearAfterFilter(year);
        
        ArrayList<Rating> filteredList=thirdRate.getAverageRatingsByFilter(minRaters, f);
        
        System.out.println("Number of movies filtered "+filteredList.size());
        
        Collections.sort(filteredList);

    }
    
    public void printAverageRatingsByGenre(){
    
        System.out.println("----------NewTest--------------");
        
        ThirdRatings thirdRate = new ThirdRatings("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        String genre="Comedy";
        int minRaters=20;
        
        Filter gFilter = new GenreFilter(genre);
        
        ArrayList<Rating> filteredList=thirdRate.getAverageRatingsByFilter(minRaters, gFilter);
        
        System.out.println("Number of movies filtered "+filteredList.size());
        
        Collections.sort(filteredList);
        
    }
    
    public void printAverageRatingsByMinutes(){
    
        System.out.println("----------NewTest--------------");
        
        ThirdRatings thirdRate = new ThirdRatings("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        int min=105;
        int max=135;
        int minRaters=5;
        
        Filter timeF= new MinutesFilter(min,max);
        
        ArrayList<Rating> filteredList=thirdRate.getAverageRatingsByFilter(minRaters, timeF);
        
        System.out.println("Number of movies filtered "+filteredList.size());
        
        Collections.sort(filteredList);
        
        
    }    

    public void printAverageRatingsByDirectors(){
        
        System.out.println("----------NewTest--------------");
        
        ThirdRatings thirdRate = new ThirdRatings("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        String directors="Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        int minRaters=4;
        
        Filter dirF= new DirectorsFilter(directors);
        
        ArrayList<Rating> filteredList=thirdRate.getAverageRatingsByFilter(minRaters, dirF);
        
        System.out.println("Number of movies filtered "+filteredList.size());
        
        Collections.sort(filteredList);
        
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        System.out.println("----------NewTest--------------");
        
        ThirdRatings thirdRate = new ThirdRatings("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
         
        
        int year=1990;
        String genre="Drama";
        int minRater=8;
         
         AllFilters af = new AllFilters();
         
         af.addFilter(new YearAfterFilter(year));
         af.addFilter(new GenreFilter(genre));
         
        ArrayList<Rating> filteredList=thirdRate.getAverageRatingsByFilter(minRater, af);
        
        System.out.println("Number of movies filtered "+filteredList.size());
        
        Collections.sort(filteredList);

    }
    
    public void printAverageRatingsByDirectorsAndMinutes(){
        
        System.out.println("----------NewTest--------------");
        
        ThirdRatings thirdRate = new ThirdRatings("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
         
        int min=90;
        int max=180;
        int minRater=3;
        String directors="Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
    
        AllFilters af = new AllFilters();
        
        af.addFilter(new MinutesFilter(min,max));
        af.addFilter(new DirectorsFilter(directors));
        
        ArrayList<Rating> filteredList=thirdRate.getAverageRatingsByFilter(minRater, af);
        
        System.out.println("Number of movies filtered "+filteredList.size());
        
        Collections.sort(filteredList);

    }
}
