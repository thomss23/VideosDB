package actions;

import entertainment.Database;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import entertainment.Video;

public final class QCommands implements Commands {

    private Database database;

    public QCommands(final Database database) {
        this.database = database;
    }

    /**
     * Adds a video to a user's favorites list, if the user has seen the video
     * @param username a user's account name
     * @param title the title of the video
     * @return A message if the video was successfully added or not to the user's favorites
     */
    public String setFavorite(final String username, final String title) {
        String message = null;
        for (User user : database.getUsers()) {
            if (user.getUsername().equals(username)) {
                message = user.favorite(title);
            }
        }
        return message;
    }

    /**
     * Marks a video as seen. If the user has already seen the video, the number of views of
     * that video by the user increases.
     * When viewing a series, all seasons are considered to be marked as seen.
     * @param username a user's account name
     * @param title the title of the video
     * @return A message with the number of times a user has seen a particular video
     */

    public String setView(final String username, final String title) {
        String message = null;
        for (User user : database.getUsers()) {
            if (user.getUsername().equals(username)) {
                message = user.view(title);
            }
        }
        return message;

    }

    /**
     * A user giving giving a rating to a movie
     * @param username a user's account name
     * @param title the title of the movie
     * @param rating the rating for the particular video
     * @return A message whether the user has successfully rated a particular movie
     */
    public String setMovieRating(final String username, final String title, final double rating) {
        String message = null;
        for (User user : database.getUsers()) {
            if (user.getUsername().equals(username)) {
                for (Video movie : database.getVideos()) {
                    if (movie.getTitle().equals(title)) {
                        Movie foundMovie = (Movie) movie;
                        message = user.rating(foundMovie, rating);
                    }
                }
            }
        }
        return message;
    }
    /**
     * A user giving giving a rating to a TV Show's season
     * @param username a user's account name
     * @param title the title of the TV Show
     * @param season the season number that is wanted to be rated
     * @param rating the rating for the season
     * @return A message whether the user has successfully rated a Tv Show's season
     */

    public String setSerialRating(final String username,
                                  final String title, final int season, final double rating) {
        String message = null;
        for (User user : database.getUsers()) {
            if (user.getUsername().equals(username)) {
                for (Video serial :database.getVideos()) {
                    if (serial.getTitle().equals(title)) {
                        Serial foundSerial = (Serial) serial;
                        message = user.rating(foundSerial, season, rating);
                    }
                }
            }
        }
        return message;
    }
}
