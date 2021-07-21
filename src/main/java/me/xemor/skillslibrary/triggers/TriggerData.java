package me.xemor.skillslibrary.triggers;

import me.xemor.skillslibrary.SkillsLibrary;
import me.xemor.skillslibrary.conditions.ConditionList;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class TriggerData {

    private final ConditionList conditions;
    private final int trigger;

    public TriggerData(ConfigurationSection configurationSection) {
        ConfigurationSection conditionsSection = configurationSection.getConfigurationSection("conditions");
        conditions = new ConditionList(conditionsSection);
        this.trigger = Trigger.getTrigger(configurationSection.getString("trigger"));
        if (trigger == -1) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid trigger type at " + configurationSection.getCurrentPath() + ".trigger");
        }
    }

    @Nullable
    public static TriggerData create(int trigger, ConfigurationSection configurationSection) {
        try {
            return Trigger.getClass(trigger).getConstructor(int.class, ConfigurationSection.class).newInstance(trigger, configurationSection);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTrigger() {
        return trigger;
    }

    public ConditionList getConditions() {
        return conditions;
    }

}
