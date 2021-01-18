package actions;

public interface RecommendationsPremium {

    /**
     * Returns the first unseen video of the most popular genre
     * (videos are browsed according to the order in the database).
     * The popularity of the genre is calculated
     * from the total number of views of videos of that genre. If all the videos of the most popular
     * genre were seen by the user, then it proceede to the next most popular genre.
     * The process resumes until the first video that was not viewed in the database is found.
     * @param username a string of the user's account name
     * @return a string which represents the video's title
     */
    String popularRec(String username);

    /**
     * Returns the video that is most common in the list of favorites
     * (not to be seen by the user for whom the recommendation applies) of all users,
     * the second criterion being the order of appearance in the database.
     * The video must exist in at least one list of users' favorite videos.
     * @param username a string of the user's account name
     * @return a string which represents the video's title
     */
    String favoriteRec(String username);

    /**
     * Returns all videos not seen by the user of a certain genre, given as an input filter.
     * Sorting is done in ascending order by rating, the second criterion being the name.
     * @param username a string of the user's account name
     * @param genre a string representing a video genre
     * @return a string which consists of video titles
     */
    String search(String username, String genre);

}
