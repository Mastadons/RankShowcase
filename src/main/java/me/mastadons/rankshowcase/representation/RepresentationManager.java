package me.mastadons.rankshowcase.representation;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.Data;
import me.mastadons.flag.DefinedFlag;
import me.mastadons.flag.FlagManager;
import me.mastadons.rankshowcase.RankShowcase;
import me.mastadons.rankshowcase.configuration.Configuration;
import me.mastadons.rankshowcase.database.DatabaseManager;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import me.mastadons.rankshowcase.structure.VectorLocation;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Data
@FlagManager.FlaggedClass
public class RepresentationManager {

    public static RepresentationManager INSTANCE;

    @FlagManager.FlaggedMethod(flag = DefinedFlag.PLUGIN_LOAD, priority = 10)
    public static void load() {
        INSTANCE = new RepresentationManager();
        INSTANCE.startUpdater();
    }

    private final RepresentationUpdater updater = new RepresentationUpdater(this);
    private final NPCRegistry registry;

    private final List<TrackRepresentation> trackRepresentationList = new ArrayList<>();
    private final List<RankRepresentation> rankRepresentationList = new ArrayList<>();
    private final Map<UUID, String> cachedMinecraftNames = new HashMap<>();

    public RepresentationManager() {
        this.registry = CitizensAPI.createNamedNPCRegistry("rankshowcase", new MemoryNPCDataStore());
    }

    public void startUpdater() {
        updater.runTaskTimer(RankShowcase.INSTANCE, 10L, Configuration.INSTANCE.updateFrequency);
    }

    public List<OfflinePlayer> getMatchedPlayers(Rank rank) {
        List<List<UUID>> matchedPlayers = new ArrayList<>();
        for (String groupName : rank.groups) {
            try {
                List<UUID> matchedGroup = new ArrayList<>();
                for (UUID uuid : DatabaseManager.INSTANCE.hasPermission("group." + groupName)) {
                    if (Configuration.INSTANCE.ignoredPlayers.contains(uuid.toString())) continue;
                    matchedGroup.add(uuid);
                }
                matchedPlayers.add(matchedGroup);
            } catch (SQLException exception) { exception.printStackTrace(); }
        }
        return existsInAll(matchedPlayers).stream().map(id -> Bukkit.getOfflinePlayer(id)).collect(Collectors.toList());
    }

    public <T> List<T> existsInAll(List<List<T>> lists) {
        List<T> finalList = lists.get(0);
        for (int i=1; i<lists.size(); i++) {
            int finalI = i;
            finalList.removeIf(t -> !lists.get(finalI).contains(t));
        }
        return finalList;
    }

    public RankRepresentation createRankRepresentation(OfflinePlayer player, Location location, Rank rank) {
        String playerName = null;
        try {
            playerName = getMinecraftName(player.getUniqueId());
        } catch (IOException exception) {
            RankShowcase.print(Level.WARNING, "Encountered exception fetching username for %s", player.getUniqueId().toString());
        }
        RankRepresentation rankRepresentation = new RankRepresentation(playerName, rank, location);
        Bukkit.getScheduler().scheduleSyncDelayedTask(RankShowcase.INSTANCE, rankRepresentation::summon);
        rankRepresentationList.add(rankRepresentation);
        return rankRepresentation;
    }

    public TrackRepresentation createTrackRepresentation(Track track) {
        TrackRepresentation trackRepresentation = new TrackRepresentation(track);
        trackRepresentation.summon();
        trackRepresentationList.add(trackRepresentation);
        return trackRepresentation;
    }

    public VectorLocation getUnusedLocation(World world, Rank rank) {
        for (VectorLocation location : rank.locations)
            if (getRankRepresentation(world, location) == null) return location;
        return null;
    }

    public RankRepresentation getRankRepresentation(World world, VectorLocation location) {
        for (RankRepresentation rankRepresentation : rankRepresentationList)
            if (rankRepresentation.getLocation().equals(location.toLocation(world))) return rankRepresentation;
        return null;
    }

    public void deleteRankRepresentations() {
        for (RankRepresentation rankRepresentation : new ArrayList<>(rankRepresentationList))
            rankRepresentation.despawn();
        rankRepresentationList.clear();
    }

    public void deleteTrackRepresentations() {
        for (TrackRepresentation trackRepresentation : new ArrayList<>(trackRepresentationList))
            trackRepresentation.despawn();
        trackRepresentationList.clear();
    }

    public String getMinecraftName(UUID id) throws IOException {
        if (cachedMinecraftNames.containsKey(id)) return cachedMinecraftNames.get(id);
        URL url = new URL("https://api.mojang.com/user/profiles/" + serializeUUID(id) + "/names");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int status = connection.getResponseCode();
        if (status != 200) throw new IOException("Invalid response code");

        StringBuffer content = new StringBuffer();
        Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()));
        while (scanner.hasNextLine()) content.append(scanner.nextLine());

        scanner.close();
        connection.disconnect();

        JsonArray contentJSON = new Gson().fromJson(content.toString(), JsonArray.class);
        String name = contentJSON.get(contentJSON.size()-1).getAsJsonObject().get("name").getAsString();
        cachedMinecraftNames.put(id, name);
        return name;
    }

    private String serializeUUID(UUID id) {
        return id.toString().replace("-", "");
    }
}
