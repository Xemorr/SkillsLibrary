package me.xemor.skillslibrary2.triggers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.conditions.Condition;
import me.xemor.skillslibrary2.conditions.ConditionList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
public class TriggerData {

    @JsonPropertyWithDefault
    private ConditionList conditions = new ConditionList();
    @CompulsoryJsonProperty
    private String type;

    public void addConditions(ConditionList conditionList) {
        for (Condition condition : conditionList) conditions.appendCondition(condition);
    }

    public ConditionList getConditions() {
        return conditions;
    }

    public TriggerId getTriggerId() {
        return Trigger.getTrigger(type);
    }

}
