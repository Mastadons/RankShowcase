package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import me.mastadons.rankshowcase.structure.VectorLocation;
import org.bukkit.entity.Player;

public class RemoveLocationCommand extends BasicCommandListener {

    public RemoveLocationCommand() {
        super("removelocation", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 2) throw new SyntaxException("/rankshowcase removelocation <track> <rank>");

        String trackName = arguments[0];
        String rankName = arguments[1];

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");
        Rank rank = track.getRank(rankName);
        if (rank == null) throw new RuntimeException("Unknown rank.");

        rank.removeLocation(VectorLocation.fromLocation(player.getLocation()));
        player.sendMessage("Removed rank location");
    }
}
