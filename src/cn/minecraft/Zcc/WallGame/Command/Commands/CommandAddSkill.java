package cn.minecraft.Zcc.WallGame.Command.Commands;

import cn.minecraft.Zcc.WallGame.Command.CommandBase;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import cn.minecraft.Zcc.WallGame.Managers.PlayerSend;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CommandAddSkill extends CommandBase {
    public CommandAddSkill(String cmd, String per, String help) {
        super(cmd, per, help);
    }

    @Override
    public void CommandEvent(Player player, String[] commands) {

        Game.addSkill(commands[1],player);
    }
}
