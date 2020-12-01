package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SetTrackLineCommand extends BasicCommandListener {

    public SetTrackLineCommand() {
        super("settrackline", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 3) throw new SyntaxException("/rankshowcase settrackline <track> <index> <message>");

        String trackName = arguments[0];
        int line = Integer.parseInt(arguments[1]);
        String message = String.join(" ", Arrays.copyOfRange(arguments, 2, arguments.length));

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");

        track.lines.set(line, message);
        player.sendMessage("Set track line");
    }
}
