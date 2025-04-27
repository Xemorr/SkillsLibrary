package me.xemor.skillslibrary2.triggers;

import me.xemor.configurationdata.Duration;
import me.xemor.configurationdata.JsonPropertyWithDefault;

public class LoopData extends TriggerData {

    @JsonPropertyWithDefault
    private Duration period = new Duration(1D);

    public long getPeriod() {
        return period.getDurationInTicks().orElse(20L);
    }

}
