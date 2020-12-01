package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import org.bukkit.entity.Player;

public class RemoveGroupCommand extends BasicCommandListener {

    public RemoveGroupCommand() {
        super("removegroup", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length != 3) throw new SyntaxException("/rankshowcase removegroup <track> <rank> <group>");

        String trackName = arguments[0];
        String rankName = arguments[1];
        String groupName = arguments[2];

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");
        Rank rank = track.getRank(rankName);
        if (rank == null) throw new RuntimeException("Unknown rank.");

        rank.groups.remove(groupName);
        player.sendMessage("Added rank group");
    }
}
