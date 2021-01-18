package entertainment;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.Map;

public final class Actor implements Comparable<Actor> {
    private String name;

    private String careerDescription;

    private ArrayList<String> filmography;

    private Map<ActorsAwards, Integer> awards;

    private int numberOfAwards = 0;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        setNumberOfAwards();
    }

    public int getNumberOfAwards() {
        return this.numberOfAwards;
    }
    private void setNumberOfAwards() {
        awards.forEach((key, value) -> this.numberOfAwards += value);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    @Override
    public String toString() {
        return "Actor{"
                +
                "name='"
                + name
                + '\''
                + '}';
    }

    @Override
    public int compareTo(final Actor o) {
        return this.getName().compareTo(o.getName());
    }
}
