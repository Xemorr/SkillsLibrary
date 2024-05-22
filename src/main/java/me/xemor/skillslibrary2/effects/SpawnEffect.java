package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.entity.EntityData;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class SpawnEffect extends Effect implements EntityEffect, TargetEffect, LocationEffect {

    private final EntityData entityData;

    public SpawnEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection entitySection = configurationSection.getConfigurationSection("entity");
        if (entitySection == null) {
            SkillsLibrary.getInstance().getLogger().severe("You have not specified an entity! " + configurationSection.getCurrentPath() + ".entity");
        }
        entityData = EntityData.create(entitySection);
    }

    @Override
    public boolean useEffect(Entity entity) {
        useEffect(entity, entity.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        entityData.spawnEntity(location);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        useEffect(entity, target.getLocation());
        return false;
    }
}
