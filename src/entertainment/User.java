package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User  {
    private final String username;

    private final ArrayList<Movie> ratedMovies;

    private final HashMap<String, ArrayList<Integer>> ratedSerials;

    private final String subscriptionType;

    private final Map<String, Integer> history;

    private final ArrayList<String> favoriteVideos;

    private int numberOfRatings;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history, final ArrayList<String> favoriteVideos) {

        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteVideos = favoriteVideos;
        this.ratedMovies = new ArrayList<>();
        this.ratedSerials = new HashMap<>();
        this.numberOfRatings = 0;
    }


    public final int getNumberOfRatings() {
        return numberOfRatings;
    }

    public final String getUsername() {
        return username;
    }

    public final Map<String, Integer> getHistory() {
        return history;
    }

    public final ArrayList<String> getFavoriteVideos() {
        return favoriteVideos;
    }

    /**
     * This method allows the user to set
     * a video to their favorites list
     * @param video a string representing the title of the video
     * @return a message if the user successfully added
     * the title to their favorites list or not
     */
    public final String favorite(final String video) {
        for (String vid : getFavoriteVideos()) {
            if (vid.equals(video)) {
                return "error -> " + video + " is already in favourite list";
            }
        }
        for (Map.Entry vid : getHistory().entrySet()) {
            if (vid.getKey().equals(video)) {
                getFavoriteVideos().add(video);
                return "success -> " + video + " was added as favourite";
            }
        }
        return "error -> " + video + " is not seen";
    }

    /**
     * This method returns the number of times a user
     * has viewed a specific video
     * @param video a string representing the title of
     *              the particular video
     * @return a message with the number of times
     * a user has seen a particular video
     */
    public final String view(final String video) {
        int times = 0;
        for (Map.Entry vid : getHistory().entrySet()) {
            if (vid.getKey().equals(video)) {
                 times = (Integer) vid.getValue() + 1;
                getHistory().replace(video, times);
                return "success -> " + video + " was viewed with total views of " + times;
            }
        }
        getHistory().put(video, 1);
        return "success -> " + video + " was viewed with total views of 1";
    }

    /**
     * This method allows the user to give a rating to a particular movie
     * @param video a string representing the movie's title
     * @param rating a double representing the rating
     * @return a message if a user has successfully managed to
     *         give a rating to a movie or not
     */
    public final String rating(final Movie video, final double rating) {
        boolean hasRated = false;
        for (Video ratedVideo : ratedMovies) {
            if (ratedVideo.equals(video)) {
                hasRated = true;
                break;
            }
        }
        if (hasRated) {
            return "error -> " + video.getTitle() + " has been already rated";
        }
        for (Map.Entry vid : getHistory().entrySet()) {
            if (vid.getKey().equals(video.getTitle())) {
                video.setRating(rating + video.getRating());
                video.setNoOfRatings(video.getNoOfRatings() + 1);
                ratedMovies.add(video);
                this.numberOfRatings++;
                return "success -> " + video.getTitle()
                        + " was rated with "
                        + rating + " by "
                        + this.getUsername();
            }
        }
        if (!history.containsKey(video.getTitle())) {
                return "error -> " + video.getTitle() + " is not seen";
        }
        return null;
    }

    /**
     * An overloaded method allowing the user to give a rating to a specific serial
     * @param video a string representing the serial's title
     * @param season an integer representing the serial's season to be rated
     * @param rating a double representing the rating to be applied
     * @return a message if a user has successfully managed to
     *         give a rating to the serial or not
     */
    public final String rating(final Serial video, final int season, final double rating) {
        boolean hasRated = false;
        ArrayList<Integer> itemsList = ratedSerials.get(video.getTitle());
        if (itemsList == null) {
            itemsList = new ArrayList<>();
        }
        for (Integer integer : itemsList) {
            if (season == integer) {
                hasRated = true;
                break;
            }
        }
        if (hasRated) {
            return "error -> " + video.getTitle() + " has been already rated";
        }
        for (Map.Entry vid : getHistory().entrySet()) {
            if (vid.getKey().equals(video.getTitle())) {
                video.getSeasons().get(season).addRating(rating);
                if (!itemsList.contains(season)) {
                    itemsList.add(season);
                    ratedSerials.put(video.getTitle(), itemsList);
                }
                this.numberOfRatings++;
                return "success -> "
                        + video.getTitle()
                        + " was rated with " + rating + " by " + this.getUsername();
            }
        }
        if (!history.containsKey(video.getTitle())) {
            return "error -> " + video.getTitle() + " is not seen";
        }
        return null;
    }
}
