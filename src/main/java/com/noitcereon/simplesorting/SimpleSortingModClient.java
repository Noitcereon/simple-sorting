package com.noitcereon.simplesorting;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleSortingModClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID + "-client");

    public static final Identifier CONFIG_ID = new Identifier(ModInfo.MOD_ID, "mod-config");
    @Override
    public void onInitializeClient() {
        // Make sure Noit's Simple Sorting configuration is available.
        SimpleSortingConfig.CONFIG_HANDLER.load();

        // Add buttons to inventory interfaces.
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof GenericContainerScreen) {
                Screens.getButtons(screen).add(
                        createSortButton(scaledWidth, scaledHeight)
                );
                // Temporary way to access config menu
                SimpleSortingConfig sortConfig = new SimpleSortingConfig();
                Screen sortConfigScreen = sortConfig.createConfigScreen(screen);
                createTemporarySortConfigBtn(client, screen, scaledWidth, scaledHeight, sortConfigScreen);
            }
            if(screen instanceof ShulkerBoxScreen){
                Screens.getButtons(screen).add(
                        createSortButton(scaledWidth, scaledHeight)
                );
            }
        });
    }

    private void createTemporarySortConfigBtn(MinecraftClient client, Screen screen, int scaledWidth, int scaledHeight, Screen sortConfigScreen) {
        // TODO: Find a different place to render the config button. Preferably in options.

        int placementCoordinateX = (scaledWidth / 100) * 20;
        int placementCoordinateY = (scaledHeight / 100) * 5;
        int buttonWidth = 40;
        int buttonHeight = 16;

        ButtonWidget.Builder buttonBuilder = ButtonWidget.builder(Text.literal("Sort Config"), button -> {
                    client.setScreen(sortConfigScreen);
                })
                .dimensions(placementCoordinateX, placementCoordinateY, buttonWidth, buttonHeight);

        ButtonWidget configButton = buttonBuilder.build();

        Screens.getButtons(screen)
                .add(configButton);
    }

    @NotNull
    private ButtonWidget createSortButton(int scaledWidth, int scaledHeight) {
        int xCoordinatePercent = SimpleSortingConfig.CONFIG_HANDLER.instance().getSortBtnXCoordinatePercentage();
        int yCoordinatePercent = SimpleSortingConfig.CONFIG_HANDLER.instance().getSortBtnYCoordinatePercentage();
        LOGGER.info("Temp debug info: X = {}, Y = {}", xCoordinatePercent, yCoordinatePercent);
        int placementCoordinateX = (scaledWidth / 100) * xCoordinatePercent;
        int placementCoordinateY = (scaledHeight / 100) * yCoordinatePercent;
        int buttonWidth = 40;
        int buttonHeight = 16;

        ButtonWidget.Builder buttonBuilder = ButtonWidget.builder(Text.literal("Sort"), button -> {
            SimpleSortingMod.LOGGER.debug("Sort Button pressed");
            PacketByteBuf packet = PacketByteBufs.empty();
            ClientPlayNetworking.send(SimpleSortingMod.INVENTORY_SORT_REQUEST_ID, packet); // this triggers the global receiver registered with the same identifier.
        })
        .dimensions(placementCoordinateX, placementCoordinateY, buttonWidth, buttonHeight);

        return buttonBuilder.build();
    }
}
