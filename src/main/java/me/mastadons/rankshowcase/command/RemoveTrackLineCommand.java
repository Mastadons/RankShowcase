package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RemoveTrackLineCommand extends BasicCommandListener {

    public RemoveTrackLineCommand() {
        super("removetrackline", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 2) throw new SyntaxException("/rankshowcase removetrackline <track> <index>");

        String trackName = arguments[0];
        int line = Integer.parseInt(arguments[1]);

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");

        track.lines.remove(line);
        player.sendMessage("Removed track line");
    }
}
