package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.BlockDataData;
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

    private BlockData blockData;
    private final boolean updatePhysics;
    private final int revertsAfter;

    public PlaceBlockEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String typeStr = configurationSection.getString("material");
        if (typeStr == null) {
            BlockDataData blockDataData = new BlockDataData(configurationSection.getConfigurationSection("block"));
            blockData = blockDataData.getBlockData();
        }
        else {
            try {
                blockData = Bukkit.createBlockData(Material.valueOf(typeStr));
            } catch (IllegalArgumentException e) {
                SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid material! " + configurationSection.getCurrentPath() + ".material");
            }
        }
        updatePhysics = configurationSection.getBoolean("updatePhysics", true);
        revertsAfter = (int) Math.round(configurationSection.getDouble("revertsAfter", -1) * 20);

    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        Block block = location.getBlock();
        BlockData oldData = block.getBlockData();
        block.setBlockData(blockData, updatePhysics);
        if (revertsAfter > 0) {
            Bukkit.getScheduler().runTaskLater(SkillsLibrary.getInstance(), () -> {
                if (block.getBlockData().matches(blockData)) {
                    block.setBlockData(oldData, updatePhysics);
                }
            }, revertsAfter);
        }
        return false;
    }
}
