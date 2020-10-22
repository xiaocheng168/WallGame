package cn.minecraft.Zcc.WallGame.Skill;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillBase {
    List<String> lore;
    List<ItemStack> itemStacks;
    String name;
    public SkillBase(String name, List<String> lore, List<ItemStack> itemStack){
        this.lore = lore;
        this.itemStacks = itemStack;
    }
    public void usePlayer(Player player){ }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }
}
