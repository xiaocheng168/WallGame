package cn.minecraft.Zcc.WallGame.Threads;

import cn.minecraft.Zcc.WallGame.Main;
import org.bukkit.Bukkit;

public class ScorecardRun extends ThreadBase {

    public ScorecardRun(String name, int sleep) {
        super(name, sleep);
    }

    @Override
    public void run() {
        while (!Main.isStop) {
            Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(getName());
                }
            });
            try { sleep(getSleep()); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        End();
    }
}
