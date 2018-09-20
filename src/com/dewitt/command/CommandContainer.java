package com.dewitt.command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("updateloc", new UpdateLocationCmd()); // update location
        commands.put("error", new ErrorCmd());
    }

    public static Command get(String commandName){
        if (commandName == null || !commands.containsKey(commandName))
            commandName = "error";

        return commands.get(commandName);
    }
}
