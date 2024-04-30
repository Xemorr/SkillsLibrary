package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class MessageEffect extends Effect implements EntityEffect, TargetEffect {

    private final String message;

    public MessageEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        this.message = configurationSection.getString("message");

        try {
            MiniMessage.miniMessage().deserialize(message, Placeholder.unparsed("player", ""));
        } catch (ParsingException e) {
            SkillsLibrary.getInstance().getLogger().severe("There is likely a legacy colour code at this location " + configurationSection.getCurrentPath() + ".message");
            e.printStackTrace();
        }
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        sendMessage(entity, target);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity) {
        sendMessage(entity, null);
        return false;
    }

    public void sendMessage(Entity entity, @Nullable Entity target) {
        if (entity instanceof Player player) {
            Audience audience = null;
            Component component = null;
            try {
                if (target instanceof Player targetPlayer) {
                    audience = SkillsLibrary.getBukkitAudiences().player(targetPlayer);
                    component = MiniMessage.miniMessage().deserialize(message, Placeholder.unparsed("player", target.getName()), Placeholder.unparsed("self", entity.getName()), Placeholder.unparsed("target", target.getName()));
                }
                else if (target == null) {
                    audience = SkillsLibrary.getBukkitAudiences().player(player);
                    component = MiniMessage.miniMessage().deserialize(message, Placeholder.unparsed("player", entity.getName()), Placeholder.unparsed("self", entity.getName()));
                }
                else {
                    return;
                }
            } catch (ParsingException e) {
                SkillsLibrary.getInstance().getLogger().severe("There is likely a legacy colour code in this message " + message);
                e.printStackTrace();
            }
            audience.sendMessage(component);
        }
    }
}
