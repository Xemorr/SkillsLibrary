package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class NOTCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition, ItemStackCondition {

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
        if (condition instanceof EntityCondition entityCondition) {
            return !entityCondition.isTrue(boss);
        }
        return true;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        if (condition instanceof TargetCondition targetCondition) {
            return !targetCondition.isTrue(entity, target);
        }
        return true;
    }

    @Override
    public boolean isTrue(Entity entity, ItemStack itemStack) {
        if (condition instanceof ItemStackCondition itemCondition) {
            return !itemCondition.isTrue(entity, itemStack);
        }
        return false;
    }

    @Override
    public boolean isTrue(Entity entity, Location location) {
        if (condition instanceof LocationCondition locationCondition) {
            return !locationCondition.isTrue(entity, location);
        }
        return false;
    }
}
