package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.configuration.Configuration;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class ReloadCommand extends BasicCommandListener {

    public ReloadCommand() {
        super("reload", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) throws FileNotFoundException {
        Configuration.load();
        player.sendMessage("Reloaded!");
    }
}
