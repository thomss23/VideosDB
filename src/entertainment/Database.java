package entertainment;

import common.Constants;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.ArrayList;

public final class Database  {

     private final ArrayList<User> users;
     private final ArrayList<Video> videos;


    public Database() {
        this.users = new ArrayList<>();
        this.videos = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    /**
     * A method which sets up the database using the information that was parsed
     * from the JSON files.
     * @param input The object containing all the components needed for the database
     */
    public void setDatabase(final Input input) {

        for (UserInputData inputUsers : input.getUsers()) {
            if (inputUsers.getSubscriptionType().equals(Constants.STANDARD_SUBSCRIPTION)) {
                this.users.add(new StandardUser(inputUsers.getUsername(),
                        inputUsers.getHistory(), inputUsers.getFavoriteMovies()));
            } else {
                this.users.add(new PremiumUser(inputUsers.getUsername(),
                        inputUsers.getHistory(), inputUsers.getFavoriteMovies()));
            }
        }
        for (MovieInputData movie : input.getMovies()) {
            ArrayList<Actor> actors = new ArrayList<>();
            for (ActorInputData actor : input.getActors()) {
               for (String played : actor.getFilmography()) {
                   if (movie.getTitle().equals(played)) {
                       actors.add(new Actor(actor.getName(),
                               actor.getCareerDescription(),
                               actor.getFilmography(), actor.getAwards()));
                   }
               }
            }
            this.videos.add(new Movie(movie.getTitle(),
                    movie.getYear(), movie.getGenres(), movie.getDuration(), actors));
        }
        for (SerialInputData serial : input.getSerials()) {
            ArrayList<Actor> actors = new ArrayList<>();
            for (ActorInputData actor : input.getActors()) {
                for (String played : actor.getFilmography()) {
                    if (serial.getTitle().equals(played)) {
                        actors.add(new Actor(actor.getName(),
                                actor.getCareerDescription(),
                                actor.getFilmography(), actor.getAwards()));
                    }
                }
            }
            this.videos.add(new Serial(serial.getTitle(),
                    serial.getYear(), serial.getGenres(), serial.getSeasons(), actors));
        }
    }
}
