package com.noitcereon.simplesorting;

import com.noitcereon.simplesorting.interfaces.IExtendedShulkerBoxScreenHandler;
import com.noitcereon.simplesorting.networking.SortPayload;
import com.noitcereon.simplesorting.sorting.InventorySorter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSortingMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);
    public static final ResourceLocation INVENTORY_SORT_REQUEST_ID = ResourceLocation.fromNamespaceAndPath(ModInfo.MOD_ID, "inventory-sort-request");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("Initialising {}", ModInfo.MOD_ID);

        // Register payload must be done before registering payload receiver.
        PayloadTypeRegistry.playC2S().register(SortPayload.PAYLOAD_ID, SortPayload.SORT_PAYLOAD_CODEC);
        PayloadTypeRegistry.playS2C().register(SortPayload.PAYLOAD_ID, SortPayload.SORT_PAYLOAD_CODEC);

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

    private void sortCurrentlyOpenInventory(ServerPlayer player) {
        try {
            AbstractContainerMenu screenHandler = player.containerMenu;
            switch (screenHandler) {
                case null -> LOGGER.error("Sorting failed, because screenHandler is null.");
                case ChestMenu genericContainerScreenHandler -> {
                    if (playerCannotUse(player, screenHandler)) return;

                    Container containerInventory = genericContainerScreenHandler.getContainer();
                    InventorySorter.sortInventory(containerInventory);
                    containerInventory.setChanged();
                }
                case IExtendedShulkerBoxScreenHandler shulkerBoxScreenHandler -> {
                    if (playerCannotUse(player, screenHandler)) return;

                    Container containerInventory = shulkerBoxScreenHandler.getInventory();
                    InventorySorter.sortInventory(containerInventory);
                    containerInventory.setChanged();
                }
                default -> {
                    String currentScreenHandlerReturnType = screenHandler.getClass().getName();
                    LOGGER.warn("player.currentScreenHandler returned {}, which does not work with Simple Sorting.", currentScreenHandlerReturnType);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Sorting failed, because of this exception: {}", e.getMessage());
        }
    }

    private boolean playerCannotUse(ServerPlayer player, AbstractContainerMenu screenHandler) {
        if (!screenHandler.stillValid(player)) {
            LOGGER.info("Failed to sort, because player cannot use the container anymore.");
            return true;
        }
        return false;
    }
}
