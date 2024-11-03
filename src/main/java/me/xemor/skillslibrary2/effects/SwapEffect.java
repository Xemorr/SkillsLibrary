package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class SwapEffect extends Effect implements TargetEffect {

    public SwapEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        Location location1 = entity.getLocation();
        entity.teleport(target);
        target.teleport(location1);
    }
}
