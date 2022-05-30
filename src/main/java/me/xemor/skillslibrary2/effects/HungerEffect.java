package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;

public class HungerEffect extends ModifyEffect implements EntityEffect, TargetEffect {

	public HungerEffect(int effect, ConfigurationSection configurationSection) {
		super(effect, configurationSection);
	}

	@Override
	public boolean useEffect(Entity entity) {
		if (entity instanceof HumanEntity) {
			((HumanEntity) entity).setFoodLevel((int) changeValue(((HumanEntity) entity).getFoodLevel()));
		}
		return false;
	}

	@Override
	public boolean useEffect(Entity livingEntity, Entity target) {
		return useEffect(target);
	}

}

