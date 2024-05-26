package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class ItemMaterialEffect extends Effect implements ItemStackEffect {
    private Material material;
    public ItemMaterialEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        try {
            material = Material.valueOf(configurationSection.getString("material"));
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().warning("You have entered an invalid item material! " + configurationSection.getCurrentPath() + ".material");
        }
    }

    @Override
    public boolean useEffect(Entity entity, ItemStack item) {
        item.setType(material);
        return false;
    }
}
