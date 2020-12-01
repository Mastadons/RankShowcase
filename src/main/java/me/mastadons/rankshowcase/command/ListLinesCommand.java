package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

public class ListLinesCommand extends BasicCommandListener {

    public ListLinesCommand() {
        super("listlines", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length != 2) throw new SyntaxException("/rankshowcase listlines <track>");

        String trackName = arguments[0];
        String rankName = arguments[1];

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");

        player.sendMessage("Lines:");
        track.lines.forEach(line -> player.sendMessage(line));
    }
}
