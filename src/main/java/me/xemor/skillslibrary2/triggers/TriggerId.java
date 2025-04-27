package me.xemor.skillslibrary2.triggers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.xemor.configurationdata.deserializers.text.TextDeserializer;

public class TriggerId {

    private int id;

    public TriggerId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
