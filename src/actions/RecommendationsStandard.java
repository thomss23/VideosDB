package actions;


public interface RecommendationsStandard {

    /**
     * Returns the first unseen video by the user from the database
     * @param username a string of the user's account name
     * @return a string representing the video's title
     */
    String standard(String username);

    /**
     * Returns the best video unseen by that user.
     * All videos are sorted in descending order
     * by rating and the first of them is chosen, the second criterion being
     * the order of appearance in the database.
     * @param username a string of the user's account name
     * @return a string representing the video's title
     */
    String bestUnseen(String username);


}
