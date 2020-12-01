package me.mastadons.rankshowcase.structure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VectorLocation {

    public double x, y, z;
    public float yaw, pitch;

    public static VectorLocation fromLocation(Location location) {
        return new VectorLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z, yaw, pitch);
    }

    public boolean blockEquals(VectorLocation location) {
        return this.toVector().equals(location.toVector());
    }

    private Vector toVector() {
        return new Vector((int)x, (int)y, (int)z);
    }
}
