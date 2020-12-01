package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import me.mastadons.rankshowcase.structure.VectorLocation;
import org.bukkit.entity.Player;

public class SetTrackLocationCommand extends BasicCommandListener {

    public SetTrackLocationCommand() {
        super("settracklocation", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length < 1) throw new SyntaxException("/rankshowcase settracklocation <track>");
        String trackName = arguments[0];

        Track track = Configuration.INSTANCE.trackConfiguration.getTrack(trackName);
        if (track == null) throw new RuntimeException("Unknown track.");

        track.location = VectorLocation.fromLocation(player.getLocation());
        player.sendMessage("Set track location");
    }
}
