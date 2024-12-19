
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    
    public ArrayList<Movie> loadMovies(String filename){
        
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        CSVParser parser = null;
        try {
            parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        
        //String anID, String aTitle, String aYear, String theGenres
        
        for(CSVRecord currentRow:parser){
            String title=currentRow.get("title");
            String id=currentRow.get("id");
            String year=currentRow.get("year");
            String genre=currentRow.get("genre");
            String director=currentRow.get("director");
            String country=currentRow.get("country");
            String poster=currentRow.get("poster");
            String mins=currentRow.get("minutes");
            int minutes=Integer.parseInt(mins);
            Movie myMovie = new Movie(id,title,year,genre,director,country,poster,minutes);
            movieList.add(myMovie);
        }      
        
        
        return movieList;
    
    }
    
    public int numGenre(ArrayList<Movie> myMovies, String thisGenre){
    
        int number=0;
        for(Movie thisMovie : myMovies){
        
            String genres=thisMovie.getGenres();
            if(genres.contains(thisGenre)){
                number++;
            }
        }
        
        return number;
    }
    
    public int getLongerMovies(ArrayList<Movie> myMovies, int duration){
        
        int number=0;
        
        for(Movie thisMovie : myMovies){
            
            int minutes=thisMovie.getMinutes();
            if(minutes>duration){
                number++;
            }
        }
        
        return number;
    }
    
    public HashMap<String,Integer> buildDirectorMovieMap(ArrayList<Movie> myMovies){
        
        HashMap<String,Integer> myMap= new HashMap<String,Integer>();
        //ArrayList<String> myList= new ArrayList<String>();
        //make a hashmap with <Director name, List of movies they directed>
        
        //int num_movies=0;
        
        for (Movie thisMovie:myMovies){
            // get a list of all directors:
            int num_movies=0;
            String directors=thisMovie.getDirector();
            
            String[] dirArray=directors.split("[,]",0);
                
                for(String currentDir:dirArray){
                    if(!myMap.containsKey(currentDir)){
                        num_movies=1;
                        myMap.put(currentDir,num_movies);
                    }else{
                        int currentMovieCount = myMap.get(currentDir);
                        int currentCount=Integer.valueOf(currentMovieCount);
                        myMap.put(currentDir,currentMovieCount+1);
                        
                        
                    }
                }
            
        }
    
        return myMap;
    }
    
    public int maxNumDirMovies(HashMap<String,Integer> myMap){
        int number=0;
        
        // loop through the hashmap and get the max number of movies by any director:
          for(String director : myMap.keySet()){
                if(myMap.get(director)>number){
                    number=myMap.get(director);
                }
            }  
        
        return number;
    }
    
    public ArrayList<Rater> loadRaters(String filename){
        //process every record from the CSV file with that filename
        //return an arrayList with all the rater data from the file:
        //System.out.println("---------------Starting test load raters----------------");
          
        ArrayList<Rater> raterList = new ArrayList<Rater>();

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        CSVParser parser = null;
        try {
            parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        

        
        for(CSVRecord currentRow:parser){
            String id=currentRow.get("rater_id");
            String movie=currentRow.get("movie_id");
            String Str_rating=currentRow.get("rating");
            double rating=Double.parseDouble(Str_rating);
            //System.out.println("new csv line");
            
            //for the first time, just add the first rater you get
            if(raterList.size()<1){
                
                //System.out.println("Adding first rating "+movie+" "+rating);
                Rater currentRater= new EfficientRater(id);
                
                
                currentRater.addRating(movie,rating);
            
                raterList.add(currentRater);
                
                
            }else{
            
                Rater thisRater=raterList.get(raterList.size()-1);
                String thisId=thisRater.getID();
                    
               
                if(thisId.equals(id)){
                        //System.out.println("the id already exists, added "+movie+" "+rating);
                        //if yes, just add the movie with rating
                        
                        thisRater.addRating(movie,rating);
                        
                }else{
                        // if no, create a new rater profile and add the rating
                        Rater currentRater= new EfficientRater(id);
            
                        currentRater.addRating(movie,rating);
            
                        raterList.add(currentRater);
                        
                }
                
                }
            }
            
        //}
        
        return raterList;
    }
    
    public void testLoadMovies(){
        
        String filename="ratedmoviesfull.csv";
        ArrayList<Movie> myMovies=loadMovies(filename);
        
        for(Movie thisMovie : myMovies){
            System.out.println(thisMovie);
        }
        System.out.println("The number of movies is "+myMovies.size());
        System.out.println("The number of comedies is "+numGenre(myMovies,"Comedy"));
        System.out.println("The number of movies longer than 150 mins is "+getLongerMovies(myMovies,150));
        
        HashMap<String,Integer> myMap=buildDirectorMovieMap(myMovies);
        int maxMoviesDirected=maxNumDirMovies(myMap);
        System.out.println("The most movies made by a director is "+maxMoviesDirected);
        
        int count=0;
        for(String director:myMap.keySet()){
            if(myMap.get(director)==maxMoviesDirected){
                count++;
                System.out.println("The director with max movie count is "+director);
            }
        }
        System.out.println("There are "+count+" directors with max movie count.");
    }
    
    public int getNumRatingsRater(ArrayList<Rater> raterList, String targetID){
        // get the number of ratings from a particular rater:
        int number=0;
        
        
            for (Rater currentRat:raterList){
                String raterID=currentRat.getID();
                if(targetID.equals(raterID)){
                    ArrayList<String>targetRatings=currentRat.getItemsRated();
                    number=targetRatings.size();
                }
                
            }
        
        return number;
    }
    
    public int getMaxNumRatings(ArrayList<Rater> raterList){
        int maxNum=0;
        
            for (Rater currentRat:raterList){
                int numRatings=getNumRatingsRater(raterList, currentRat.getID());
                
                if(numRatings>maxNum){
                    maxNum=numRatings;
                }
            }
        
        return maxNum;
    }
    
    public ArrayList<String> getRatersMaxNum(ArrayList<Rater> raterList){
        // counts how many raters have the max number of ratings
        ArrayList<String> ratersWithMaxRating=new ArrayList<String>();
        int maxNum=getMaxNumRatings(raterList);
        
        for(Rater currentRat:raterList){
            int numRatings=getNumRatingsRater(raterList, currentRat.getID());
            if(numRatings==maxNum){
                ratersWithMaxRating.add(currentRat.getID());
            }
        }
        
        return ratersWithMaxRating;
    }
    
    public int numRatingsMovie(ArrayList<Rater> raterList, String movie){
        int number=0;
        
            for(Rater currentRat:raterList){
                ArrayList<String> ratedMovies=currentRat.getItemsRated();
                
                if(ratedMovies.contains(movie)){
                    number++;
                }
            }
        
        return number;
    }
    
    public int numberOfMoviesRated(ArrayList<Rater> raterList){
        int number=0;
        ArrayList<String>totalMovies=new ArrayList<String>();
            
            for(Rater currentRat:raterList){
                ArrayList<String> ratedMovies=currentRat.getItemsRated();
                
                for(String movie:ratedMovies){
                    if(!totalMovies.contains(movie)){
                    totalMovies.add(movie);
                    }
                }
            }
        number=totalMovies.size();
            
        return number;
    }
    
    public void testLoadRaters(){
        ArrayList<Rater> raterList=loadRaters("ratings.csv");
        System.out.println("Total number of raters is "+raterList.size());
        String targetID="193";
        int numRatings=getNumRatingsRater(raterList,targetID);
        System.out.println("Number of ratings from rater "+targetID+" is "+numRatings);
        
        int maxRating=getMaxNumRatings(raterList);
        
        System.out.println("Maximum number of ratings are "+maxRating);
        
        ArrayList<String> ratersMaxRating=getRatersMaxNum(raterList);
        
        System.out.println("Number of raters with max rating are "+ratersMaxRating.size());
        System.out.println("IDs of raters with max rating are: "+ratersMaxRating);
        
        int numRatersPerItem=numRatingsMovie(raterList,"1798709");
        System.out.println("There were "+numRatersPerItem+" raters that rated this movie.");
        
        int numMoviesRated=numberOfMoviesRated(raterList);
        System.out.println("There were "+numMoviesRated+" rated movies in the list.");
        
        /*
        for(Rater currentRat:raterList){
            String ratersID=currentRat.getID();
            int numberRatings=currentRat.numRatings();
            System.out.println("Rater is "+ratersID+" and number of ratings is "+numberRatings);
            ArrayList<String> ratRatings=currentRat.getItemsRated();    
            
            //for(int i=0; i<ratRatings.size(); i++){
            //        System.out.println("items rated were "+ratRatings.get(i)+" and rating was "+currentRat.getRating(ratRatings.get(i)));
            //    }
        }
        */
    }

}
