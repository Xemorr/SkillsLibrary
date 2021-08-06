package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class PlaceBlockEffect extends Effect implements LocationEffect {

    private Material type;

    public PlaceBlockEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String typeStr = configurationSection.getString("material", "STONE");
        try {
            type = Material.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid material! " + configurationSection.getCurrentPath() + ".material");
        }
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        World world = location.getWorld();
        world.getBlockAt(location).setType(type);
        return false;
    }
}
