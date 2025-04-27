package me.xemor.skillslibrary2.execution;

import com.fasterxml.jackson.annotation.JsonCreator;
import me.xemor.skillslibrary2.SkillsLibrary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataHolder;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressiveMessage {

    private final String message;

    @JsonCreator
    public ExpressiveMessage(String message) {
        this.message = message;
        try {
            MiniMessage.miniMessage().deserialize(message);
        } catch (ParsingException e) {
            SkillsLibrary.getInstance().getLogger().severe("Cannot parse legacy colour codes i.e &5!");
            e.printStackTrace();
        }
    }

    public ExpressiveMessage() {message = "";}

    public String result(Execution execution) {
        return result(execution, Map.of());
    }

    public String result(Execution execution, Entity entity) {
        return result(execution, Map.of("self", entity));
    }

    public String result(Execution execution, Entity entity, Entity other) {
        if (other == null) return result(execution, entity);
        return result(execution, Map.of("self", entity, "other", other));
    }

    public String result(Execution execution, Map<String, PersistentDataHolder> containers) {
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)}");
        Matcher matcher = pattern.matcher(message);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            Expression expression = new Expression(matcher.group(1));
            double evaluatedValue = expression.result(execution, containers);
            matcher.appendReplacement(result, String.valueOf(evaluatedValue));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public Component component(Execution execution) {
        return component(execution, Map.of());
    }

    public Component component(Execution execution, Entity entity) {
        return component(execution, Map.of("self", entity));
    }

    public Component component(Execution execution, Entity entity, Entity other) {
        return component(execution, Map.of("self", entity, "other", other));
    }

    public Component component(Execution execution, Map<String, PersistentDataHolder> containers) {
        TagResolver[] resolvers = containers
                .entrySet()
                .stream()
                .filter((it) -> it.getValue() instanceof Entity)
                .map((it) -> Map.entry(it.getKey(), ((Entity) it.getValue()).getName()))
                .map((it) -> Placeholder.unparsed(it.getKey(), it.getValue()))
                .toArray(TagResolver[]::new);
        return MiniMessage.miniMessage().deserialize(result(execution, containers), resolvers);
    }
}
