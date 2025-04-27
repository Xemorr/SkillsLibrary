package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class HealthEffect extends ModifyEffect implements EntityEffect, TargetEffect {

	@Override
	public void useEffect(Execution execution, Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			double newHealth = (int) changeValue(execution, livingEntity.getHealth());
			if (newHealth < 0) newHealth = 0;
			double maxHealth = livingEntity.getAttribute(Attribute.MAX_HEALTH).getValue();
			if (newHealth > maxHealth) newHealth = maxHealth;
			livingEntity.setHealth(newHealth);
		}
	}

	@Override
	public void useEffect(Execution execution, Entity entity, Entity target) {
		SkillsLibrary.getFoliaHacks().runASAP(target, () -> useEffect(execution, target));
	}

}

