package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;

public class HealthEffect extends ModifyEffect implements EntityEffect, TargetEffect {

	public HealthEffect(int effect, ConfigurationSection configurationSection) {
		super(effect, configurationSection);
	}

	@Override
	public boolean useEffect(Entity entity) {
		if (entity instanceof LivingEntity) {
			((LivingEntity) entity).setHealth((int) changeValue(((LivingEntity) entity).getHealth()));
		}
		return false;
	}

	@Override
	public boolean useEffect(Entity livingEntity, Entity target) {
		return useEffect(target);
	}

}

