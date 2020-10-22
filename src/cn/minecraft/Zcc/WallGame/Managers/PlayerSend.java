package cn.minecraft.Zcc.WallGame.Managers;

import cn.minecraft.Zcc.WallGame.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerSend {
    public static void sendMessage(Player player, String msg){
        player.sendMessage(Main.Prefix+" §f"+msg);
    }
    public static void sendNoPerFixMessage(Player player, String msg){
        player.sendMessage("§f"+msg);
    }
    public static void sendSound(Player player){
        player.playSound(player.getLocation(), Sound.LEVEL_UP,1,1);
    }
}
