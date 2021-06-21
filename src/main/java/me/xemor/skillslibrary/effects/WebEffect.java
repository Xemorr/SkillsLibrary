package me.xemor.skillslibrary.effects;

import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class WebEffect extends Effect implements TargetEffect {

    private final int duration;

    public WebEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        this.duration = (int) Math.round(configurationSection.getDouble("duration", 10) * 20);
    }

    @Override
    public boolean useEffect(LivingEntity boss, Entity target) {
        Location location = target.getLocation();
        World world = location.getWorld();
        Block block = world.getBlockAt(location);
        Material originalType = block.getType();
        block.setType(Material.COBWEB, false);
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(originalType, false);
            }
        }.runTaskLater(SkillsLibrary.getInstance(), this.duration);
        return false;
    }
}
