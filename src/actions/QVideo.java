package actions;


import common.Constants;
import entertainment.Database;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;
import entertainment.User;
import entertainment.Video;
import fileio.ActionInputData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static utils.Utils.replaceLowerCase;
import static utils.Utils.sortHashMapByIntValue;

public final class QVideo implements QueryVideo {
    private final Database database;

    public QVideo(final Database database) {
        this.database = database;
    }

    @Override
    public ArrayList<String> sortRating(final ActionInputData rating) {
        ArrayList<Video> videos = new ArrayList<>(database.getVideos());
        ArrayList<String> foundVideos = new ArrayList<>();
        int n = rating.getNumber();
        Collections.sort(videos);
        for (Video video : videos) {
            if (rating.getObjectType().equals(Constants.MOVIES)) {
                if (video instanceof Movie) {
                    if (video.getVideoRating() != 0 && checkFilters(rating, video)) {
                        foundVideos.add(video.getTitle());
                    }
                }
            } else {
                if (video instanceof Serial) {
                    if (video.getVideoRating() != 0 && checkFilters(rating, video)) {
                        foundVideos.add(video.getTitle());
                    }
                }
            }
        }
        Collections.sort(foundVideos);
        if (!rating.getSortType().equals("asc")) {
            Collections.reverse(foundVideos);
        }
        if (foundVideos.size() < n) {
            return foundVideos;
        }
        return new ArrayList<>(foundVideos.subList(0, n));
    }

    @Override
    public ArrayList<String> sortFavorite(final ActionInputData favorite) {
        HashMap<String, Integer> map = new HashMap<>();
        int n = favorite.getNumber();

        for (Video video : database.getVideos()) {
            addToMap(map, video, favorite, findTotalFavViewsForVideo(database, video));
        }

        return findVideos(n, map, favorite);
    }

    @Override
    public ArrayList<String> sortLongest(final ActionInputData longest) {
        HashMap<String, Integer> map = new HashMap<>();
        int n = longest.getNumber();
        for (Video video : database.getVideos()) {
            addToMap(map, video, longest, findTotalDurationForVideo(database, video));
        }

        return findVideos(n, map, longest);
    }

    @Override
    public ArrayList<String> sortMostViewed(final ActionInputData mostViewed) {
        HashMap<String, Integer> map = new HashMap<>();
        int n = mostViewed.getNumber();
        for (Video video : database.getVideos()) {
            addToMap(map, video, mostViewed, findTotalHistViewsForVideo(database, video));
        }

        return findVideos(n, map, mostViewed);
    }

  private static boolean checkFilters(final ActionInputData data, final Video video) {
        replaceLowerCase(video.getGenre());
      if (!data.getFilters().get(1).contains(null)) {
          replaceLowerCase((ArrayList<String>) data.getFilters().get(1));
      }
    String genre = data.getFilters().get(1).get(0);
    if (data.getFilters().get(0).get(0) != null && genre != null) {
        int year = Integer.parseInt(data.getFilters().get(0).get(0));
        return video.getYear() == year && video.getGenre().contains(genre);
    } else if (data.getFilters().get(0).get(0) == null && genre != null) {
        return video.getGenre().contains(genre);
    } else if (data.getFilters().get(0).get(0) != null && genre == null) {
        int year = Integer.parseInt(data.getFilters().get(0).get(0));
        return video.getYear() == year;
    } else {
        return data.getFilters().get(0).get(0) == null && genre == null;
    }
  }

  private static int findTotalFavViewsForVideo(final Database database, final Video v) {
        int numberOfTimes = 0;
        for (User user : database.getUsers()) {
            for (String video : user.getFavoriteVideos()) {
                if (video.equals(v.getTitle())) {
                   numberOfTimes++;
                }
            }
        }
        return numberOfTimes;
  }

  private static int findTotalHistViewsForVideo(final Database database, final Video v) {
      int numberOfTimes = 0;
      for (User user : database.getUsers()) {
          for (Map.Entry video : user.getHistory().entrySet()) {
              if (video.getKey().equals(v.getTitle())) {
                  int times = (int) video.getValue();
                  numberOfTimes += times;
              }
          }
      }
      return numberOfTimes;
  }

  private static int findTotalDurationForVideo(final Database database, final Video v) {
        int serialDuration = 0;
        for (Video video : database.getVideos()) {
          if (v instanceof Movie) {
                return ((Movie) v).getDuration();
          } else {
              if (v instanceof Serial) {
                  for (Season season : ((Serial) v).getSeasons()) {
                      serialDuration += season.getDuration();
                  }
                  return serialDuration;
              }
          }
        }
        return 0;
  }

  private static void addToMap(final HashMap<String, Integer> map, final Video video,
                               final ActionInputData actionInputData, final int num) {
      if (actionInputData.getObjectType().equals(Constants.MOVIES)) {
          if (video instanceof Movie) {
              if (checkFilters(actionInputData, video)
                      && num != 0) {
                  map.put(video.getTitle(), num);
              }
          }
      } else {
          if (video instanceof Serial) {
              if (checkFilters(actionInputData, video)
                      && num != 0) {
                  map.put(video.getTitle(), num);
              }

          }
      }
  }

  private  static ArrayList<String> findVideos(final int n, final HashMap<String, Integer> map,
                                               final ActionInputData actionInputData) {
        ArrayList<String> foundVideos = new ArrayList<>();
      LinkedHashMap<String, Integer> sortedByValue
              = new LinkedHashMap<>(sortHashMapByIntValue(map, actionInputData.getSortType()));
      sortedByValue.forEach((key, value) -> foundVideos.add((String) key));
      if (foundVideos.size() < n) {
          return foundVideos;
      }
      return new ArrayList<>(foundVideos.subList(0, n));
  }


}
