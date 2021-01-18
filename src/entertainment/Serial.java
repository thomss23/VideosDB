package entertainment;

import java.util.ArrayList;

public final class Serial extends Video {
    private ArrayList<Season> seasons;
    private double averageRating;


    public Serial(final String title,
                  final int year, final ArrayList<String> genre,
                  final ArrayList<Season> season, final ArrayList<Actor> actors) {
        super(title, year, genre, actors);
        this.seasons = season;
    }

    private void setAverageRating() {
        double sum = 0;
        int numberOfRatedSeasons = 0;
        for (Season season : seasons) {
            season.setSeasonRating();
            sum = sum + season.getSeasonRating();
            numberOfRatedSeasons++;
        }
        if (numberOfRatedSeasons == 0) {
            this.averageRating = 0.0;
        } else {
            this.averageRating = sum / numberOfRatedSeasons;
        }
    }

    /**
     * This returns the average for a serial
     * @return a dobule representing the serial's average rating
     */
    public double getAverageRating() {
        setAverageRating();
        return averageRating;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(final ArrayList<Season> seasons) {
        this.seasons = seasons;
    }
}
