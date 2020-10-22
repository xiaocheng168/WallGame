package cn.minecraft.Zcc.WallGame.Command.Commands;

import cn.minecraft.Zcc.WallGame.Command.CommandBase;
import cn.minecraft.Zcc.WallGame.Command.CommandManager;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import cn.minecraft.Zcc.WallGame.Managers.PlayerSend;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandCreate extends CommandBase {

    public CommandCreate(String cmd, String per, String help) {
        super(cmd, per, help);
    }

    @Override
    public void CommandEvent(Player player, String[] commands) {
        Game.addGame(player, commands[1],Integer.parseInt(commands[2]),Integer.parseInt(commands[3]));
    }
}
