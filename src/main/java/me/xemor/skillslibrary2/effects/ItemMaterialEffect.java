package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class ItemMaterialEffect extends Effect implements ItemStackEffect {

    @JsonPropertyWithDefault
    private Material material;

    @Override
    public void useEffect(Execution execution, Entity entity, ItemStack item) {
        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            item.setType(material);
        });
    }
}
