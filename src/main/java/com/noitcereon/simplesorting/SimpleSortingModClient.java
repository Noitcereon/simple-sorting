package com.noitcereon.simplesorting;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;


public class SimpleSortingModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof GenericContainerScreen) {
                Screens.getButtons(screen).add(
                        createSortButton(scaledWidth, scaledHeight)
                );
            }
            if(screen instanceof ShulkerBoxScreen){
                Screens.getButtons(screen).add(
                        createSortButton(scaledWidth, scaledHeight)
                );
            }
        });
    }

    @NotNull
    private ButtonWidget createSortButton(int scaledWidth, int scaledHeight) {
        return new ButtonWidget((scaledWidth / 100) * 70, (scaledHeight / 100) * 5, 40, 16, Text.literal("Sort"), (btn) -> {
            SimpleSortingMod.LOGGER.debug("Sort Button pressed");
            PacketByteBuf packet = PacketByteBufs.empty();
            ClientPlayNetworking.send(SimpleSortingMod.INVENTORY_SORT_REQUEST_ID, packet); // this triggers the global receiver registered with the same identifier.
        });
    }
}
