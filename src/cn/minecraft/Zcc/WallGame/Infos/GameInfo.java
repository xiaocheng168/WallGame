package cn.minecraft.Zcc.WallGame.Infos;

import cn.minecraft.Zcc.WallGame.Listener.Play;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class GameInfo {
    HashMap<Player, PlayerInfo> players = new HashMap<>();
    String game;
    int max;
    int min;
    boolean start = false;
    int Djs = 2;
    Location spawn;
    Location zone1;
    Location zone2;
    public GameInfo(String game,int max,int min){
        this.game = game;
        this.max = max;
        this.min = min;
        this.spawn = (Location) Game.getGameConfig(game).get(game+".SpawnLocation");
        zone1 = Game.getGameZone1(game);
        zone2 = Game.getGameZone2(game);
    }

    public int getDjs() {
        return Djs;
    }

    public void setDjs(int djs) {
        Djs = djs;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public int getMin() {
        return min;
    }

    public String getGame() {
        return game;
    }

    public int getMax() {
        return max;
    }

    public HashMap<Player, PlayerInfo> getPlayers() {
        return players;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getZone1() {
        return zone1;
    }

    public Location getZone2() {
        return zone2;
    }
}
