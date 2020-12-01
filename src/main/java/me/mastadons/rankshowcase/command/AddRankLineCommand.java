package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AddRankLineCommand extends BasicCommandListener {

    public AddRankLineCommand() {
        super("addrankline", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 3) throw new SyntaxException("/rankshowcase addrankline <track> <rank> <line>");
        String trackName = arguments[0];
        String rankName = arguments[1];
        String message = String.join(" ", Arrays.copyOfRange(arguments, 2, arguments.length));

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");
        Rank rank = track.getRank(rankName);
        if (rank == null) throw new RuntimeException("Unknown rank.");

        rank.lines.add(message);
        player.sendMessage("Added rank line");
    }
}
