package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class FlyEffect extends Effect implements EntityEffect, TargetEffect {

    private final boolean fly;

    public FlyEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        fly = configurationSection.getBoolean("fly", true);
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        if (entity instanceof Player player) {
            player.setAllowFlight(fly);
            player.setFlying(fly);
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> useEffect(execution, target));
    }
}
