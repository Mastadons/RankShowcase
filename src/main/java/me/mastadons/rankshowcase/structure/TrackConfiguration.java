package me.mastadons.rankshowcase.structure;

import java.util.ArrayList;
import java.util.List;

public class TrackConfiguration {

    public List<Track> tracks = new ArrayList<>();

    public Track getTrack(String trackName) {
        for (Track track : tracks) if (track.name.equalsIgnoreCase(trackName)) return track;
        return null;
    }
}
