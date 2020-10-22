package cn.minecraft.Zcc.WallGame.Skill;

import java.util.HashMap;

public class SkillManager {
    public static HashMap<String,SkillBase> skills = new HashMap<>();
    public static void addSkill(SkillBase skillBase){
        skills.put(skillBase.getName(),skillBase);
    }
}
