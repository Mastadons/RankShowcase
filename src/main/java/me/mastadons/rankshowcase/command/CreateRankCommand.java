package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import me.mastadons.rankshowcase.structure.VectorLocation;
import org.bukkit.entity.Player;

public class CreateRankCommand extends BasicCommandListener {

    public CreateRankCommand() {
        super("createrank", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 3) throw new SyntaxException("/rankshowcase createrank <track> <rank> <groups...>");

        String trackName = arguments[0];
        String rankName = arguments[1];

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");
        if (track.getRank(rankName) != null) if (track == null) throw new RuntimeException("Rank already exists.");
        Rank rank = new Rank();
        rank.name = rankName;
        for (int i=2; i<arguments.length; i++) rank.groups.add(arguments[i]);

        track.ranks.add(rank);
        player.sendMessage("Created rank");
    }
}
