package com.noitcereon.simplesorting;

import com.noitcereon.simplesorting.item.Items;
import com.noitcereon.simplesorting.sorting.InventorySorter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSortingMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);


    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Initialising {}", ModInfo.MOD_ID);
        Registry.register(Registry.ITEM, new Identifier(ModInfo.MOD_ID, "my_item"), Items.MY_ITEM);
        FuelRegistry.INSTANCE.add(Items.MY_ITEM, 600);

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof GenericContainerScreen) {
                Screens.getButtons(screen).add(
                        new ButtonWidget((scaledWidth / 100) * 70, (scaledHeight / 100) * 5, 40, 16, Text.literal("Sort"), (btn) -> {
                            LOGGER.debug("Sort Button pressed");
                            Identifier identifier = new Identifier(ModInfo.MOD_ID, "inventory-sort-request");

                            ServerPlayNetworking.registerGlobalReceiver(identifier, (server, player, handler, buf, responseSender) -> {
                                client.execute(() -> {
                                    LOGGER.debug("Server received package: " + identifier.getNamespace() + " " + identifier.getPath());

                                    sortCurrentlyOpenInventory(player);
                                });
                            });

                            PacketByteBuf packet = PacketByteBufs.empty();
                            ClientPlayNetworking.send(identifier, packet); // this triggers the global receiver registered with the same identifier.
                        })
                );
            }
        });
    }

    private void sortCurrentlyOpenInventory(ServerPlayerEntity player) {
        GenericContainerScreenHandler screenHandler = (GenericContainerScreenHandler) player.currentScreenHandler;
        Inventory containerInventory = screenHandler.getInventory();
        InventorySorter.sortInventory(containerInventory);
        containerInventory.markDirty();
    }
}
