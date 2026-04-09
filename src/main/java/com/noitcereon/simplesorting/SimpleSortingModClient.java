package com.noitcereon.simplesorting;

import com.mojang.blaze3d.platform.InputConstants;
import com.noitcereon.simplesorting.networking.SortPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleSortingModClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID + "-client");

    public static final ResourceLocation CONFIG_ID = ResourceLocation.fromNamespaceAndPath(ModInfo.MOD_ID, "mod-config");

    // I assume it has to be static, because only ONE keybinding of this type must exist.
    // See https://fabricmc.net/wiki/tutorial:keybinds
    private static KeyMapping openConfigKeybinding;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing client part of Noit's Simple Sorting.");

        // Make sure Noit's Simple Sorting configuration is available.
        SimpleSortingConfig.CONFIG_HANDLER.load();

        // Register mod's custom keybinding(s).
        openConfigKeybinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                CONFIG_ID.toLanguageKey("open"),
                InputConstants.Type.KEYSYM, // Specifies whether it is a keyboard or mouse binding
                GLFW.GLFW_KEY_F4, // Default key
                ModInfo.MOD_ID // Category
        ));

        // Add buttons to inventory interfaces.
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof ContainerScreen) {
                Screens.getButtons(screen).add(
                        createSortButton(scaledWidth, scaledHeight)
                );
            }
            if (screen instanceof ShulkerBoxScreen) {
                Screens.getButtons(screen).add(
                        createSortButton(scaledWidth, scaledHeight)
                );
            }
        });

        // Responding to custom keybinding(s)
        SimpleSortingConfig sortConfig = new SimpleSortingConfig();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Ideally I'd like this only to happen, when a screen with sort button is open. I'm not sure how to do that though.
            // I tried to do it by checking client.currentScreen, but it does not seem to register END_CLIENT_TICK while in an open screen (based on brief trial-and-error).
            while(openConfigKeybinding.consumeClick()){
                Screen sortConfigScreen = sortConfig.createConfigScreen(null); // null is the default, when you're running about in-game.
                client.setScreen(sortConfigScreen);
            }
        });
    }

    @NotNull
    private Button createSortButton(int scaledWidth, int scaledHeight) {
        int xCoordinatePercent = SimpleSortingConfig.CONFIG_HANDLER.instance().getSortBtnXCoordinatePercentage();
        int yCoordinatePercent = SimpleSortingConfig.CONFIG_HANDLER.instance().getSortBtnYCoordinatePercentage();
        int placementCoordinateX = (scaledWidth / 100) * xCoordinatePercent;
        int placementCoordinateY = (scaledHeight / 100) * yCoordinatePercent;
        int buttonWidth = 40;
        int buttonHeight = 16;

        Button.Builder buttonBuilder = Button.builder(Component.literal("Sort"), button -> {
            SimpleSortingMod.LOGGER.debug("Sort Button pressed");
            ClientPlayNetworking.send(new SortPayload()); // this triggers the global receiver registered for this type of payload.
        })
        .bounds(placementCoordinateX, placementCoordinateY, buttonWidth, buttonHeight);

        return buttonBuilder.build();
    }
}
