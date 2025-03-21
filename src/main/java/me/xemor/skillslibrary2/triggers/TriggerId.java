package me.xemor.skillslibrary2.triggers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.xemor.configurationdata.deserializers.text.TextDeserializer;

@JsonDeserialize(using = TriggerId.IdDeserializer.class)
public class TriggerId {

    private int id;

    public TriggerId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public class IdDeserializer extends TextDeserializer<TriggerId> {
        @Override
        public TriggerId deserialize(String text) {
            return Trigger.getTrigger(text);
        }
    }
}
