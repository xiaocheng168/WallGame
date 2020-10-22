package cn.minecraft.Zcc.WallGame.Listener;

import cn.minecraft.Zcc.WallGame.Main;
import cn.minecraft.Zcc.WallGame.Managers.Game;
import cn.minecraft.Zcc.WallGame.Managers.PlayerSend;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SkillGui implements Listener {
    public static HashMap<Player,String> editPlayer = new LinkedHashMap<>();
    //玩家点击SkillGui事件
    @EventHandler
    public void Click(InventoryClickEvent A){
        if (A.getView().getTitle().contains(Main.Prefix) && !A.getView().getTitle().contains("编辑职业")){//取消所有关于战墙打开的GUI事假
            A.setCancelled(true);
        }
        Player player = (Player) A.getWhoClicked();
        if (A.getView().getTitle().contains("管理职业") && A.getCurrentItem() != null && A.getCurrentItem().hasItemMeta()){
            String[] name_z =A.getCurrentItem().getItemMeta().getDisplayName().split(" - ");
            String name = name_z[name_z.length-1];
            if (A.getClick().equals(ClickType.LEFT)){
                Game.editSkill(name,player);
            }
            if (A.getClick().equals(ClickType.RIGHT)){
                Game.delSkill(name,player);
                Game.openAdminSkill(player);
            }

        }
    }

    @EventHandler
    public void Close(InventoryCloseEvent A){
        Player player = (Player) A.getPlayer();
        if (A.getView().getTitle().contains("编辑职业") && editPlayer.get(player) != null){//取消所有关于战墙打开的GUI事假

            String name = editPlayer.get(player);
            Inventory inventory = A.getInventory();
            List<ItemStack> is = new ArrayList<>();
            for (int i = 0; i < inventory.getSize(); i++) {
                is.add(inventory.getItem(i));
            }
            Game.addSkillItem(name,is);
            PlayerSend.sendMessage(player,"职业物品保存完毕");
        }
    }

}
