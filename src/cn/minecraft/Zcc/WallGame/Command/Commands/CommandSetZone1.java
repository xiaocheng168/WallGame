package cn.minecraft.Zcc.WallGame.Command.Commands;

import cn.minecraft.Zcc.WallGame.Command.CommandBase;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import org.bukkit.entity.Player;

public class CommandSetZone1 extends CommandBase {
    public CommandSetZone1(String cmd, String per, String help) {
        super(cmd, per, help);
    }

    @Override
    public void CommandEvent(Player player, String[] commands) {
        String name = commands[1];
        Game.setGameZone1(name,player,player.getLocation());
    }
}
