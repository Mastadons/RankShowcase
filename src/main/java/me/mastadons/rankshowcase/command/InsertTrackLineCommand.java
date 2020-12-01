package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class InsertTrackLineCommand extends BasicCommandListener {

    public InsertTrackLineCommand() {
        super("inserttrackline", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 3) throw new SyntaxException("/rankshowcase inserttrackline <track> <index> <line>");

        String trackName = arguments[0];
        int line = Integer.parseInt(arguments[1]);
        String message = String.join(" ", Arrays.copyOfRange(arguments, 2, arguments.length));

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");

        track.lines.add(line, message);
        player.sendMessage("Inserted track line");
    }
}
