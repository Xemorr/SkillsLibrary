package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.xemor.configurationdata.deserializers.text.TextDeserializer;

@JsonDeserialize(using = ConditionId.IdDeserializer.class)
public class ConditionId {

    private int id;

    public ConditionId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public class IdDeserializer extends TextDeserializer<ConditionId> {
        @Override
        public ConditionId deserialize(String text, JsonParser jsonParser, DeserializationContext deserializationContext) {
            return Conditions.getCondition(text);
        }
    }
}
