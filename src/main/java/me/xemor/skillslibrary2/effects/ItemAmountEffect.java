package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class ItemAmountEffect extends ModifyEffect implements ItemStackEffect {

    public ItemAmountEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public void useEffect(Entity entity, ItemStack item) {
        item.setAmount((int) Math.round(changeValue(item.getAmount())));
    }

}
