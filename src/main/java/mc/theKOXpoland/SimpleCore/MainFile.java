package mc.theKOXpoland.SimpleCore;

import dev.jorel.commandapi.CommandAPI;
import mc.theKOXpoland.SimpleCore.Commands.ApiCommands.*;
import mc.theKOXpoland.SimpleCore.Commands.*;
import mc.theKOXpoland.SimpleCore.Events.BlockedCommands;
import mc.theKOXpoland.SimpleCore.Events.ChatDisplay;
import mc.theKOXpoland.SimpleCore.Events.ChatOffListener;
import mc.theKOXpoland.SimpleCore.Events.PlayerListener;
import mc.theKOXpoland.SimpleCore.Managers.ConfigManager;
import mc.theKOXpoland.SimpleCore.Tasks.AutoMessage;
import mc.theKOXpoland.SimpleCore.Tasks.TabListPrefixes;
import mc.theKOXpoland.SimpleCore.Tasks.VanishMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public class MainFile extends JavaPlugin {

    public ConfigManager configManager;


    public Location spawn;
    private boolean chatEnabled;

    public ArrayList<Player> invisible_list = new ArrayList<>();
    public ArrayList<Player> godmode_list = new ArrayList<>();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Override
    public void onLoad() {

        configManager = new ConfigManager(this);

        //CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }

    @Override
    public void onEnable() {

        CommandAPI.onEnable();

        saveDefaultConfig();

        configManager.saveDefaultConfig();

        ConfigCommand configCommand = new ConfigCommand(this);
        configCommand.configReload();

        RulesCommand rulesCommand = new RulesCommand(this);
        rulesCommand.getRules();

        AdminChatCommand adminChatCommand = new AdminChatCommand(this);
        adminChatCommand.adminChat();

        ChatCommand chatCommand = new ChatCommand(this);
        chatCommand.getChat();

        BroadcastCommand broadcastCommand = new BroadcastCommand(this);
        broadcastCommand.broadcast();

        FlyCommand flyCommand = new FlyCommand(this);
        flyCommand.fly();

        Objects.requireNonNull(getCommand("spawn")).setExecutor(new Spawn(this));
        Objects.requireNonNull(getCommand("gamemode")).setExecutor(new GameMode(this));
        Objects.requireNonNull(getCommand("heal")).setExecutor(new Heal(this));
       // Objects.requireNonNull(getCommand("fly")).setExecutor(new Fly(this));
        //Objects.requireNonNull(getCommand("chat")).setExecutor(new Chat(this));
        Objects.requireNonNull(getCommand("teleport")).setExecutor(new Teleport(this));
        Objects.requireNonNull(getCommand("sun")).setExecutor(new Sun(this));
        Objects.requireNonNull(getCommand("day")).setExecutor(new TimeSet(this));
        Objects.requireNonNull(getCommand("night")).setExecutor(new TimeSet(this));
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new Tpa(this));
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new Tpa(this));
        Objects.requireNonNull(getCommand("tpdeny")).setExecutor(new Tpa(this));
        Objects.requireNonNull(getCommand("msg")).setExecutor(new Msg(this));
        Objects.requireNonNull(getCommand("reply")).setExecutor(new Msg(this));
        Objects.requireNonNull(getCommand("help")).setExecutor(new Help(this));
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new Vanish(this));
        Objects.requireNonNull(getCommand("godmode")).setExecutor(new GodMode(this));
        //Objects.requireNonNull(getCommand("broadcast")).setExecutor(new Broadcast(this));
        Objects.requireNonNull(getCommand("kickall")).setExecutor(new Kickall(this));
        Objects.requireNonNull(getCommand("op")).setExecutor(new Op(this));
        Objects.requireNonNull(getCommand("deop")).setExecutor(new Op(this));

        //Objects.requireNonNull(getCommand("chat")).setTabCompleter(new Chat(this));
        //Objects.requireNonNull(getCommand("broadcast")).setTabCompleter(new Broadcast(this));
        //Objects.requireNonNull(getCommand("fly")).setTabCompleter(new Fly(this));
        Objects.requireNonNull(getCommand("gamemode")).setTabCompleter(new GameMode(this));
        Objects.requireNonNull(getCommand("godmode")).setTabCompleter(new GodMode(this));
        Objects.requireNonNull(getCommand("heal")).setTabCompleter(new Heal(this));
        Objects.requireNonNull(getCommand("help")).setTabCompleter(new Help(this));
        Objects.requireNonNull(getCommand("kickall")).setTabCompleter(new Kickall(this));
        Objects.requireNonNull(getCommand("msg")).setTabCompleter(new Msg(this));

        new TabListPrefixes(this).runTaskTimer(this,0L,getConfig().getInt("TabRefresh") * 20L);
        new AutoMessage(this).runTaskTimer(this, 0L, getConfig().getInt("AutoMessageSeconds") * 20L);
        new VanishMessage(this).runTaskTimer(this, 0L, getConfig().getInt("VanishMessageSeconds") * 20L);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatOffListener(this), this);
       // Bukkit.getPluginManager().registerEvents(new ChatDisplay(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockedCommands(this), this);
     //   Bukkit.getPluginManager().registerEvents(new ConsoleListener(this), this);

        spawn = (Location) getConfig().get("spawnLocation");

        chatEnabled = getConfig().getBoolean("chat-enabled", false);

        Bukkit.getLogger().info("[SimpleCore]" + ANSI_GREEN + " Activated!" +  ANSI_RESET);
    }

    public boolean isChatEnabled() {
        return this.chatEnabled;
    }

    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
        getConfig().set("chat-enabled", chatEnabled);
        saveConfig();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        getConfig().set("spawnLocation", spawn);
    }
}