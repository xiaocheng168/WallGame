package cn.minecraft.Zcc.WallGame.Command.Commands;

import cn.minecraft.Zcc.WallGame.Command.CommandBase;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import org.bukkit.entity.Player;

public class CommandOpenAdminSkill extends CommandBase {
    public CommandOpenAdminSkill(String cmd, String per, String help) {
        super(cmd, per, help);
    }

    @Override
    public void CommandEvent(Player player, String[] commands) {
        Game.openAdminSkill(player);
    }
}
