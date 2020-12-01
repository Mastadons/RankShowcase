package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.representation.RepresentationManager;
import org.bukkit.entity.Player;

public class UpdateCommand extends BasicCommandListener {

    public UpdateCommand() {
        super("update", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        RepresentationManager.INSTANCE.getUpdater().update();
        player.sendMessage("Updated representations!");
    }
}
