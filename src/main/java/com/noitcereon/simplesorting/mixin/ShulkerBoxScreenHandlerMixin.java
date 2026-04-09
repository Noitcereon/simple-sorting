package com.noitcereon.simplesorting.mixin;

import com.noitcereon.simplesorting.interfaces.IExtendedShulkerBoxScreenHandler;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShulkerBoxMenu.class)
public abstract class ShulkerBoxScreenHandlerMixin implements IExtendedShulkerBoxScreenHandler {
    @Shadow @Final private Container container;

    public Container getInventory(){
        return this.container;
    }
}
