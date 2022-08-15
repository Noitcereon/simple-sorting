package com.noitcereon.simplesorting;

import com.noitcereon.simplesorting.sorting.InventorySorter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSortingMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);
    public static final Identifier INVENTORY_SORT_REQUEST_ID = new Identifier(ModInfo.MOD_ID, "inventory-sort-request");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("Initialising {}", ModInfo.MOD_ID);

        ServerPlayNetworking.registerGlobalReceiver(INVENTORY_SORT_REQUEST_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                LOGGER.debug("Server received package: " + INVENTORY_SORT_REQUEST_ID.getNamespace() + " " + INVENTORY_SORT_REQUEST_ID.getPath());

                sortCurrentlyOpenInventory(player);
            });
        });
    }

    private void sortCurrentlyOpenInventory(ServerPlayerEntity player) {
        ScreenHandler screenHandler = player.currentScreenHandler;
        if (screenHandler instanceof GenericContainerScreenHandler containerScreenHandler) {
            if (!screenHandler.canUse(player)){
                LOGGER.info("Failed to sort, because player cannot use the container anymore.");
                return;
            }

            Inventory containerInventory = containerScreenHandler.getInventory();
            InventorySorter.sortInventory(containerInventory);
            containerInventory.markDirty();
        }
        else{
            LOGGER.warn("player.currentScreenHandler returned {}. Simply Sorting uses GenericContainerScreenHandler to function.", screenHandler.getClass());
        }
    }
}
