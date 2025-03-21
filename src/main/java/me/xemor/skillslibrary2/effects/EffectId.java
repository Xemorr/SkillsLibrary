package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.xemor.configurationdata.deserializers.text.TextDeserializer;

@JsonDeserialize(using = EffectId.IdDeserializer.class)
public class EffectId {

    private int id;

    public EffectId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public class IdDeserializer extends TextDeserializer<EffectId> {
        @Override
        public EffectId deserialize(String text) {
            return Effects.getEffect(text);
        }
    }
}
