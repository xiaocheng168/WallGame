package cn.minecraft.Zcc.WallGame.Command.Commands;

import cn.minecraft.Zcc.WallGame.Command.CommandBase;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import org.bukkit.entity.Player;

public class CommandEditSkill extends CommandBase {
    public CommandEditSkill(String cmd, String per, String help) {
        super(cmd, per, help);
    }

    @Override
    public void CommandEvent(Player player, String[] commands) {
        Game.editSkill(commands[1],player);
    }
}
