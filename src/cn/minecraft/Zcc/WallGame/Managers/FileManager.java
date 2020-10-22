package cn.minecraft.Zcc.WallGame.Managers;

import cn.minecraft.Zcc.WallGame.Main;
import com.sun.org.apache.regexp.internal.RE;

import java.io.File;

public class FileManager {
    public static void createDir(File file,String DirName){
        if (new File(file,DirName).mkdir()) Main.plugin.getLogger().info("创建 "+DirName+" 目录成功 √");
    }
    public static File getGameDir(){
        return new File(Main.plugin.getDataFolder(),"Game");
    }
    public static File getSkillDir(){
        return new File(Main.plugin.getDataFolder(),"Skill");
    }
    public static File getPlayerDir(){
        return new File(Main.plugin.getDataFolder(),"Player");
    }
}
