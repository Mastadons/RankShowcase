package me.mastadons.rankshowcase.structure;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Track {

    public String name;

    public List<Rank> ranks = new ArrayList<>();
    public String world;

    public VectorLocation location;
    public List<String> lines = new ArrayList<>();

    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    public Location getLocation() {
        return location.toLocation(Bukkit.getWorld(world));
    }

    public Rank getRank(String rankName) {
        for (Rank rank : ranks) if (rank.name.equalsIgnoreCase(rankName)) return rank;
        return null;
    }
}
