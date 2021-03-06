package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class InsertRankLineCommand extends BasicCommandListener {

    public InsertRankLineCommand() {
        super("insertrankline", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        String trackName = arguments[0];
        String rankName = arguments[1];
        int line = Integer.parseInt(arguments[2]);
        String message = String.join(" ", Arrays.copyOfRange(arguments, 3, arguments.length));

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");
        Rank rank = track.getRank(rankName);
        if (rank == null) throw new RuntimeException("Unknown rank.");

        rank.lines.add(line, message);
        player.sendMessage("Inserted rank line");
    }
}
