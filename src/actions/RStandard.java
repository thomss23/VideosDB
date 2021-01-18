package actions;

import entertainment.Database;
import entertainment.User;
import entertainment.Video;

import java.util.ArrayList;
import java.util.Collections;


public final class RStandard implements RecommendationsStandard {

    private final Database database;

    public RStandard(final Database database) {
        this.database = database;
    }


    @Override
    public String standard(final String username) {
       User user = null;
        for (User foundUser : database.getUsers()) {
            if (foundUser.getUsername().equals(username)) {
                user = foundUser;
            }
        }
        for (Video video : database.getVideos()) {
            assert user != null;
            if (!user.getHistory().containsKey(video.getTitle())) {
                return "StandardRecommendation result: " + video.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    @Override
    public String bestUnseen(final String username) {
        ArrayList<Video> sortedVideos = new ArrayList<>(database.getVideos());
        Collections.sort(sortedVideos);
        User user = null;
        for (User foundUser : database.getUsers()) {
            if (foundUser.getUsername().equals(username)) {
                user = foundUser;
            }
        }
        if (user == null) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }
        for (Video sortedVideo : sortedVideos) {
            for (Video video : database.getVideos()) {
                if (!user.getHistory().containsKey(video.getTitle()) && sortedVideo.equals(video)) {
                    return "BestRatedUnseenRecommendation result: " + video.getTitle();
                }
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }
}
