package process;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * process all the commands, queries and recommendations
 */
public final class Process {

    private final Database database;

    public Process(final Input input) {
        this.database = new Database(input);
    }

    /**
     * process information based on actionType
     * @param fileWriter writer
     * @param jsonArray json
     */
    public void processTests(final Writer fileWriter, final JSONArray jsonArray)
            throws IOException {
        JSONObject jsonObject;
        for (ActionInputData actionInputData : database.getActionsDB()) {
            switch (actionInputData.getActionType()) {
                case Constants.COMMAND -> {
                    Commands commands = new Commands(database);
                    jsonObject = fileWriter.writeFile(actionInputData.getActionId(), "",
                            commands.processCommand(actionInputData));
                    jsonArray.add(jsonObject);
                }
                case Constants.QUERY -> {
                    Query query = new Query(database);
                    jsonObject = fileWriter.writeFile(actionInputData.getActionId(), "",
                            query.processQuery(actionInputData));
                    jsonArray.add(jsonObject);
                }
                case Constants.RECOMMENDATION -> {
                    Recommendation recommendation = new Recommendation(database);
                    jsonObject = fileWriter.writeFile(actionInputData.getActionId(), "",
                            recommendation.processRecommendation(actionInputData));
                    jsonArray.add(jsonObject);
                }
                default -> { }
            }
        }
    }
}
