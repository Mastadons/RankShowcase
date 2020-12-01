package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

public class ListRankLinesCommand extends BasicCommandListener {

    public ListRankLinesCommand() {
        super("listranklines", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length != 2) throw new SyntaxException("/rankshowcase listranklines <track> <rank>");

        String trackName = arguments[0];
        String rankName = arguments[1];

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");
        Rank rank = track.getRank(rankName);
        if (rank == null) throw new RuntimeException("Unknown rank.");

        player.sendMessage("Lines:");
        rank.lines.forEach(line -> player.sendMessage(line));
    }
}
