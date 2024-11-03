package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class TeleportEffect extends Effect implements TargetEffect, ComplexLocationEffect {

    public TeleportEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        entity.teleport(location);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        entity.teleport(target);
    }
}
