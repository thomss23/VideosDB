package entertainment;

import common.Constants;

import java.util.ArrayList;
import java.util.Map;

public class PremiumUser extends User {

    public PremiumUser(final String username,
                       final Map<String, Integer> history, final ArrayList<String> favoriteVideos) {
        super(username, Constants.PREMIUM_SUBSCRIPTION, history, favoriteVideos);
    }

}
