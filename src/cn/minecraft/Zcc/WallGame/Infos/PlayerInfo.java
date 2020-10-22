package cn.minecraft.Zcc.WallGame.Infos;

import cn.minecraft.Zcc.WallGame.Skill.SkillBase;
import org.bukkit.entity.Player;

public class PlayerInfo {
    Player player;
    GameInfo gameInfo = null;
    SkillBase skillBase = null;
    public PlayerInfo(Player player,GameInfo info){
        this.player = player;
        this.gameInfo = getGameInfo();
        skillBase = null;
    }

    public SkillBase getSkillBase() {
        return skillBase;
    }

    public PlayerInfo(Player player){
        this.player = player;
    }


    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public Player getPlayer() {
        return player;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
