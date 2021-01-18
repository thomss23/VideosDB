package actions;

import fileio.ActionInputData;

import java.util.ArrayList;

public interface QueryVideo {

    /**
     * Returns the first N videos sorted by rating.
     * For series, the rating is calculated
     * as the average of all seasons, provided that at least one season
     * has a rating, those without a rating having 0 as their score
     * videos that have not received a rating
     * will not be taken into account
     * @param rating an object containing all the information
     *               needed for the query
     * @return a list of videos
     */
    ArrayList<String> sortRating(ActionInputData rating);

    /**
     * Returns the first N videos sorted by the number
     * of appearances in users' favorite video lists
     * @param favorite an object containing all
     *                 the information needed for the query
     * @return a list of videos
     */
    Object sortFavorite(ActionInputData favorite);

    /**
     * Returns the first N videos sorted by duration.
     * For series, the sum of the seasons is considered.
     * @param longest an object containing all the information
     *                needed for the query
     * @return a list of videos
     */
    ArrayList<String> sortLongest(ActionInputData longest);

    /**
     * Returns the first N videos sorted by number of views.
     * In the case of seasons,
     * the entire series is taken as the number of views,
     * not independently by seasons.
     * @param mostViewed an object containing all the information
     *                   needed for the query
     * @return a list of videos
     */
    ArrayList<String> sortMostViewed(ActionInputData mostViewed);

}
