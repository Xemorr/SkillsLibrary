package me.xemor.skillslibrary2.effects;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class FurnaceBurnTimeEffect extends ModifyEffect implements LocationEffect {

    public FurnaceBurnTimeEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        BlockState state = location.getBlock().getState();
        if (state instanceof Furnace furnace) {
            short newFurnaceBurnTime = (short) Math.round(changeValue(furnace.getBurnTime()));
            if (newFurnaceBurnTime >= 0) {
                furnace.setBurnTime(newFurnaceBurnTime);
            }
        }
        return false;
    }
}
