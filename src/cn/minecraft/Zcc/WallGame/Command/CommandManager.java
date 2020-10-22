package cn.minecraft.Zcc.WallGame.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CommandManager {
    public static HashMap<String, CommandBase> commands = new LinkedHashMap<>();

    public static List<CommandManager> cmds = new ArrayList<>();

    public static boolean addCommand(CommandBase commandGameBase){
        return commands.put(commandGameBase.getHandCmd(),commandGameBase) != null;
    }

    public static HashMap<String, CommandBase> getCommands() {
        return commands;
    }
    public static int getCommandSize(){
        return commands.size();
    }
}
