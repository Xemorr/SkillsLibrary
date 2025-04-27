package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;

public class TimeCondition extends Condition implements EntityCondition {

    @JsonPropertyWithDefault
    private RangeData time = new RangeData();

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        long time = entity.getWorld().getTime();
        return this.time.isInRange(time);
    }
}
