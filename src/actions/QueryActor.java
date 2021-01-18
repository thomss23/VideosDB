package actions;

import fileio.ActionInputData;

import java.util.ArrayList;

public interface QueryActor {
    /**
     * This method returns the first N actors (N given in the query)
     * sorted by the average ratings of the
     * movies and series in which they played. The average is arithmetic
     * and is calculated for all videos
     * (with a total rating other than 0) in which they played.
     * @param n the number of actors given in the query
     * @param sortType the sorting criteria(ascending or descending order)
     * @return a list of the actors
     */
    ArrayList<String> average(int n, String sortType);

    /**
     * all actors with the prizes mentioned in the query.
     * The actors must have received all the required awards,
     * not just some of them.
     * The sorting will be done according to the number of prizes of each actor,
     * according to the order criteria specified in the input.
     * @param awards an action object that contains a list of prizes
     * @return a list of actors
     */
    ArrayList<String> awards(ActionInputData awards);

    /**
     * filters the actors from their description
     * using the keywords given in the query.
     * Sorting will be done in alphabetical
     * order of the names of the actors,
     * and the order criteria is specified in the input.
     * @param filters an action object that contains a list of the filters to be used in the query
     * @return a list of actors
     */
    ArrayList<String> filterDescription(ActionInputData filters);


}
