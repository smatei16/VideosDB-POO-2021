package process;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public final class Process {
    private Database database;

    public Process(final Input input) {
        this.database = new Database(input);
    }

    /**
     *
     */
    public void processTests(final Writer fileWriter, final JSONArray jsonArray)
            throws IOException {
        JSONObject jsonObject;
        for (ActionInputData actionInputData : database.getActionsDB()) {
            if (actionInputData.getActionType().equals(Constants.COMMAND)) {
                Commands commands = new Commands(database);
                jsonObject = fileWriter.writeFile(actionInputData.getActionId(), "",
                        commands.processCommand(actionInputData));
                jsonArray.add(jsonObject);
            } else if (actionInputData.getActionType().equals(Constants.QUERY)) {
                Query query = new Query(database);
                jsonObject = fileWriter.writeFile(actionInputData.getActionId(), "",
                        query.processQuery(actionInputData));
                jsonArray.add(jsonObject);
            }
        }
    }
}
