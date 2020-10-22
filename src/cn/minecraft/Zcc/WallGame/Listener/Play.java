package cn.minecraft.Zcc.WallGame.Listener;

import cn.minecraft.Zcc.WallGame.Infos.PlayerInfo;
import cn.minecraft.Zcc.WallGame.Main;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import cn.minecraft.Zcc.WallGame.Managers.PlayerSend;
import cn.minecraft.Zcc.WallGame.Threads.Game.GameThread;
import cn.minecraft.Zcc.WallGame.Threads.GameThreadManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Play implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent A){
        if (A.getView().getTitle().equals(Main.Prefix)){
            A.setCancelled(true);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent A){
        Game.newPlayer(A.getPlayer());
    }
    @EventHandler
    public void onMove(PlayerMoveEvent A){

        float x1 = 1755;float y1 = 7;float z1 = -188;
        float x2 = 1761;float y2 = 4;float z2 = -194;
        Location location = A.getTo();
        if (Math.abs(location.getX()) >= x1 && Math.abs(location.getX()) <= x2 || Math.abs(location.getX()) >= x2 && Math.abs(location.getX()) <= x1){
            if (Math.abs(location.getZ()) >= Math.abs(z1) && Math.abs(location.getZ()) <= Math.abs(z2) || Math.abs(location.getZ()) >= Math.abs(z2) && Math.abs(location.getZ()) <= Math.abs(z1)){
                if (location.getY() >= y1 && location.getY() <= y2 || location.getY() >= y2 && location.getY() <= y1){
                    Bukkit.broadcastMessage("1");
                }
            }
        }
    }
    @EventHandler
    public void onInt(PlayerInteractEvent A){
        Player player = A.getPlayer();
        Game.addPlayerToServerPlayers(player);
        PlayerInfo playerInfo = GameThreadManager.serverPlayers.get(player);
        if (playerInfo.getGameInfo() == null) { return; }
        if (!playerInfo.getGameInfo().isStart()) {
            A.setCancelled(true);
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent A){
        Player player = A.getPlayer();
        Game.addPlayerToServerPlayers(player);
        PlayerInfo playerInfo = GameThreadManager.serverPlayers.get(player);
        if (playerInfo.getGameInfo() == null) { return; }
        if (!playerInfo.getGameInfo().isStart()) {
            A.setCancelled(true);
        }
    }
}
