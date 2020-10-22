package cn.minecraft.Zcc.WallGame.Command.Commands;

import cn.minecraft.Zcc.WallGame.Command.CommandBase;
import cn.minecraft.Zcc.WallGame.Main;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import cn.minecraft.Zcc.WallGame.Managers.PlayerSend;
import org.bukkit.entity.Player;

public class CommandSetLobby extends CommandBase {
    public CommandSetLobby(String cmd, String per, String help) {
        super(cmd, per, help);
    }

    @Override
    public void CommandEvent(Player player, String[] commands) {
        Game.setLobby(player);
        PlayerSend.sendMessage(player,"游戏主大厅已设置");
    }
}
