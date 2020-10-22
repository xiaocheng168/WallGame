package cn.minecraft.Zcc.WallGame.Command.Commands;

import cn.minecraft.Zcc.WallGame.Command.CommandBase;
import cn.minecraft.Zcc.WallGame.Command.CommandManager;
import cn.minecraft.Zcc.WallGame.Main;
import cn.minecraft.Zcc.WallGame.Managers.PlayerSend;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandHelp extends CommandBase {
    public CommandHelp(String cmd, String per, String help) {
        super(cmd, per, help);
    }

    @Override
    public void CommandEvent(Player player, String[] commands) {
        Main.sendHelp(player);
    }
}
