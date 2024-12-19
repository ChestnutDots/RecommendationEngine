import lib.Filter;

/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DirectorsFilter implements Filter {
    private String myDir="";
    
    public DirectorsFilter(String director){
        myDir=director;
    }
    
    public boolean satisfies(String id){
        String[] directors=myDir.split(",");
        
        //look if the movie has any of the directors of interest:
        for(int i=0; i<directors.length; i++){
            if(MovieDatabase.getDirector(id).contains(directors[i])){
                return true;
            }
        }
        
        return false;
    }
}
