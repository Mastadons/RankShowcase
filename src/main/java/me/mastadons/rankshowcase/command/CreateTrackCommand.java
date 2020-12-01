package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.rankshowcase.command.exception.SyntaxException;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import me.mastadons.rankshowcase.structure.VectorLocation;
import org.bukkit.entity.Player;

public class CreateTrackCommand extends BasicCommandListener {

    public CreateTrackCommand() {
        super("createtrack", "rankshowcase.admin");
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        if (arguments.length != 1) throw new SyntaxException("/rankshowcase createtrack <track>");
        String trackName = arguments[0];

        if (Configuration.INSTANCE.trackConfiguration.getTrack(trackName) != null)
            throw new RuntimeException("Track already exists.");
        Track track = new Track();
        track.name = trackName;
        track.world = player.getLocation().getWorld().getName();
        track.location = VectorLocation.fromLocation(player.getLocation());
        Configuration.INSTANCE.trackConfiguration.tracks.add(track);

        player.sendMessage("Created track");
    }
}
