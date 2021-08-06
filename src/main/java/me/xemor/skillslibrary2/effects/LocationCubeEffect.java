package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.VectorData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class LocationCubeEffect extends WrapperEffect implements LocationEffect {

    private final int verticalRadius;
    private final int horizontalRadius;
    private final Vector offset;

    public LocationCubeEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        verticalRadius = configurationSection.getInt("verticalRadius");
        horizontalRadius = configurationSection.getInt("horizontalRadius");
        ConfigurationSection offsetSection = configurationSection.getConfigurationSection("offset");
        if (offsetSection == null) {
            offset = new Vector(0, 0, 0);
        }
        else {
            offset = new VectorData(offsetSection).getVector();
        }
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        Location cubeCentre = location.add(offset);
        for (int i = -horizontalRadius; i <= horizontalRadius; i++) {
            for (int j = -horizontalRadius; j <= horizontalRadius; j++) {
                for (int k = -verticalRadius; k <= verticalRadius; k++) {
                    handleEffects(entity, cubeCentre.clone().add(i, k, j));
                }
            }
        }
        return false;
    }

}
