package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.BlockDataData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.conditions.ConditionList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlaceBlockEffect extends Effect implements LocationEffect {

    private BlockData blockData;
    private final boolean updatePhysics;
    private final boolean isPacket;
    private final ConditionList revertConditions;
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
        isPacket = configurationSection.getBoolean("isPacket", false);
        revertConditions = new ConditionList(configurationSection.getConfigurationSection("revertConditions"));
        revertsAfter = (int) Math.round(configurationSection.getDouble("revertsAfter", -1) * 20);

    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        if (isPacket) {
            if (entity instanceof Player player) {
                player.sendBlockChange(location, blockData);
                if (revertsAfter > 0) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            boolean conditionsPassed = revertConditions.ANDConditions(entity, false, location);
                            if (conditionsPassed) {
                                player.sendBlockChange(location, location.getBlock().getBlockData());
                                cancel();
                            }
                        }
                    }.runTaskTimer(SkillsLibrary.getInstance(), revertsAfter, revertsAfter);
                }
            }
        }
        else {
            Block block = location.getBlock();
            BlockData oldData = block.getBlockData();
            block.setBlockData(blockData, updatePhysics);
            if (revertsAfter > 0) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        boolean conditionsPassed = revertConditions.ANDConditions(entity, false, location);
                        if (block.getBlockData().matches(blockData) && conditionsPassed) {
                            block.setBlockData(oldData, updatePhysics);
                        }
                        if (conditionsPassed) {
                            cancel();
                        }
                    }
                }.runTaskTimer(SkillsLibrary.getInstance(), revertsAfter, revertsAfter);
            }
        }
        return false;
    }
}
