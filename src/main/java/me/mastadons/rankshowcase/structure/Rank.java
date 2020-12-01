package me.mastadons.rankshowcase.structure;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Rank {

    public String name;
    public int priority;
    public String playerName = "{player}";

    public List<String> groups = new ArrayList<>();
    public List<String> emptyLines = new ArrayList<>();
    public List<String> lines = new ArrayList<>();

    public List<VectorLocation> locations = new ArrayList<>();

    public void removeLocation(VectorLocation location) {
        for (VectorLocation vectorLocation : new ArrayList<>(locations)) {
            if (vectorLocation.blockEquals(location)) locations.remove(vectorLocation);
        }
    }
}
