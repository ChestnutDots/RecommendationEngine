public class Main {
    public static void main(String[] args) {

        //MovieRunnerWithFilters class sorts movies and presents recommendations based on selected filters
        MovieRunnerWithFilters movieRunner = new MovieRunnerWithFilters();
        movieRunner.printAverageRatings();
        movieRunner.printAverageRatingsByDirectors();
        movieRunner.printAverageRatingsByGenre();
        movieRunner.printAverageRatingsByDirectorsAndMinutes();
        movieRunner.printAverageRatingsByYear();
        movieRunner.printAverageRatingsByYearAfterAndGenre();

        // MovieRunnerSimilarRatings class takes into account other user data and weighs it by similarity to current user ratings
        MovieRunnerSimilarRatings movieRunnerSimilarRatings = new MovieRunnerSimilarRatings();
        movieRunnerSimilarRatings.printAverageRatings();
        movieRunnerSimilarRatings.printAverageRatingsByYearAfterAndGenre();
        movieRunnerSimilarRatings.printSimilarRatingsByYearAfterAndMinutes();
    }
}
