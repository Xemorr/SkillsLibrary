package me.xemor.skillslibrary2.triggers;

import me.xemor.skillslibrary2.conditions.ConditionList;
import me.xemor.skillslibrary2.effects.Effects;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class TriggerData {

    private final ConditionList conditions;
    private final int trigger;

    public TriggerData(int trigger, ConfigurationSection configurationSection) {
        ConfigurationSection conditionsSection = configurationSection.getConfigurationSection("conditions");
        if (conditionsSection != null) {
            conditions = new ConditionList(conditionsSection);
        } else {
            conditions = new ConditionList();
        }
        this.trigger = trigger;
    }

    @Nullable
    public static TriggerData create(int trigger, ConfigurationSection configurationSection) {
        try {
            return Trigger.getClass(trigger).getConstructor(int.class, ConfigurationSection.class).newInstance(trigger, configurationSection);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Throwable result = e;
            if (e instanceof InvocationTargetException c) {
                result = c.getCause();
            }
            Bukkit.getLogger().severe("Exception for " + Trigger.getClass(trigger).getName());
            result.printStackTrace();
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
