package actions;

/**
 * A set of commands a user can perform on the platform
 */
public interface Commands {

     /**
      * Adds a video to a user's favorites list, if the user has seen the video
      * @param username a user's account name
      * @param title the title of the video
      * @return A message if the video was successfully added or not to the user's favorites
      */
     String setFavorite(String username, String title);

     /**
      * Marks a video as seen. If the user has already seen the video, the number of views of
      * that video by the user increases.
      * When viewing a series, all seasons are considered to be marked as seen.
      * @param username a user's account name
      * @param title the title of the video
      * @return A message with the number of times a user has seen a particular video
      */
     String setView(String username, String title);

     /**
      * A user giving giving a rating to a movie
      * @param username a user's account name
      * @param title the title of the movie
      * @param rating the rating for the particular video
      * @return A message whether the user has successfully rated a particular movie
      */
     String setMovieRating(String username, String title, double rating);

     /**
      * A user giving giving a rating to a TV Show's season
      * @param username a user's account name
      * @param title the title of the TV Show
      * @param season the season number that is wanted to be rated
      * @param rating the rating for the season
      * @return A message whether the user has successfully rated a Tv Show's season
      */
     String setSerialRating(String username,
                                  String title, int season, double rating);
}
