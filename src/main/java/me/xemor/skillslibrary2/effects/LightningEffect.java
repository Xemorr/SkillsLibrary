package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class LightningEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private final boolean fake = false;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        strikeLightning(entity);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> strikeLightning(target));
    }

    public void strikeLightning(Entity entity) {
        World world = entity.getWorld();
        if (fake) {
            world.strikeLightningEffect(entity.getLocation());
        }
        else {
            world.strikeLightning(entity.getLocation());
        }
    }
}
