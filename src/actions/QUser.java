package actions;

import entertainment.Database;
import entertainment.User;
import fileio.ActionInputData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import static utils.Utils.sortHashMapByIntValue;

public final class QUser implements QueryUser {

    private final Database database;

    public QUser(final Database database) {
        this.database = database;
    }

    @Override
    public ArrayList<String> numberOfRatings(final ActionInputData action) {

        HashMap<String, Integer> sortedMap = new HashMap<>();
        ArrayList<String> foundUsers = new ArrayList<>();
        int n = action.getNumber();
        for (User user :database.getUsers()) {
            if (user.getNumberOfRatings() != 0) {
                sortedMap.put(user.getUsername(), user.getNumberOfRatings());
            }
        }
        LinkedHashMap<String, Integer> sortedByValue
                = new LinkedHashMap<>(sortHashMapByIntValue(sortedMap, action.getSortType()));
        sortedByValue.forEach((key, value) -> foundUsers.add((String) key));
        if (foundUsers.size() < n) {
            return foundUsers;
        }
        return new ArrayList<>(foundUsers.subList(0, n));
    }
}
