import lib.Filter;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {
    public void printAverageRatings(){
        System.out.println("----------NewTest FourthRatings--------------");
        FourthRatings fourthRate = new FourthRatings();


        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");

        System.out.println("Number of raters read in: "+RaterDatabase.size());
        System.out.println("The number of movies in database is "+MovieDatabase.size());
        //print a list of movies and their ratings ascending:
        int minRating=35;

        ArrayList<Rating> ratingList=fourthRate.getAverageRatings(minRating);

        for(Rating currRating:ratingList){
            //System.out.println(currRating.getValue()+" "+currRating.getItem());
        }
        System.out.println("Movies with over "+minRating+" ratings are "+ratingList.size());
        //sorts the list by value, because it is specified with the compareTo method in the Rating
        Collections.sort(ratingList);

        for(Rating currentRating:ratingList){
            String id=currentRating.getItem();
            String title=MovieDatabase.getTitle(id);
            //System.out.println(+currentRating.getValue()+" "+title);
        }
    }

    public void printAverageRatingsByYearAfterAndGenre(){
        System.out.println("----------NewTest FourthRatings--------------");

        FourthRatings fourthRate = new FourthRatings();


        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");

        System.out.println("Number of raters read in: "+RaterDatabase.size());
        System.out.println("The number of movies in database is "+MovieDatabase.size());

        int year=1990;
        String genre="Drama";
        int minRater=8;

        AllFilters af = new AllFilters();

        af.addFilter(new YearAfterFilter(year));
        af.addFilter(new GenreFilter(genre));

        ArrayList<Rating> filteredList=fourthRate.getAverageRatingsByFilter(minRater, af);

        System.out.println("Number of movies filtered "+filteredList.size());

        Collections.sort(filteredList);

        for(Rating currentRating: filteredList){
            String id=currentRating.getItem();
            String title=MovieDatabase.getTitle(id);
            //System.out.println(currentRating.getValue()+" "+MovieDatabase.getYear(id)+" "+title);
            //System.out.println("    "+MovieDatabase.getGenres(id));
        }
    }

    public void printSimilarRatings(){

        System.out.println("----------NewTest FourthRatings--------------");

        FourthRatings fourthRate = new FourthRatings();


        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");

        System.out.println("Number of raters read in: "+RaterDatabase.size());
        System.out.println("The number of movies in database is "+MovieDatabase.size());

        String raterID="71";
        int numMinimalRaters=5;
        int topSimilarRaters=20;

        ArrayList<Rating> myList=fourthRate.getSimilarRatings(raterID,topSimilarRaters,numMinimalRaters);

        for(int i=0; i<15; i++){
            Rating currentRating=myList.get(i);
            String id=currentRating.getItem();
            System.out.println(currentRating.getValue()+" "+MovieDatabase.getTitle(id));
        }
    }

    public void printSimilarRatingsByGenre(){

        System.out.println("----------NewTest FourthRatings--------------");

        FourthRatings fourthRate = new FourthRatings();


        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");

        System.out.println("Number of raters read in: "+RaterDatabase.size());
        System.out.println("The number of movies in database is "+MovieDatabase.size());

        String raterID="964";
        int numMinimalRaters=5;
        int topSimilarRaters=20;
        String genre="Mystery";
        Filter gF = new GenreFilter(genre);

        ArrayList<Rating> myList=fourthRate.getSimilarRatingsByFilter(raterID,topSimilarRaters,numMinimalRaters, gF);

        for(int i=0; i<15; i++){
            Rating currentRating=myList.get(i);
            String id=currentRating.getItem();
            System.out.println(currentRating.getValue()+" "+MovieDatabase.getTitle(id));
            System.out.println("    "+MovieDatabase.getGenres(id));
        }

    }

    public void printSimilarRatingsByDirector(){
        System.out.println("----------NewTest FourthRatings--------------");

        FourthRatings fourthRate = new FourthRatings();


        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");

        System.out.println("Number of raters read in: "+RaterDatabase.size());
        System.out.println("The number of movies in database is "+MovieDatabase.size());

        String raterID="120";
        int numMinimalRaters=2;
        int topSimilarRaters=10;
        String director="Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        Filter dF = new DirectorsFilter(director);

        ArrayList<Rating> myList=fourthRate.getSimilarRatingsByFilter(raterID,topSimilarRaters,numMinimalRaters, dF);

        for(int i=0; i<myList.size(); i++){
            Rating currentRating=myList.get(i);
            String id=currentRating.getItem();
            System.out.println(currentRating.getValue()+" "+MovieDatabase.getTitle(id));
            System.out.println("    "+MovieDatabase.getDirector(id));
        }

    }

    public void printSimilarRatingsByGenreAndMinutes(){
        System.out.println("----------NewTest FourthRatings--------------");

        FourthRatings fourthRate = new FourthRatings();


        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");

        System.out.println("Number of raters read in: "+RaterDatabase.size());
        System.out.println("The number of movies in database is "+MovieDatabase.size());

        String raterID="168";
        int numMinimalRaters=3;
        int topSimilarRaters=10;
        String genre="Drama";
        int mins_min=80;
        int mins_max=160;

        AllFilters af = new AllFilters();

        af.addFilter(new MinutesFilter(mins_min,mins_max));
        af.addFilter(new GenreFilter(genre));


        ArrayList<Rating> myList=fourthRate.getSimilarRatingsByFilter(raterID,topSimilarRaters,numMinimalRaters, af);

        for(int i=0; i<myList.size(); i++){
            Rating currentRating=myList.get(i);
            String id=currentRating.getItem();
            System.out.println(currentRating.getValue()+" "+MovieDatabase.getTitle(id)+" Mins: "+MovieDatabase.getMinutes(id));
            System.out.println("    "+MovieDatabase.getGenres(id));
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes(){

        System.out.println("----------NewTest FourthRatings--------------");

        FourthRatings fourthRate = new FourthRatings();


        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");

        System.out.println("Number of raters read in: "+RaterDatabase.size());
        System.out.println("The number of movies in database is "+MovieDatabase.size());

        String raterID="314";
        int numMinimalRaters=5;
        int topSimilarRaters=10;
        int year=1975;
        int mins_min=70;
        int mins_max=200;

        AllFilters af = new AllFilters();

        af.addFilter(new MinutesFilter(mins_min,mins_max));
        af.addFilter(new YearAfterFilter(year));


        ArrayList<Rating> myList=fourthRate.getSimilarRatingsByFilter(raterID,topSimilarRaters,numMinimalRaters, af);

        for(int i=0; i<myList.size(); i++){
            Rating currentRating=myList.get(i);
            String id=currentRating.getItem();
            System.out.println(currentRating.getValue()+" "+MovieDatabase.getTitle(id)+" Year: "+MovieDatabase.getYear(id)+" Mins: "+MovieDatabase.getMinutes(id));
        }

    }
}
