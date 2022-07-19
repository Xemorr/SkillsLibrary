package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.comparison.SetData;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class VeinMinerEffect extends Effect implements LocationEffect {

    private SetData<Material> materials;
    private long delay;
    private boolean allowMultiTypeVein;

    public VeinMinerEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        materials = new SetData<>(Material.class,"types",configurationSection);
        if (materials.getSet().isEmpty()){
            throw new IllegalStateException("Materials property has not been specified.");
        }
        delay = Math.round(20 * configurationSection.getDouble("delay",0.05D));
        allowMultiTypeVein = configurationSection.getBoolean("allowMultiTypeVein",false);
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        breakLog(location.getBlock());
        return false;
    }

    private void breakLog(Block block) {
        Material currentType = block.getType();
        if (materials.inSet(currentType)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (BlockFace face : BlockFace.values()) {
                        Block blockToBreak = block.getRelative(face);
                        if (allowMultiTypeVein || blockToBreak.getType() == currentType) {
                            breakLog(blockToBreak);
                        }
                    }
                    block.breakNaturally();
                }
            }.runTaskLater(SkillsLibrary.getInstance(),delay);
        }
    }
}
