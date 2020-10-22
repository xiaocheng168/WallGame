package cn.minecraft.Zcc.WallGame.Managers;

import cn.minecraft.Zcc.WallGame.Threads.EntityLocation;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;

public class EntitySet {
    public static void addEntity(Location location,double Hp){
        Pig entity = (Pig) location.getWorld().spawnEntity(location, EntityType.PIG);
        EntityPig entityPig = ((CraftPig)entity).getHandle();
        entityPig.goalSelector = new PathfinderGoalSelector(entityPig.getWorld().methodProfiler);
        entity.setMaxHealth(Hp);
        entity.setHealth(Hp);
        new EntityLocation("怪物线程",100,location,entity);
    }
}
