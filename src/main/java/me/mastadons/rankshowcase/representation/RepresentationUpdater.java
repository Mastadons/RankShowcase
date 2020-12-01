package me.mastadons.rankshowcase.representation;

import lombok.Data;
import me.mastadons.rankshowcase.RankShowcase;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import me.mastadons.rankshowcase.structure.VectorLocation;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

@Data
public class RepresentationUpdater extends BukkitRunnable {

    private static Object SYNCHRONIZER = new Object();

    private final RepresentationManager manager;

    @Override
    public void run() {
        update();
    }

    public void update() {
        RankShowcase.INSTANCE.getLogger().info("Updating representations...");
        manager.deleteRankRepresentations();
        manager.deleteTrackRepresentations();

        for (Track track : Configuration.INSTANCE.trackConfiguration.tracks) {
            RankShowcase.INSTANCE.getLogger().info("Creating track representation for " + track.name);
            manager.createTrackRepresentation(track);
            Collections.sort(track.ranks, Comparator.comparingInt(rank -> rank.priority));
            for (Rank rank : track.ranks) {
                new Thread(() -> {
                    RankShowcase.INSTANCE.getLogger().info("Creating rank representation for " + rank.name);
                    List<OfflinePlayer> matchedPlayerList = manager.getMatchedPlayers(rank);
                    RankShowcase.print(Level.INFO, "Found %d matching players", matchedPlayerList.size());
                    for (OfflinePlayer player : matchedPlayerList) {
                        synchronized (SYNCHRONIZER) {
                            VectorLocation location = manager.getUnusedLocation(track.getWorld(), rank);
                            if (location == null) {
                                RankShowcase.print(Level.WARNING, "Rank %s does not have enough locations.", rank.name);
                                return;
                            }
                            manager.createRankRepresentation(player, location.toLocation(track.getWorld()), rank);
                        }
                    }
                }).start();
            }
        }
    }
}
