package me.mastadons.rankshowcase.representation;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.Data;
import me.mastadons.rankshowcase.RankShowcase;
import me.mastadons.rankshowcase.structure.Rank;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

@Data
public class RankRepresentation {

    private final String playerName;
    private final Rank rank;
    private final Location location;

    private boolean summoned;

    private NPC entity;
    private Hologram hologram;

    public void summon() {
        if (summoned) return;
        summoned = true;

        if (playerName == null) {
            summonEmpty();
            return;
        }

        entity = RepresentationManager.INSTANCE.getRegistry().createNPC(EntityType.PLAYER, playerName);
        entity.spawn(location);

        Location hologramLocation = location.clone().add(0, calculateHologramHeight(rank.lines.size()), 0);
        hologram = HologramsAPI.createHologram(RankShowcase.INSTANCE, hologramLocation);
        for (String line : rank.lines) {
            if (line.startsWith("item:")) hologram.appendItemLine(new ItemStack(Material.getMaterial(line.split(":")[1].trim())));
            else hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', line));
        }

        entity.setName(ChatColor.translateAlternateColorCodes('&', rank.playerName.replace("{player}", playerName)));
    }

    private void summonEmpty() {
        entity = RepresentationManager.INSTANCE.getRegistry().createNPC(EntityType.SKELETON, "");

        Location hologramLocation = location.clone().add(0, calculateHologramHeight(rank.emptyLines.size()), 0);
        hologram = HologramsAPI.createHologram(RankShowcase.INSTANCE, hologramLocation);
        for (String line : rank.emptyLines) {
            if (line.startsWith("item:")) hologram.appendItemLine(new ItemStack(Material.getMaterial(line.split(":")[1])));
            else hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    public void despawn() {
        if (!summoned) return;
        summoned = false;

        entity.destroy();
        RepresentationManager.INSTANCE.getRegistry().deregister(entity);

        hologram.delete();
    }

    private double calculateHologramHeight(int lines) {
        return 2.55 + (0.0625 * lines);
    }

}
