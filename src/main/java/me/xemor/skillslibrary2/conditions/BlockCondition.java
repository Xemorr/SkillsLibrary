package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.SetData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;


public class BlockCondition extends Condition implements LocationCondition {

    private SetData<Material> allowedMaterials;

    public BlockCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        allowedMaterials = new SetData<>(Material.class, "materials", configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity, Location location) {
        World world = location.getWorld();
        return allowedMaterials.inSet(world.getBlockAt(location).getType());
    }

}
