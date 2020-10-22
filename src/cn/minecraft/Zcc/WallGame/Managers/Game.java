package cn.minecraft.Zcc.WallGame.Managers;

import cn.minecraft.Zcc.WallGame.Infos.PlayerInfo;
import cn.minecraft.Zcc.WallGame.Listener.SkillGui;
import cn.minecraft.Zcc.WallGame.Main;
import cn.minecraft.Zcc.WallGame.Threads.Game.GameThread;
import cn.minecraft.Zcc.WallGame.Threads.GameThreadManager;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Game {
    public static void addGame(Player player,String game,int min,int max) {
        if (isGame(player,game)){
            PlayerSend.sendMessage(player,"该游戏房间已存在,请重新选择一个吧！(#^.^#)");
            return;
        }
        File file = new File(FileManager.getGameDir(), game + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(game + ".name", game);//固定房间名
        config.set(game + ".max", max);//最大人数
        config.set(game + ".min", min);//最小人数
        config.set(game+".Zone1","");//安全区第一个点
        config.set(game+".Zone1","");//安全区第二个点
        config.set(game + ".SpawnLocation", player.getLocation());//初生蛋
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File gamesFile = new File(Main.plugin.getDataFolder(),"games.yml");
        YamlConfiguration gamesConfig = YamlConfiguration.loadConfiguration(gamesFile);
        List<String> games = gamesConfig.getStringList("games");

        games.add(game);
        gamesConfig.set("games",games);
        try {
            gamesConfig.save(gamesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerSend.sendMessage(player,"§a游戏房间已创建完毕 房间名:"+game);
    }
    public static void delGame(Player player,String game) {
        if (!isGame(player,game)){
            PlayerSend.sendMessage(player,"游戏房间不存在哦！┗( ▔, ▔ )┛");
            return;
        }
        File file = new File(FileManager.getGameDir(), game + ".yml");
        file.delete();

        File gamesFile = new File(Main.plugin.getDataFolder(),"games.yml");
        YamlConfiguration gamesConfig = YamlConfiguration.loadConfiguration(gamesFile);
        List<String> games = gamesConfig.getStringList("games");

        games.remove(game);
        gamesConfig.set("games",games);
        try {
            gamesConfig.save(gamesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerSend.sendMessage(player,"§a游戏房间已删除 房间名:"+game);
    }

    public static YamlConfiguration getGameConfig(String game){
        File file = new File(FileManager.getGameDir(), game + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }
    public static void setGameSpawn(Player player,String game){
        if (!isGame(player,game)){
            PlayerSend.sendMessage(player,"游戏房间不存在哦！┗( ▔, ▔ )┛");
            return;
        }
        File file = new File(FileManager.getGameDir(), game + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(game + ".SpawnLocation", player.getLocation());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerSend.sendMessage(player,"§a游戏房间出生点设置完毕 房间名:"+game);
    }

    public static void setLobby(Player player){
        File file = new File(Main.plugin.getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("LobbyLocation", player.getLocation());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getLobby(){
        File file = new File(Main.plugin.getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return (Location) config.get("LobbyLocation");
    }

    //判断游戏是否存在
    public static boolean isGame(Player player,String game){
        File file = new File(FileManager.getGameDir(),game+".yml");
        if (!file.isFile()){
            List<String> games =getGames();
            for (String s : games) {
                if (s.toLowerCase().equals(game.toLowerCase())) {
                    return true;
                }
            }
        }else{
            return true;
        }
        return false;
    }
    
    public static YamlConfiguration getGamesFile(){
        return YamlConfiguration.loadConfiguration(new File(Main.plugin.getDataFolder(),"games.yml"));
    }
    public static List<String> getGames(){
        return YamlConfiguration.loadConfiguration(new File(Main.plugin.getDataFolder(),"games.yml")).getStringList("games");
    }
    public static void joinGame(Player player,String game){
        GameThread gameThread = GameThreadManager.games.get(game);
        if (gameThread == null) {
            PlayerSend.sendMessage(player,"游戏房间不存在哦！┗( ▔, ▔ )┛");
            return;
        }
        addPlayerToServerPlayers(player);
        PlayerInfo playerInfo = GameThreadManager.serverPlayers.get(player);
        playerInfo.setGameInfo(gameThread.getGameInfo());//设置玩家所在的房间
        gameThread.getGameInfo().getPlayers().put(player,GameThreadManager.serverPlayers.get(player));
        PlayerSend.sendMessage(player,"你已加入游戏房间 房间:"+game);
        player.teleport(gameThread.getGameInfo().getSpawn());
    }
    public static void leaveGame(Player player){
        addPlayerToServerPlayers(player);

        PlayerInfo playerInfo = GameThreadManager.serverPlayers.get(player);
        if (playerInfo.getGameInfo() == null) {
            PlayerSend.sendMessage(player,"游你没有在游戏房间里诶！┗( ▔, ▔ )┛");
        }else{
            playerInfo.getGameInfo().getPlayers().remove(player);
            PlayerSend.sendMessage(player,"你已离开游戏房间！┗( ▔, ▔ )┛");
            player.teleport(getLobby());
            playerInfo.setGameInfo(null);
            sendMessage_2(player,"");
        }
    }
    public static void addPlayerToServerPlayers(Player player){
        GameThreadManager.serverPlayers.putIfAbsent(player,new PlayerInfo(player));
    }
    public static void sendMessage_2(Player player,String msg) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(msg), (byte) 2));
    }
    public static void openMySkillGui(Player player){
        Inventory inventory = Bukkit.createInventory(null,9,Main.Prefix+"职业选择");
        player.openInventory(inventory);
    }
    public static void addSkill(String skillname,Player player){
        if (isSkill(skillname)) {
            PlayerSend.sendMessage(player,"这个职业名称已经存在了");
            return;
        }
        if (player.getItemInHand().getType().equals(Material.AIR)){
            PlayerSend.sendMessage(player,"手上要有东西哦！");
            return;
        }
        File file = new File(FileManager.getSkillDir(),skillname+".yml");
        YamlConfiguration config =YamlConfiguration.loadConfiguration(file);
        config.set(skillname+".name",skillname);
        config.set(skillname+".icon",player.getItemInHand());
        config.set(skillname+".lore",new ArrayList<>());
        config.set(skillname+".items",new ArrayList<>());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        editSkill(skillname,player);

    }
    public static ItemStack getSkillIcon(String skillname){
        File file = new File(FileManager.getSkillDir(),skillname+".yml");
        YamlConfiguration config =YamlConfiguration.loadConfiguration(file);
        return (ItemStack) config.get(skillname+".icon",new ItemStack(Material.BOOK));
    }
    public static boolean isSkill(String skillname){
        File file = new File(FileManager.getSkillDir(),skillname+".yml");
        return file.isFile();
    }
    public static void editSkill(String skillname,Player player){
        if (!isSkill(skillname)){
            PlayerSend.sendMessage(player,"这个职业不存在");
            return;
        }
        SkillGui.editPlayer.put(player,skillname);//存储管理当前编辑的职业
        Inventory inventory = Bukkit.createInventory(null,27,Main.Prefix+"编辑职业"+skillname);
        File file = new File(FileManager.getSkillDir(),skillname+".yml");
        YamlConfiguration config =YamlConfiguration.loadConfiguration(file);

        List<ItemStack> list = (List<ItemStack>) config.getList(skillname+".items");
        if (list != null && list.size() > 0){
            for (int i = 0; i < 27; i++) {
                inventory.setItem(i,list.get(i));
            }
        }
        player.openInventory(inventory);
    }
    public static void addSkillItem(String skillname, ItemStack itemStack){
        File file = new File(FileManager.getSkillDir(),skillname+".yml");
        YamlConfiguration config =YamlConfiguration.loadConfiguration(file);

        List<ItemStack> list = (List<ItemStack>) config.getList(skillname+".items");

        list.add(itemStack);

        config.set(skillname+".items",list);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addSkillItem(String skillname, List<ItemStack> itemStack){
        File file = new File(FileManager.getSkillDir(),skillname+".yml");
        YamlConfiguration config =YamlConfiguration.loadConfiguration(file);

        config.set(skillname+".items",itemStack);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void openAdminSkill(Player player){
        Inventory inventory = Bukkit.createInventory(null,54,Main.Prefix+"管理职业");
        for (String s : FileManager.getSkillDir().list()) {
            String name = s.replace(".yml","");
            File file = new File(FileManager.getSkillDir(),name+".yml");
            YamlConfiguration config =YamlConfiguration.loadConfiguration(file);
            List<String> l = new ArrayList<>();
            for (String lore : config.getStringList(name+".lore")) {
                l.add(lore.replace("&","§"));
            }
            inventory.addItem(getitem(getSkillIcon(name),config.getString(name+".name")+" - "+name,l));
        }
        player.openInventory(inventory);
    }
    public static ItemStack getitem(Material material, String ItemName, String[] lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ItemName);
        ArrayList arrayList = new ArrayList();

        for (int i = 0; i < lore.length; ++i) {
            arrayList.add(lore[i]);
        }

        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static ItemStack getitem(ItemStack itemStack, String ItemName, String[] lore) {
        ItemMeta itemMeta = itemStack.getItemMeta().clone();
        itemMeta.setDisplayName(ItemName);
        ArrayList arrayList = new ArrayList();

        for (int i = 0; i < lore.length; ++i) {
            arrayList.add(lore[i]);
        }

        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static ItemStack getitem(ItemStack itemStack, String ItemName, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta().clone();
        itemMeta.setDisplayName(ItemName);
        lore.add("");
        lore.add("§a左键编辑");
        lore.add("§c右键删除");
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static void delSkill(String skillname,Player player){
        File file = new File(FileManager.getSkillDir(),skillname+".yml");
        if (file.delete()) {
            PlayerSend.sendMessage(player,"职业已删除");
        }
    }
    public static void newPlayer(Player player){
        File file = new File(FileManager.getPlayerDir(),player.getName()+".yml");
        if (!file.isFile()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set(player.getName()+".name",player.getName());//击杀
            config.set(player.getName()+".kill",0);//击杀
            config.set(player.getName()+".money",0);//金币
            config.set(player.getName()+".death",0);//死亡次数
            config.set(player.getName()+".Skills",new ArrayList<>());//职业列表
            config.set(player.getName()+".Use","none");//当前使用
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setGameZone1(String name,Player player,Location location){
        File file = new File(FileManager.getGameDir(),name+".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(name+".Zone1",location);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setGameZone2(String name,Player player,Location location){
        File file = new File(FileManager.getGameDir(),name+".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(name+".Zone2",location);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getGameZone1(String name){
        File file = new File(FileManager.getGameDir(),name+".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return (Location) config.get(name+".Zone1");
    }
    public static Location getGameZone2(String name){
        File file = new File(FileManager.getGameDir(),name+".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return (Location) config.get(name+".Zone2");
    }
}

