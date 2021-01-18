package actions;


import fileio.ActionInputData;

import java.util.ArrayList;

public interface QueryUser {

    /**
     * returns the first N users sorted by the number of ratings they gave (the most active users)
     * @param action the action object containing information about the type of query
     * @return a list of users
     */

    ArrayList<String> numberOfRatings(ActionInputData action);

}
