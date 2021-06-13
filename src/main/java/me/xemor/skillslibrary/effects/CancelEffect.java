package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

public class CancelEffect extends Effect implements EntityEffect {

    public CancelEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        return true;
    }
}
