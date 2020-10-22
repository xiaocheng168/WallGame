package cn.minecraft.Zcc.WallGame.Threads.Game;

import cn.minecraft.Zcc.WallGame.Infos.GameInfo;
import cn.minecraft.Zcc.WallGame.Infos.PlayerInfo;
import cn.minecraft.Zcc.WallGame.Main;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import cn.minecraft.Zcc.WallGame.Threads.ThreadBase;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Map;

public class GameThread extends ThreadBase {

    public static boolean GameEnd = false;

    public GameThread(String name, int sleep, String game, GameInfo gameInfo) {
        super(name, sleep, game, gameInfo);
    }


    //Run 1s Sleep
    @Override
    public void run(){
        while (!Main.isStop){
            Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    if (!getGameInfo().isStart()){
                        GameEnd = false;
                        sendMessage_2("等待中....");
                        if (getGameInfo().getPlayers().size() >= getGameInfo().getMin()){
                            getGameInfo().setDjs(getGameInfo().getDjs()-1);
                            sendMessage_2("倒计时 : "+getGameInfo().getDjs());
                            sendPlayersSound(Sound.LEVEL_UP);
                            if (getGameInfo().getDjs() <= 0) {//游戏开始
                                getGameInfo().setStart(true);
                                sendPlayersSound(Sound.ENDERDRAGON_GROWL);
                                getGameInfo().setStart(true);//设置游戏开始
                            }
                        }else{
                            if (getGameInfo().getPlayers().size() == 0){//玩家不够 重置
                                GameEnd = true;
                            }
                        }
                    }else{
                        //游戏中
                        if (getGameInfo().getPlayers().size() <= 1){//人数 < 2 结束游戏
                            GameEnd = true;
                        }

                    }

                    //全局触发方法

                    //游戏结束
                    if (GameEnd){
                        leaves();
                        getGameInfo().setDjs(15);
                        getGameInfo().setStart(false);
                        GameEnd = false;
                    }
                }
            });

            try {
                ThreadBase.sleep(getSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setItem(){

    }
    public void sendMessage(String msg){
        for (Map.Entry<Player, PlayerInfo> playerPlayerInfoEntry : getGameInfo().getPlayers().entrySet()) {
            playerPlayerInfoEntry.getKey().sendMessage(msg);
        }
    }
    public void sendMessage_2(String msg){
        for (Map.Entry<Player, PlayerInfo> playerPlayerInfoEntry : getGameInfo().getPlayers().entrySet()) {
            Player player = playerPlayerInfoEntry.getKey();
            EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
            entityPlayer.playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(msg),(byte)2));
        }
    }
    public void sendPlayersSound(Sound sound){
        for (Map.Entry<Player, PlayerInfo> playerPlayerInfoEntry : getGameInfo().getPlayers().entrySet()) {
            Player player = playerPlayerInfoEntry.getKey();
            player.playSound(player.getLocation(), sound,1,1);
        }
    }
    public void sendPlayerSound(Player player,Sound sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }
    public void tpPlayersLocation(Location location){
        for (Map.Entry<Player, PlayerInfo> playerPlayerInfoEntry : getGameInfo().getPlayers().entrySet()) {
            Player player = playerPlayerInfoEntry.getKey();
            player.teleport(location);
        }
    }
    public void leaves(){
        for (Map.Entry<Player, PlayerInfo> playerPlayerInfoEntry : getGameInfo().getPlayers().entrySet()) {
            Player player = playerPlayerInfoEntry.getKey();
            player.teleport(Game.getLobby());
            Game.leaveGame(player);
        }
    }
    public void leave(Player player){
        getGameInfo().getPlayers().remove(player);
    }
}
