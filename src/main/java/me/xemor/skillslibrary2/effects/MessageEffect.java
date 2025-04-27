package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.ExpressiveMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private ExpressiveMessage message = new ExpressiveMessage();

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        sendMessage(execution, entity, target);
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        sendMessage(execution, entity, null);
    }

    public void sendMessage(Execution execution, @NotNull Entity entity, @Nullable Entity target) {
        if (entity instanceof Player player) {
            Audience audience = null;
            Component component = null;
            try {
                if (target instanceof Player targetPlayer) {
                    audience = SkillsLibrary.getBukkitAudiences().player(targetPlayer);
                    component = message.component(execution, entity, targetPlayer);
                }
                else if (target == null) {
                    audience = SkillsLibrary.getBukkitAudiences().player(player);
                    component = message.component(execution, entity);
                }
                else return;
            } catch (ParsingException e) {
                SkillsLibrary.getInstance().getLogger().severe("There is likely a legacy colour code in this message " + message.result(execution, entity, target));
                e.printStackTrace();
            }
            audience.sendMessage(component);
        }
    }
}
