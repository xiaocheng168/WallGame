package cn.minecraft.Zcc.WallGame.Threads;

import cn.minecraft.Zcc.WallGame.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Animals;

public class EntityLocation extends ThreadBase{
    Animals entity;
    Location location;
    public EntityLocation(String name, int sleep, Location location, Animals entity) {
        super(name, sleep);
        this.entity = entity;
        this.location = location;
    }

    public Animals getEntity() {
        return entity;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public void run() {
        while (!Main.isStop && !entity.isDead()){
            Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    entity.teleport(location);
                    //Bukkit.broadcastMessage(entity.getHealth()+"/"+entity.getMaxHealth());
                }
            });

            try {
                sleep(getSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        End();
    }
}
