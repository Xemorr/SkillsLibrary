package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

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
    public boolean isTrue(Entity boss) {
        if (condition instanceof EntityCondition) {
            EntityCondition entityCondition = (EntityCondition) condition;
            return !entityCondition.isTrue(boss);
        }
        return true;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        if (condition instanceof TargetCondition) {
            TargetCondition targetCondition = (TargetCondition) condition;
            return !targetCondition.isTrue(entity, target);
        }
        return true;
    }
}
