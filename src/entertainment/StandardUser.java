package entertainment;

import common.Constants;
import java.util.ArrayList;
import java.util.Map;

public class StandardUser extends User  {
    public StandardUser(final String username,
                        final Map<String, Integer> history,
                        final ArrayList<String> favoriteVideos) {
        super(username, Constants.STANDARD_SUBSCRIPTION, history, favoriteVideos);
    }

}
