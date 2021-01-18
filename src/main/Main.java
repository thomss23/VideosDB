package main;

import actions.QActor;
import actions.QCommands;
import actions.QUser;
import actions.QVideo;
import actions.RPremium;
import actions.RStandard;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import entertainment.Database;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        Database database = new Database();
        QCommands qCommands = new QCommands(database);
        QActor queryActor = new QActor(database);
        QVideo queryVideo = new QVideo(database);
        QUser queryUser = new QUser(database);
        RStandard recStandard = new RStandard(database);
        RPremium recPremium = new RPremium(database);

        database.setDatabase(input);

       for (ActionInputData action : input.getCommands()) {

           if (action.getActionType().equals(Constants.COMMAND)) {
               executeCommand(action, arrayResult, qCommands, fileWriter);
           }

           if (action.getActionType().equals(Constants.QUERY)
                   && action.getObjectType().equals(Constants.ACTORS)) {
               executeQueryActors(action, arrayResult, queryActor, fileWriter);
           }

           if (action.getActionType().equals(Constants.QUERY)
                   && (action.getObjectType().equals(Constants.MOVIES)
                   || action.getObjectType().equals(Constants.SHOWS))) {

               executeQueryVideos(action, arrayResult, queryVideo, fileWriter);
           }

           if (action.getActionType().equals(Constants.QUERY)
                   && action.getObjectType().equals(Constants.USERS)
                   && action.getCriteria().equals("num_ratings")) {

               executeQueryUser(action, arrayResult, queryUser, fileWriter);

           }

           if (action.getActionType().equals(Constants.RECOMMENDATION)) {
               if (action.getType().equals("standard") || action.getType().equals("best_unseen")) {
                   executeRecommendationStandard(action, arrayResult, recStandard, fileWriter);
               } else {
                   executeRecommendationPremium(action, arrayResult, recPremium, fileWriter);
               }
           }

       }
        fileWriter.closeJSON(arrayResult);
    }

    private static void executeCommand(final ActionInputData action, final JSONArray arrayResult,
                                       final QCommands qCommands,
                                       final Writer fileWriter) throws IOException {

        if (action.getType().equals("favorite")) {
            String message = qCommands.setFavorite(action.getUsername(), action.getTitle());
            arrayResult.add(fileWriter.writeFile(action.getActionId(), null, message));
        }

        if (action.getType().equals("view")) {
            String message = qCommands.setView(action.getUsername(), action.getTitle());
            arrayResult.add(fileWriter.writeFile(action.getActionId(), null, message));
        }

        if (action.getType().equals("rating")
                && action.getSeasonNumber() == 0) {
            String message = qCommands.setMovieRating(action.getUsername(),
                    action.getTitle(), action.getGrade());
            arrayResult.add(fileWriter.writeFile(action.getActionId(), null, message));
        }

        if (action.getType().equals("rating")
                && action.getSeasonNumber() != 0) {
            String message = qCommands.setSerialRating(action.getUsername(),
                    action.getTitle(),
                    action.getSeasonNumber() - 1, action.getGrade());
            arrayResult.add(fileWriter.writeFile(action.getActionId(), null, message));
        }
    }

    private static void executeQueryActors(final ActionInputData action,
                                           final JSONArray arrayResult,
                                           final QActor queryActor,
                                           final Writer fileWriter) throws IOException {

        if (action.getCriteria().equals("average")) {
            String message = queryActor.average(action.getNumber(),
                    action.getSortType()).toString();
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, "Query result: " + message));
        }

        if (action.getCriteria().equals("awards")) {
            String message = queryActor.awards(action).toString();
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, "Query result: " + message));
        }

        if (action.getCriteria().equals("filter_description")) {
            String message = queryActor.filterDescription(action).toString();
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, "Query result: " + message));
        }
    }

    private static void executeQueryVideos(final ActionInputData action,
                                           final JSONArray arrayResult,
                                           final QVideo queryVideo,
                                           final Writer fileWriter) throws IOException {

        if (action.getCriteria().equals("ratings")) {
            String message = queryVideo.sortRating(action).toString();
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, "Query result: " + message));
        }

        if (action.getCriteria().equals("favorite")) {
            String message = queryVideo.sortFavorite(action).toString();
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, "Query result: " + message));
        }

        if (action.getCriteria().equals("longest")) {
            String message = queryVideo.sortLongest(action).toString();
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, "Query result: " + message));
        }

        if (action.getCriteria().equals("most_viewed")) {
            String message = queryVideo.sortMostViewed(action).toString();
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, "Query result: " + message));
        }

    }

    private static void executeQueryUser(final ActionInputData action,
                                         final JSONArray arrayResult,
                                         final QUser queryUser,
                                         final Writer fileWriter) throws IOException {

            String message = queryUser.numberOfRatings(action).toString();
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, "Query result: " + message));

    }

    private static void executeRecommendationStandard(final ActionInputData action,
                                                      final JSONArray arrayResult,
                                                      final RStandard recStandard,
                                                      final Writer fileWriter) throws IOException {
        if (action.getType().equals("standard")) {
            String message = recStandard.standard(action.getUsername());
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, message));
        }

        if (action.getType().equals("best_unseen")) {
            String message = recStandard.bestUnseen(action.getUsername());
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, message));
        }
    }

    private static void executeRecommendationPremium(final ActionInputData action,
                                                     final JSONArray arrayResult,
                                                     final RPremium recPremium,
                                                     final Writer fileWriter) throws IOException {

        if (action.getType().equals("popular")) {
            String message = recPremium.popularRec(action.getUsername());
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, message));
        }

        if (action.getType().equals("favorite")) {
            String message = recPremium.favoriteRec(action.getUsername());
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, message));
        }

        if (action.getType().equals("search")) {
            String message = recPremium.search(action.getUsername(), action.getGenre());
            arrayResult.add(fileWriter.writeFile(action.getActionId(),
                    null, message));
        }
    }
}
