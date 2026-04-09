package com.noitcereon.simplesorting.interfaces;


import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

public interface IExtendedShulkerBoxScreenHandler {
    Container getInventory();

    boolean canUse(Player player);
}
