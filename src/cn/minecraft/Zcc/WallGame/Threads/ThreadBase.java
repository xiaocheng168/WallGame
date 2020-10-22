package cn.minecraft.Zcc.WallGame.Threads;


import cn.minecraft.Zcc.WallGame.Infos.GameInfo;

import java.util.Date;

public class ThreadBase extends Thread{
    Date date;
    int sleep;
    String game = "";
    GameInfo gameInfo = null;
    public ThreadBase(String name, int sleep, String game, GameInfo gameInfo){
        this.game = game;
        this.date = new Date();
        this.sleep = sleep;
        this.gameInfo = gameInfo;
        setName(name);
        ThreadManager.threadBases.put(this,this);
        System.out.print("[WallGame-GameThread-"+game+"-New] "+getId());
        start();
    }
    public ThreadBase(String name,int sleep){
        this.date = new Date();
        this.sleep = sleep;
        setName(name);
        ThreadManager.threadBases.put(this,this);
        System.out.print("[WallGame-Thread-"+getName()+"-New] "+getId());
        start();
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public int getSleep() {
        return sleep;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void End(){
        System.out.print("[WallGame-Thread-"+getName()+"-Stop] "+getId());
    }
}
