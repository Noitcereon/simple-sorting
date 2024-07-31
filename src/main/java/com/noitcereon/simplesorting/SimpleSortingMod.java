package com.noitcereon.simplesorting;

import com.noitcereon.simplesorting.interfaces.IExtendedShulkerBoxScreenHandler;
import com.noitcereon.simplesorting.networking.SortPayload;
import com.noitcereon.simplesorting.sorting.InventorySorter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
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
    public static final Identifier INVENTORY_SORT_REQUEST_ID = Identifier.of(ModInfo.MOD_ID, "inventory-sort-request");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("Initialising {}", ModInfo.MOD_ID);

        // Register payload must be done before registering payload receiver.
        PayloadTypeRegistry.playC2S().register(SortPayload.PAYLOAD_ID, SortPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SortPayload.PAYLOAD_ID, SortPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(SortPayload.PAYLOAD_ID, (payload, context) -> {
            // Fabric API for MC v1.20.5 changed registerGlobalReceiver method from using the following variables:
            // server, player, handler, buf, responseSender
            // To using: CustomPayload.Id record and CustomPayload interface (which seems to contain same information, but wrapped...)
            context.server().execute(() -> {
                LOGGER.debug("Server received package: {} {}", INVENTORY_SORT_REQUEST_ID.getNamespace(), INVENTORY_SORT_REQUEST_ID.getPath());

                sortCurrentlyOpenInventory(context.player());
            });
        });
    }

    private void sortCurrentlyOpenInventory(ServerPlayerEntity player) {
        try {
            ScreenHandler screenHandler = player.currentScreenHandler;
            if(screenHandler == null){
                LOGGER.error("Sorting failed, because screenHandler is null.");
                return;
            }
            if (screenHandler instanceof GenericContainerScreenHandler genericContainerScreenHandler) {
                if (playerCannotUse(player, screenHandler)) return;

                Inventory containerInventory = genericContainerScreenHandler.getInventory();
                InventorySorter.sortInventory(containerInventory);
                containerInventory.markDirty();
            } else if (screenHandler instanceof IExtendedShulkerBoxScreenHandler shulkerBoxScreenHandler) {
                if (playerCannotUse(player, screenHandler)) return;

                Inventory containerInventory = shulkerBoxScreenHandler.getInventory();
                InventorySorter.sortInventory(containerInventory);
                containerInventory.markDirty();

            } else {
                String currentScreenHandlerReturnType = screenHandler.getClass().getName();
                LOGGER.warn("player.currentScreenHandler returned {}, which does not work with Simple Sorting.", currentScreenHandlerReturnType);
            }
        } catch (Exception e) {
            LOGGER.error("Sorting failed, because of this exception: {}", e.getMessage());
        }
    }

    private boolean playerCannotUse(ServerPlayerEntity player, ScreenHandler screenHandler) {
        if (!screenHandler.canUse(player)) {
            LOGGER.info("Failed to sort, because player cannot use the container anymore.");
            return true;
        }
        return false;
    }
}
