package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class PlaceBlockEffect extends Effect implements LocationEffect {

    private Material type;
    private final boolean updatePhysics;
    private final int revertsAfter;

    public PlaceBlockEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String typeStr = configurationSection.getString("material", "STONE");
        updatePhysics = configurationSection.getBoolean("updatePhysics", true);
        revertsAfter = (int) Math.round(configurationSection.getDouble("revertsAfter", -1) * 20);
        try {
            type = Material.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid material! " + configurationSection.getCurrentPath() + ".material");
        }
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        World world = location.getWorld();
        Block block = location.getBlock();
        BlockData blockData = block.getBlockData();
        block.setType(type, updatePhysics);
        if (revertsAfter > 0) {
            Bukkit.getScheduler().runTaskLater(SkillsLibrary.getInstance(), () -> {
                block.setBlockData(blockData, updatePhysics);
            }, revertsAfter);
        }
        return false;
    }
}
