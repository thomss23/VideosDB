package actions;

import common.Constants;
import entertainment.Actor;
import entertainment.Database;
import entertainment.Video;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.Utils.replaceLowerCase;
import static utils.Utils.sortHashMapByDoubleValue;

public final class QActor implements QueryActor {

    private final Database database;


    public QActor(final Database database) {
        this.database = database;
    }

    @Override
    public ArrayList<String> average(final int n, final String sortType) {
        ArrayList<String> actorsToSort = new ArrayList<>();
        ArrayList<String> foundActors = new ArrayList<>();
        HashMap<String, Double> map = new HashMap<>();
        for (Video video : database.getVideos()) {
            for (Actor actor : video.getActors()) {
                if (!actorsToSort.contains(actor.getName())) {
                    actorsToSort.add(actor.getName());
                }
            }
        }
        for (String actor: actorsToSort) {
            if (actorAverageRating(actor, database) != 0) {
                map.put(actor, actorAverageRating(actor, database));
            }

        }
        LinkedHashMap<String, Double> sortedByValue =
                new LinkedHashMap<>(sortHashMapByDoubleValue(map, sortType));
        for (Map.Entry actor : sortedByValue.entrySet()) {
            foundActors.add((String) actor.getKey());
        }
        if (foundActors.size() < n) {
                return foundActors;
            }
        return new ArrayList<>(foundActors.subList(0, n));
    }

    @Override
    public ArrayList<String> awards(final ActionInputData awards) {
        ArrayList<Actor> foundActors = new ArrayList<>();
        ArrayList<String> foundActorsString2 = new ArrayList<>();
        for (Video video : database.getVideos()) {
            for (Actor actor : video.getActors()) {
                ArrayList<String> actorAwards = new ArrayList<String>();
                ArrayList<String> toSearchAwards =
                        new ArrayList<>(awards.getFilters().get(Constants.FILTER));
                replaceLowerCase(toSearchAwards);
                for (Map.Entry actorAwardFromMap : actor.getAwards().entrySet()) {
                    actorAwards.add(actorAwardFromMap.getKey().toString().toLowerCase());
                }
                for (String s : actorAwards) {
                    toSearchAwards.remove(s);
                }
                if (toSearchAwards.isEmpty()) {
                    if (!foundActorsString2.contains(actor.getName())) {
                        foundActorsString2.add(actor.getName());
                        foundActors.add(actor);
                    }
                }
            }
        }
        if (awards.getSortType().equals("asc")) {
            foundActors.sort(new Comparator<Actor>() {
                @Override
                public int compare(final Actor o1, final Actor o2) {
                    if (o1.getNumberOfAwards() == o2.getNumberOfAwards()) {
                        return o1.getName().compareTo(o2.getName());
                    } else {
                        return Integer.compare(o1.getNumberOfAwards(), o2.getNumberOfAwards());
                    }
                }
            });
            ArrayList<String> foundActorsString = new ArrayList<>();
            for (Actor actor : foundActors) {
                foundActorsString.add(actor.getName());
            }
            return foundActorsString;
        } else {
                foundActors.sort(new Comparator<Actor>() {
                    @Override
                    public int compare(final Actor o1, final Actor o2) {
                        if (o1.getNumberOfAwards() == o2.getNumberOfAwards()) {
                            return o2.getName().compareTo(o1.getName());
                        } else {
                            return Integer.compare(o2.getNumberOfAwards(), o1.getNumberOfAwards());
                        }
                    }
                });
            ArrayList<String> foundActorsString = new ArrayList<>();
            for (Actor actor : foundActors) {
                foundActorsString.add(actor.getName());
            }
            return foundActorsString;
            }
        }

    @Override
    public ArrayList<String> filterDescription(final ActionInputData filters) {
        ArrayList<String> foundActors = new ArrayList<>();
        for (Video video : database.getVideos()) {
            for (Actor actor : video.getActors()) {
                ArrayList<String> searchFilters = new ArrayList<>();
                for (String filterWord : filters.getFilters().get(2)) {
                    searchFilters.add(filterWord.toLowerCase());
                }
                String  text = actor.getCareerDescription();
                String[] actorDescription = text.split("\\P{L}+");
                for (String s : actorDescription) {
                    searchFilters.remove(s.toLowerCase());
                }
                if (!foundActors.contains(actor.getName()) && searchFilters.isEmpty()) {
                    foundActors.add(actor.getName());
                }
            }
        }
        Collections.sort(foundActors);
        if (!filters.getSortType().equals("asc")) {
            Collections.reverse(foundActors);
        }
        return foundActors;
    }

    /**
     * This method provides an actor's average rating
     * @param actor an actor's name
     * @param database the database containing all video related components
     * @return a double which represents the actor's rating
     */
    public static double actorAverageRating(final String actor, final Database database) {
        double totalGrade = 0;
        int numberOfVideos = 0;
        for (Video video : database.getVideos()) {
            if (hasActor(actor, video.getActors()) && video.getVideoRating() != 0) {
                totalGrade = totalGrade + video.getVideoRating();
                numberOfVideos++;
            }
        }
        if (numberOfVideos == 0) {
            return 0.0;
        } else {
            return totalGrade / numberOfVideos;
        }
    }

    /**
     * This method searches the database for a certain actor
     * @param toFindActor the name of the wanted actor
     * @param actors a list of all the actors in the database
     * @return a boolean whether the actor exists or not in the database
     */
    public static boolean hasActor(final String toFindActor, final ArrayList<Actor> actors) {
        for (Actor actor : actors) {
            if (actor.getName().equals(toFindActor)) {
                return true;
            }
        }
        return false;
    }
}
