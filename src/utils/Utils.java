package utils;

import actor.ActorsAwards;
import common.Constants;
import entertainment.Database;
import entertainment.Genre;
import entertainment.User;
import entertainment.Video;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * The class contains static methods that helps with parsing.
 *
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     *
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> Genre.ACTION;
            case "adventure" -> Genre.ADVENTURE;
            case "drama" -> Genre.DRAMA;
            case "comedy" -> Genre.COMEDY;
            case "crime" -> Genre.CRIME;
            case "romance" -> Genre.ROMANCE;
            case "war" -> Genre.WAR;
            case "history" -> Genre.HISTORY;
            case "thriller" -> Genre.THRILLER;
            case "mystery" -> Genre.MYSTERY;
            case "family" -> Genre.FAMILY;
            case "horror" -> Genre.HORROR;
            case "fantasy" -> Genre.FANTASY;
            case "science fiction" -> Genre.SCIENCE_FICTION;
            case "action & adventure" -> Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> Genre.SCI_FI_FANTASY;
            case "animation" -> Genre.ANIMATION;
            case "kids" -> Genre.KIDS;
            case "western" -> Genre.WESTERN;
            case "tv movie" -> Genre.TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     *
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    /**
     * Transforms an array of JSON's into an array of strings
     *
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     *
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwards  as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     *
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }

    /**
     * Sorts a given hashmap
     * @param map to be sorted
     * @param sortType the sorting criteria
     * @return a sorted hashmap of type string,double
     */
    public static LinkedHashMap<String, Double>
    sortHashMapByDoubleValue(final HashMap<String, Double> map, final String sortType) {
        Set<Map.Entry<String, Double>> entries = map.entrySet();
        if (sortType.equals("asc")) {
            Comparator<Map.Entry<String, Double>> valueComparator = new Comparator<>() {
                @Override
                public int compare(final Map.Entry<String, Double> o1,
                                   final Map.Entry<String, Double> o2) {
                    Double v1 = o1.getValue();
                    Double v2 = o2.getValue();
                    String s1 = o1.getKey();
                    String s2 = o2.getKey();
                    if (v1.equals(v2)) {
                        return s1.compareTo(s2);
                    } else {
                        return v1.compareTo(v2);
                    }
                }
            };
            List<Map.Entry<String, Double>> listOfEntries = new ArrayList<>(entries);
            listOfEntries.sort(valueComparator);
            LinkedHashMap<String, Double> sortedByValueGenre
                    = new LinkedHashMap<>(listOfEntries.size());
            for (Map.Entry<String, Double> entry : listOfEntries) {
                sortedByValueGenre.put(entry.getKey(), entry.getValue());
            }
            return sortedByValueGenre;
        } else {
            Comparator<Map.Entry<String, Double>> valueComparator = new Comparator<>() {
                @Override
                public int compare(final Map.Entry<String, Double> o1,
                                   final Map.Entry<String, Double> o2) {
                    Double v1 = o1.getValue();
                    Double v2 = o2.getValue();
                    String s1 = o1.getKey();
                    String s2 = o2.getKey();
                    if (v1.equals(v2)) {
                        return s2.compareTo(s1);
                    } else {
                        return v2.compareTo(v1);
                    }
                }
            };

            List<Map.Entry<String, Double>> listOfEntries = new ArrayList<>(entries);
            listOfEntries.sort(valueComparator);
            LinkedHashMap<String, Double> sortedByValueGenre
                    = new LinkedHashMap<>(listOfEntries.size());
            for (Map.Entry<String, Double> entry : listOfEntries) {
                sortedByValueGenre.put(entry.getKey(), entry.getValue());
            }
            return sortedByValueGenre;
        }
    }

    /**
     * Sorts a given hashmap
     * @param map to be sorted
     * @param order the sorting criteria
     * @return a sorted hashmap of type string,integer
     */
    public static LinkedHashMap<String, Integer>
    sortHashMapByIntValue(final HashMap<String, Integer> map, final String order) {
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        if (order.equals("asc")) {
            Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<>() {
                @Override
                public int compare(final Map.Entry<String, Integer> o1,
                                   final Map.Entry<String, Integer> o2) {
                    Integer v1 = o1.getValue();
                    Integer v2 = o2.getValue();
                    String s1 = o1.getKey();
                    String s2 = o2.getKey();
                    if (v1.equals(v2)) {
                        return s1.compareTo(s2);
                    } else {
                        return v1.compareTo(v2);
                    }
                }
            };
            List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(entries);
            listOfEntries.sort(valueComparator);
            LinkedHashMap<String, Integer> sortedByValueGenre
                    = new LinkedHashMap<>(listOfEntries.size());
            for (Map.Entry<String, Integer> entry : listOfEntries) {
                sortedByValueGenre.put(entry.getKey(), entry.getValue());
            }
            return sortedByValueGenre;
        } else {
            Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<>() {
                @Override
                public int compare(final Map.Entry<String, Integer> o1,
                                   final Map.Entry<String, Integer> o2) {
                    Integer v1 = o1.getValue();
                    Integer v2 = o2.getValue();
                    String s1 = o1.getKey();
                    String s2 = o2.getKey();
                    if (v1.equals(v2)) {
                        return s2.compareTo(s1);
                    } else {
                        return v2.compareTo(v1);
                    }
                }
            };
            List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(entries);
            listOfEntries.sort(valueComparator);
            LinkedHashMap<String, Integer> sortedByValueGenre
                    = new LinkedHashMap<>(listOfEntries.size());
            for (Map.Entry<String, Integer> entry : listOfEntries) {
                sortedByValueGenre.put(entry.getKey(), entry.getValue());
            }
            return sortedByValueGenre;
        }
    }

    /**
     * Finds total views for a specific genre in the database
     * @param genre to search for
     * @param database containing the videos
     * @return an integer representing the number of times a specific genre has been viewed
     */
    public static int findTotalViewsForGenre(final String genre, final Database database) {
        int times = 0;
        for (Video video : database.getVideos()) {
            if (video.getGenre().contains(genre)) {
                times++;
            }
        }
        return times;
    }

    /**
     * Finds total views for a specific video in the database
     * @param wantedVideo to search for
     * @param database containing the videos
     * @return an integer representing the number of times a specific video has been viewed
     */
    public static int findTotalViewsForVideo(final Video wantedVideo, final Database database) {
        int times = 0;
        for (User user : database.getUsers()) {
            if (user.getFavoriteVideos().contains(wantedVideo.getTitle())) {
                times++;
            }
        }
        return times;
    }

    /**
     * Replace all characters to lower case characters in a list of strings
     * @param strings to be lowercased
     */
    public static void replaceLowerCase(final ArrayList<String> strings) {
        ListIterator<String> iterator = strings.listIterator();
        while (iterator.hasNext()) {
            iterator.set(iterator.next().toLowerCase());
        }
    }
    /**
     * Sorts a hashmap
     * @param map to be sorted
     * @return a sorted hashmap of type String,Integer
     */
    public static LinkedHashMap<String, Integer>
    sortHashMapFav(final HashMap<String, Integer> map) {
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1,
                               final Map.Entry<String, Integer> o2) {
                Integer v1 = o1.getValue();
                Integer v2 = o2.getValue();
                return v2.compareTo(v1);
            }
        };
        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, valueComparator);
        LinkedHashMap<String, Integer> sortedByValueGenre
                = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<String, Integer> entry : listOfEntries) {
            sortedByValueGenre.put(entry.getKey(), entry.getValue());
        }
        return sortedByValueGenre;
    }


}
