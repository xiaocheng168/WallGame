package cn.minecraft.Zcc.WallGame.Threads;

import cn.minecraft.Zcc.WallGame.Infos.GameInfo;
import cn.minecraft.Zcc.WallGame.Infos.PlayerInfo;
import cn.minecraft.Zcc.WallGame.Managers.FileManager;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import cn.minecraft.Zcc.WallGame.Threads.Game.GameThread;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class GameThreadManager {
    public static HashMap<String, GameThread> games = new HashMap<>();
    public static HashMap<Player, PlayerInfo> serverPlayers = new HashMap<>();

    public static boolean addGame(String game,GameThread gameThread){
        if (games.get(game) != null)return false;
        games.put(game,gameThread);
        return true;
    }

    public static void StartGames(){
        for (String game : Game.getGames()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(FileManager.getGameDir(),game+".yml"));
            addGame(game,new GameThread(game,1000,game,new GameInfo(game, config.getInt(game+".max"),config.getInt(game+".min"))));
        }
    }
}
