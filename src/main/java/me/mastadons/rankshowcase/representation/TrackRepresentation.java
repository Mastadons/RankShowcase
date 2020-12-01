package me.mastadons.rankshowcase.representation;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.Data;
import me.mastadons.rankshowcase.RankShowcase;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.Track;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

@Data
public class TrackRepresentation {

    private final Track track;

    private boolean summoned;

    private Hologram hologram;

    public void summon() {
        if (summoned) return;
        summoned = true;

        Location hologramLocation = track.getLocation().clone().add(0, calculateHologramHeight(), 0);
        hologram = HologramsAPI.createHologram(RankShowcase.INSTANCE, hologramLocation);
        for (String line : track.lines) {
            if (line.startsWith("item:")) hologram.appendItemLine(new ItemStack(Material.getMaterial(line.split(":")[1].trim())));
            else hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    public void despawn() {
        if (!summoned) return;
        summoned = false;

        hologram.delete();
    }

    private double calculateHologramHeight() {
        return 0.0625 * track.lines.size();
    }

}
