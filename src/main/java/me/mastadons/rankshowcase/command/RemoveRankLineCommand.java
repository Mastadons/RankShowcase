package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import me.mastadons.rankshowcase.structure.VectorLocation;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RemoveRankLineCommand extends BasicCommandListener {

    public RemoveRankLineCommand() {
        super("removerankline", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 3) throw new SyntaxException("/rankshowcase removerankline <track> <rank> <index>");

        String trackName = arguments[0];
        String rankName = arguments[1];
        int line = Integer.parseInt(arguments[2]);

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");
        Rank rank = track.getRank(rankName);
        if (rank == null) throw new RuntimeException("Unknown rank.");

        rank.lines.remove(line);
        player.sendMessage("Removed rank line");
    }
}
