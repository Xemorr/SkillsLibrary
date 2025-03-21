package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.ExpressiveMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.ParsingException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;

public class ActionBarEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private ExpressiveMessage message = new ExpressiveMessage();

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> sendMessage(execution, target));
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        sendMessage(execution, entity);
    }

    public void sendMessage(Execution execution, Entity entity) {
        if (entity instanceof Player player) {
            Audience audience = SkillsLibrary.getBukkitAudiences().player(player);
            try {
                Component renderedMessage = message.component(execution, Map.of("self", player, "player", player));
                audience.sendActionBar(renderedMessage);
            } catch (ParsingException e) {
                SkillsLibrary.getInstance().getLogger().severe("There is likely a legacy colour code in this message " + message);
                e.printStackTrace();
            }
        }
    }
}
