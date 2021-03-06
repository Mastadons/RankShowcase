package me.mastadons.rankshowcase;

import me.mastadons.flag.FlagManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class RankShowcase extends JavaPlugin {

    public static final int PLUGIN_LOAD_FLAG = 0;
    public static final int PLUGIN_SAVE_FLAG = 1;

    public static final String PACKAGE_NAME = "me.mastadons.rankshowcase";
    public static RankShowcase INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        FlagManager.runFlaggedMethods(RankShowcase.PACKAGE_NAME, PLUGIN_LOAD_FLAG);
    }

    @Override
    public void onDisable() {
        FlagManager.runFlaggedMethods(RankShowcase.PACKAGE_NAME, PLUGIN_SAVE_FLAG);
        INSTANCE = null;
    }

    public static void print(Level level, String message, Object... parameters) {
        INSTANCE.getLogger().log(level, String.format(message, parameters));
    }
}
