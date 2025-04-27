package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;

public class HungerEffect extends ModifyEffect implements EntityEffect, TargetEffect {

	@Override
	public void useEffect(Execution execution, Entity entity) {
		if (entity instanceof HumanEntity) {
			((HumanEntity) entity).setFoodLevel((int) changeValue(execution, ((HumanEntity) entity).getFoodLevel()));
		}
	}

	@Override
	public void useEffect(Execution execution, Entity entity, Entity target) {
		SkillsLibrary.getFoliaHacks().runASAP(target, () -> useEffect(execution, target));
	}

}

