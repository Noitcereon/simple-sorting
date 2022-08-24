package com.noitcereon.simplesorting.interfaces;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

public interface IExtendedShulkerBoxScreenHandler {
    Inventory getInventory();

    boolean canUse(PlayerEntity player);
}
