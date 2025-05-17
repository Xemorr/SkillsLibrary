package me.xemor.skillslibrary2.triggers;

import me.xemor.configurationdata.Duration;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;

public class LoopData extends TriggerData {

    @JsonPropertyWithDefault
    private Duration period = new Duration(1D);

    public long getPeriod() {
        if (period.getDurationInTicks().orElse(20L) <= 0) SkillsLibrary.getInstance().getLogger().severe("LoopData with period 0 found! This will cause an arithmetic error.");
        return period.getDurationInTicks().orElse(20L);
    }

}
