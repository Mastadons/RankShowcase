package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AddTrackLineCommand extends BasicCommandListener {

    public AddTrackLineCommand() {
        super("addtrackline", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 2) throw new SyntaxException("/rankshowcase addtrackline <track> <line>");
        String trackName = arguments[0];
        String message = String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length));

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");

        track.lines.add(message);
        player.sendMessage("Added track line");
    }
}
