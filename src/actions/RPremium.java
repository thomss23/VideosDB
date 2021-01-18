package actions;

import entertainment.Database;
import entertainment.Genre;
import entertainment.PremiumUser;
import entertainment.User;
import entertainment.Video;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static utils.Utils.findTotalViewsForGenre;
import static utils.Utils.findTotalViewsForVideo;
import static utils.Utils.replaceLowerCase;
import static utils.Utils.sortHashMapByDoubleValue;
import static utils.Utils.sortHashMapByIntValue;
import static utils.Utils.sortHashMapFav;

public final class RPremium implements RecommendationsPremium {

    private final Database database;

    public RPremium(final Database database) {
        this.database = database;
    }

    @Override
    public String popularRec(final String username) {
        HashMap<String, Integer> genreViews = new HashMap<>();
        User user = null;
        for (User foundUser : database.getUsers()) {
            if (foundUser.getUsername().equals(username)) {
                user = foundUser;
            }
        }
        if (user instanceof PremiumUser) {
            for (Genre genre : Genre.values()) {
                int times = findTotalViewsForGenre(genre.toString().toLowerCase(), database);
                genreViews.put(genre.toString().toLowerCase(), times);
            }
            LinkedHashMap<String, Integer> sortedByValueGenre
                    = new LinkedHashMap<>(sortHashMapByIntValue(genreViews, "desc"));
            for (Map.Entry genre : sortedByValueGenre.entrySet()) {
                for (Video video : database.getVideos()) {
                    replaceLowerCase(video.getGenre());
                    if (video.getGenre().contains(genre.getKey())
                            && !user.getHistory().containsKey(video.getTitle())) {
                        return "PopularRecommendation result: " + video.getTitle();
                    }
                }
            }
        } else {
            return "PopularRecommendation cannot be applied!";
        }
        return "PopularRecommendation cannot be applied!";
    }

    @Override
    public String favoriteRec(final String username) {
        HashMap<String, Integer> videoViews = new HashMap<>();
        User user = null;
        for (User foundUser : database.getUsers()) {
            if (foundUser.getUsername().equals(username)) {
                user = foundUser;
            }
        }
        if (user instanceof PremiumUser) {
            for (Video video : database.getVideos()) {
                if (!user.getHistory().containsKey(video.getTitle())) {
                    if (findTotalViewsForVideo(video, database) != 0) {
                        int times = findTotalViewsForVideo(video, database);
                        videoViews.put(video.getTitle(), times);
                    }
                }
            }
            LinkedHashMap<String, Integer> sortedByValueViews
                    = new LinkedHashMap<>(sortHashMapFav(videoViews));
            for (Map.Entry sortedVideo : sortedByValueViews.entrySet()) {
                for (Video video: database.getVideos()) {
                    if (sortedVideo.getKey().equals(video.getTitle())) {
                        return "FavoriteRecommendation result: " + video.getTitle();
                    }
                }
            }
        } else {
            return "FavoriteRecommendation cannot be applied!";
        }
        return "FavoriteRecommendation cannot be applied!";
    }

    @Override
    public String search(final String username, final String genre) {
        HashMap<String, Double> videosNotSeen = new HashMap<>();
        ArrayList<String> foundVideos = new ArrayList<>();
        User user = null;
        for (User foundUser : database.getUsers()) {
            if (foundUser.getUsername().equals(username)) {
                user = foundUser;
            }
        }
        if (user instanceof PremiumUser) {
            for (Video video : database.getVideos()) {
                replaceLowerCase(video.getGenre());
                if (!user.getHistory().containsKey(video.getTitle())
                        && video.getGenre().contains(genre.toLowerCase())) {
                    videosNotSeen.put(video.getTitle(), video.getVideoRating());
                }
            }
            LinkedHashMap<String, Double> sortedByValueViews
                    = new LinkedHashMap<>(sortHashMapByDoubleValue(videosNotSeen, "desc"));
            sortedByValueViews.forEach((key, value) -> foundVideos.add((String) key));
            if (foundVideos.size() == 0) {
                return "SearchRecommendation cannot be applied!";
            } else {
                Collections.sort(foundVideos);
                return "SearchRecommendation result: " + foundVideos.toString();
            }
        } else {
            return "SearchRecommendation cannot be applied!";
        }

    }
}
