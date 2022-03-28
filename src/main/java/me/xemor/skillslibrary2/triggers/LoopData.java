package me.xemor.skillslibrary2.triggers;

import org.bukkit.configuration.ConfigurationSection;

public class LoopData extends TriggerData {

    private final int period;

    public LoopData(int trigger, ConfigurationSection configurationSection) {
        super(trigger, configurationSection);
        period = Math.max((int) Math.round(configurationSection.getDouble("period", 1) * 20), 1);
    }

    public int getPeriod() {
        return period;
    }

}
