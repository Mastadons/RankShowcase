package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.configuration.Configuration;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SaveCommand extends BasicCommandListener {

    public SaveCommand() {
        super("save", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) throws IOException {
        Configuration.save();
        player.sendMessage("Saved!");
    }
}
