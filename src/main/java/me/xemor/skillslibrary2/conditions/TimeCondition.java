package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class TimeCondition extends Condition implements EntityCondition {

    private final long minimumTime;
    private final long maximumTime;
    private final RangeData time;

    public TimeCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        minimumTime = configurationSection.getLong("minimumTime", 0);
        maximumTime = configurationSection.getLong("maximumTime", 24000);
        time = new RangeData(configurationSection.getString("time"));
    }


    @Override
    public boolean isTrue(Entity entity) {
        long time = entity.getWorld().getTime();
        if (minimumTime != 0 || maximumTime != 24000) {
            return time >= minimumTime && time <= maximumTime;
        }
        else {
            return this.time.isInRange(time);
        }
    }
}
