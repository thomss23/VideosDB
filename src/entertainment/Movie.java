package entertainment;

import java.util.ArrayList;

public final class Movie extends Video {

    private int duration;
    private double rating;
    private int noOfRatings;
    private double averageRating;


    public Movie(final String title, final int year,
                final ArrayList<String> genre, final int duration, final ArrayList<Actor> actors) {
        super(title, year, genre, actors);
        this.duration = duration;
        this.rating = 0;
        this.noOfRatings = 0;
    }

    /**
     * Sets the arithmetic average rating for the movie
     * @return a double which represents the average rating
     */
    public double getAverageRating() {
        setAverageRating();
        return averageRating;
    }

    private void setAverageRating() {
        if (noOfRatings == 0) {
            this.averageRating = 0.0;
        } else {
            this.averageRating = rating / noOfRatings;
        }
    }

    public int getNoOfRatings() {
        return noOfRatings;
    }

    public void setNoOfRatings(final int noOfRatings) {
        this.noOfRatings = noOfRatings;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }
}
