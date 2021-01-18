package entertainment;

import java.util.ArrayList;

public class Video implements Comparable<Video> {

    private String title;
    private int year;
    private ArrayList<String> genre;
    private final ArrayList<Actor> actors;
    private double videoRating;

    public Video(final String title, final int year,
                 final ArrayList<String> genre, final ArrayList<Actor> actors) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.actors = actors;
    }

    /**
     * This method returns the average of a certain video
     * @return a double representing the video's rating
     */
    public final double getVideoRating() {
        if (this instanceof Movie) {
            videoRating =   ((Movie) this).getAverageRating();
        } else {
            if (this instanceof Serial) {
                videoRating = ((Serial) this).getAverageRating();
            }
        }
        return videoRating;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(final String title) {
        this.title = title;
    }

    public final int getYear() {
        return year;
    }

    public final void setYear(final int year) {
        this.year = year;
    }

    public final ArrayList<String> getGenre() {
        return genre;
    }

    public final void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }

    public final ArrayList<Actor> getActors() {
        return actors;
    }

    @Override
    public final int compareTo(final Video o) {
            return (int) (o.getVideoRating() - this.videoRating);
    }
}
