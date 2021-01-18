package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    private double seasonRating;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    /**
     * This sets up the average rating for the season.
     */
    public void setSeasonRating() {
        int numberOfRatings = 0;
        double sum = 0;
        for (Double rating : ratings) {
            if (rating != 0) {
                sum = sum + rating;
                numberOfRatings++;
            }
        }
        if (numberOfRatings == 0) {
            this.seasonRating = 0.0;
        } else {
            this.seasonRating = sum / numberOfRatings;
        }
    }

    public double getSeasonRating() {
        return seasonRating;
    }

    /**
     * Adds a rating to the season
     * @param rating a double representing the rating that is to be given
     */
    public void addRating(final double rating) {
        this.ratings.add(rating);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + ", ratings = "
                + ratings
                + '}';
    }
}

