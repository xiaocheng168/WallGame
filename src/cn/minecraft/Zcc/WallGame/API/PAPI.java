package cn.minecraft.Zcc.WallGame.API;

import cn.minecraft.Zcc.WallGame.Infos.PlayerInfo;
import cn.minecraft.Zcc.WallGame.Threads.Game.GameThread;
import cn.minecraft.Zcc.WallGame.Threads.GameThreadManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PAPI extends PlaceholderExpansion {
    @Override

    //PAPI 2020年10月18日00:15:05


    public String onPlaceholderRequest(Player p, String params) {
        if (GameThreadManager.serverPlayers.get(p) == null){
            GameThreadManager.serverPlayers.putIfAbsent(p,new PlayerInfo(p));
        }
        if (GameThreadManager.serverPlayers.get(p).getGameInfo() == null){
            return "No";
        }
        PlayerInfo playerInfo = GameThreadManager.serverPlayers.get(p);
        if (params.equals("max")){//房间最大人数
            return String.valueOf(GameThreadManager.serverPlayers.get(p).getGameInfo().getMax());
        }
        if (params.equals("game")){//房间名
            return String.valueOf(GameThreadManager.serverPlayers.get(p).getGameInfo().getGame());
        }
        if (params.equals("min")){//房间最小人数
            return String.valueOf(GameThreadManager.serverPlayers.get(p).getGameInfo().getMin());
        }
        if (params.equals("game_player")){//房间玩家在线玩家
            return String.valueOf(GameThreadManager.serverPlayers.get(p).getGameInfo().getPlayers().size());
        }
        if (params.equals("state")){
            if (playerInfo.getGameInfo().isStart()) {
                return "游戏中";
            }else{
                return "等待中";
            }
        }
        return "未知变量";
    }

    @Override
    public String getIdentifier() {
        return "wg";
    }

    @Override
    public String getAuthor() {
        return "Zcc";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
