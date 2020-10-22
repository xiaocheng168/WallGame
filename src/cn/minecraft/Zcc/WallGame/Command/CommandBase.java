package cn.minecraft.Zcc.WallGame.Command;

import org.bukkit.entity.Player;

public class CommandBase {
    String HandCmd;
    String p;
    String help;
    public CommandBase(String cmd, String per,String help){
        this.HandCmd = cmd.toLowerCase();
        this.p = per;
        this.help = help;
    }
    public void CommandEvent(Player player,String[] commands){}


    public String getP() {
        return p;
    }

    public String getHandCmd() {
        return HandCmd;
    }

    public String getHelp() {
        return help;
    }

    public int cmdSize(){
        String cmd = getHelp().replace("/wallgame ","");
        String[] z_cmd = cmd.split(" ");
        return z_cmd.length-1;
    }

}
