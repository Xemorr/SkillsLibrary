package me.xemor.skillslibrary2.triggers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.conditions.ConditionList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public class TriggerData {

    @JsonPropertyWithDefault
    private ConditionList conditions = new ConditionList();

    public TriggerId getTriggerId() {
        return getTriggerId();
    }

    public ConditionList getConditions() {
        return conditions;
    }

}
