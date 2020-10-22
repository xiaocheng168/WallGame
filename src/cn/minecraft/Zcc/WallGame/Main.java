package cn.minecraft.Zcc.WallGame;

import cn.minecraft.Zcc.WallGame.API.PAPI;
import cn.minecraft.Zcc.WallGame.Command.CommandBase;
import cn.minecraft.Zcc.WallGame.Command.CommandManager;
import cn.minecraft.Zcc.WallGame.Command.Commands.*;
import cn.minecraft.Zcc.WallGame.Listener.Play;
import cn.minecraft.Zcc.WallGame.Listener.SkillGui;
import cn.minecraft.Zcc.WallGame.Managers.FileManager;
import cn.minecraft.Zcc.WallGame.Managers.PlayerSend;
import cn.minecraft.Zcc.WallGame.Threads.GameThreadManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {
    public static String Prefix = "§a§k|§e§lWallGame§a§k|§3";
    public static boolean isStop;
    public static Plugin plugin;
    @Override
    public void onEnable() {
        saveDefaultConfig();//写出默认文件
        plugin = this;
        isStop = false;
        Bukkit.getPluginManager().registerEvents(new Play(),this);
        Bukkit.getPluginManager().registerEvents(new SkillGui(),this);
        getLogger().info("WallGame 已载入");
        getLogger().info("注册监听器成功！");
        StarThread();//启动相关线程
        addCommand();//注册命令
        setDirs();//设置目录
        GameThreadManager.StartGames();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            PlaceholderAPI.unregisterAllProvidedExpansions();
            PlaceholderAPI.registerExpansion(new PAPI());
            getLogger().info("PlaceholderAPI 变量已注入 √");

        }
    }

    public void setDirs() {
        FileManager.createDir(getDataFolder(),"Game");
        FileManager.createDir(getDataFolder(),"Skill");
        FileManager.createDir(getDataFolder(),"Player");
        File gameFile = new File(getDataFolder(),"games.yml");
        if (!gameFile.isFile()){
            YamlConfiguration game = YamlConfiguration.loadConfiguration(gameFile);
            game.set("games",new ArrayList<>());
            try { game.save(gameFile); } catch (IOException e) { e.printStackTrace(); }
            getLogger().info("创建 games.yml 文件成功 √");
        }

    }

    public static void addCommand(){
        CommandManager.addCommand(new CommandHelp("help","","/wallgame help 帮助指令"));
        CommandManager.addCommand(new CommandCreate("create","wallgame.admin","/wallgame create 房间名 最小人数 最大人数 帮助指令"));
        CommandManager.addCommand(new CommandRemove("delete","wallgame.admin","/wallgame delete 房间名 删除游戏房间"));
        CommandManager.addCommand(new CommandSetSpawn("setspawn","wallgame.admin","/wallgame setspawn 房间名 站在你所在的地方设置玩家进入的出生点"));
        CommandManager.addCommand(new CommandJoin("join","","/wallgame join 房间名 加入你输入的房间"));
        CommandManager.addCommand(new CommandSetLobby("setlobby","wallgame.admin","/wallgame setlobby 设置主大厅"));
        CommandManager.addCommand(new CommandLeave("leave","","/wallgame leave 离开房间"));
        CommandManager.addCommand(new CommandOpenSkill("openskill","wallgame.openskill","/wallgame openskill 打开我的职业仓库"));
        CommandManager.addCommand(new CommandOpenAdminSkill("openadminskill","wallgame.admin","/wallgame openadminskill GUI打开职业列表"));
        CommandManager.addCommand(new CommandEditSkill("editskill","wallgame.admin","/wallgame editskill 职业名称 编辑一个职业内的物品"));
        CommandManager.addCommand(new CommandAddSkill("addskill","wallgame.admin","/wallgame addskill 职业名称 创建一个职业,打开GUI编辑物品"));
        CommandManager.addCommand(new CommandRemoveSkill("delskill","wallgame.admin","/wallgame delskill 职业名称 删除一个职业"));


        CommandManager.addCommand(new CommandSetZone1("setzone1","wallgame.admin","/wallgame setzone1 房间名 设置安全区第一个点"));
        CommandManager.addCommand(new CommandSetZone2("setzone2","wallgame.admin","/wallgame setzone2 房间名 设置安全区第二个点"));
    }

    public static void StarThread(){
        //new ScorecardRun("计分板",100).start();
    }

    //指令触发器
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length > 0){
                String cmdHand = args[0];
                for (Map.Entry<String, CommandBase> cmd : CommandManager.getCommands().entrySet()) {
                    CommandBase c = cmd.getValue();
                    if (cmdHand.equals(c.getHandCmd())) {//判断指令参数 1 传递指令
                        if (args.length < c.cmdSize()){
                            PlayerSend.sendMessage(player,c.getHelp());
                        }else{
                            if (player.hasPermission(c.getP())){
                                c.CommandEvent(player,args);
                            }else{
                                PlayerSend.sendMessage(player,"你没有权限使用这个命令");
                            }
                        }
                        return true;
                    }
                }
            }
            //默认提示
            sendHelp(player);


        }
        return false;
    }

    public static void sendHelp(Player player){
        PlayerSend.sendNoPerFixMessage(player,"");
        PlayerSend.sendNoPerFixMessage(player,"§e战墙 §eWall§bGaem §dVersion: 1.0.0");
        PlayerSend.sendNoPerFixMessage(player,"§a运行在: §f"+Bukkit.getBukkitVersion());
        for (Map.Entry<String, CommandBase> cmd : CommandManager.getCommands().entrySet()) {
            PlayerSend.sendMessage(player,cmd.getValue().getHelp());
        }
        PlayerSend.sendNoPerFixMessage(player,"§a=================================");
    }

    //Tab补全
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length > 0){
            String z_cmd = args[0].toLowerCase();
            if (!z_cmd.equals("")){
                List<String> tab = new ArrayList<>();
                for (Map.Entry<String, CommandBase> cmd : CommandManager.getCommands().entrySet()) {
                    if (z_cmd.toLowerCase().getBytes().length > cmd.getKey().toLowerCase().length())break;
                    if (cmd.getKey().toLowerCase().substring(0,z_cmd.toLowerCase().getBytes().length).equals(z_cmd)) {
                        tab.add(cmd.getKey().toLowerCase());
                    }
                }
                return tab;
            }
        }
        return super.onTabComplete(sender, command, alias, args);
    }

    public static void main(String[] args) {
        String a ="abc";
        String b = "ab";
        System.out.println(a.substring(0,b.getBytes().length).equals(b));
    }

    @Override
    public void onDisable() {
        isStop=true;
    }
}
