package mc.theKOXpoland.SimpleCore.Managers;

import mc.theKOXpoland.SimpleCore.MainFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigManager{

    MainFile plugin;
    public ConfigManager(MainFile plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration messageConfig;
    private FileConfiguration translationConfig;
    private File messageConfigFile;
    private File translationConfigFile;

    public void reloadConfig() {
        if (messageConfigFile == null) {
            messageConfigFile = new File(plugin.getDataFolder(), "messageConfig.yml");
        }
        if (translationConfigFile == null) {
            translationConfigFile = new File(plugin.getDataFolder(), "translationConfig.yml");
        }

        messageConfig = YamlConfiguration.loadConfiguration(messageConfigFile);
        translationConfig = YamlConfiguration.loadConfiguration(translationConfigFile);

        InputStream defaultMessagesStream = plugin.getResource("messageConfig.yml");
        if (defaultMessagesStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultMessagesStream));
            messageConfig.setDefaults(defaultConfig);
        }
        InputStream defaultTranslationConfig = plugin.getResource("translationConfig.yml");
        if (defaultTranslationConfig != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultTranslationConfig));
            translationConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getMessageConfig() {
        if (messageConfig == null) reloadConfig();
        return messageConfig;
    }
    public FileConfiguration getTranslationConfig() {
        if (translationConfig == null) reloadConfig();
        return translationConfig;
    }

    public void saveConfig() {
        if (messageConfig == null || messageConfigFile == null)
            return;
        try {
            getMessageConfig().save(this.messageConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Nie dziala");
        }
        if (translationConfig == null || translationConfigFile == null)
            return;
        try {
            getMessageConfig().save(this.translationConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Nie dziala");
        }
    }

    public void saveDefaultConfig() {
        if (messageConfigFile == null) {
            messageConfigFile = new File(plugin.getDataFolder(), "messageConfig.yml");
        }
        if (translationConfigFile == null) {
            translationConfigFile = new File(plugin.getDataFolder(), "translationConfig.yml");
        }

        if (!this.messageConfigFile.exists())
            plugin.saveResource("messageConfig.yml", false);
        if (!this.translationConfigFile.exists())
            plugin.saveResource("translationConfig.yml", false);
    }


}
