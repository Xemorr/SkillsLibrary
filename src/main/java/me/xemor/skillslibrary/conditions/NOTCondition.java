package me.xemor.skillslibrary.conditions;

import me.xemor.configurationdata.ConfigurationData;
import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class NOTCondition extends Condition implements EntityCondition, TargetCondition {

    private Condition condition;

    public NOTCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        ConfigurationSection conditionSection = configurationSection.getConfigurationSection("condition");
        if (conditionSection == null) {
            SkillsLibrary.getInstance().getLogger().severe("You haven't specified a condition for the NOT condition " + configurationSection.getCurrentPath());
            return;
        }
        String conditionTypeStr = conditionSection.getString("type", "");
        int conditionType = Conditions.getCondition(conditionTypeStr);
        this.condition = Condition.create(conditionType, conditionSection);
    }


    @Override
    public boolean isTrue(LivingEntity boss) {
        if (condition instanceof EntityCondition) {
            EntityCondition entityCondition = (EntityCondition) condition;
            return entityCondition.isTrue(boss);
        }
        return true;
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        if (condition instanceof TargetCondition) {
            TargetCondition targetCondition = (TargetCondition) condition;
            return targetCondition.isTrue(skillEntity, target);
        }
        return true;
    }
}
