package com.noitcereon.simplesorting;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * The class intended to handle all things related to in-game configuration of Noit's Simple Sorting.
 */
public class SimpleSortingConfig {

    /**
     * Controls where the sort button is located on the Y-axis. 0 is at the top, 100 is at the bottom.
     */
    @SerialEntry
    private Integer sortBtnYCoordinatePercentage = 5;

    /**
     * Controls where the sort button is located on the X-axis. 0 is all the way to the left, 100 is all the way to the right.
     */
    @SerialEntry
    private Integer sortBtnXCoordinatePercentage = 70;


    /**
     * YetAnotherConfigLib interface to help with mod configuration (saving and loading settings)
     */
    public static final ConfigClassHandler<SimpleSortingConfig> CONFIG_HANDLER = ConfigClassHandler.createBuilder(SimpleSortingConfig.class)
            .id(SimpleSortingModClient.CONFIG_ID)
            .serializer(configSerializer -> GsonConfigSerializerBuilder.create(configSerializer)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("simple-sorting-config.json5"))
                    .appendGsonBuilder(gsonBuilder -> gsonBuilder.setPrettyPrinting())
                    .setJson5(true)
                    .build())
            .build();

    public Integer getSortBtnYCoordinatePercentage() {
        if(sortBtnYCoordinatePercentage == null){
            SimpleSortingModClient.LOGGER.warn("Failed to load sortBtnYCoordinatePercentage. Falling back to default value.");
            return 5;
        }
        return sortBtnYCoordinatePercentage;
    }

    public Integer getSortBtnXCoordinatePercentage() {
        if(sortBtnXCoordinatePercentage == null){
            SimpleSortingModClient.LOGGER.warn("Failed to load sortBtnXCoordinatePercentage. Falling back to default value.");
            return 70;
        }
        return sortBtnXCoordinatePercentage;
    }

    public Screen createConfigScreen(Screen parentScreen) {
        boolean loadSuccess = CONFIG_HANDLER.load(); // To get the latest values from config file.
        if(!loadSuccess) SimpleSortingModClient.LOGGER.warn("Failed to load configuration file, updating the config may have no effect.");

        YetAnotherConfigLib configLib = YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Used for narration. Could be used to render a title in the future."))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Simple Sorting"))
                        .tooltip(Text.literal("Contains all the configuration available for Noit's Simple Sorting."))
                        .option(() -> {
                            Option.Builder<Integer> optionBuilder = Option.<Integer>createBuilder() // Defines type of config value, in this case Integer.
                                    .name(Text.literal("Sort Button Horizontal Position"))
                                    .description(OptionDescription.of(Text.literal("Controls the horizontal position of the Sort button (0 is left, 100 is right)")))
                                    .binding(
                                            70, // the default value
                                            () -> CONFIG_HANDLER.instance().sortBtnXCoordinatePercentage, // a field to get the current value from
                                            newVal -> CONFIG_HANDLER.instance().sortBtnXCoordinatePercentage = newVal
                                    )
                                    .controller(option -> IntegerSliderControllerBuilder.create(option)
                                            .range(0, 100)
                                            .step(1)
                                    );

                            return optionBuilder.build();
                        })
                        .option(() -> {
                            Option.Builder<Integer> optionBuilder = Option.<Integer>createBuilder()
                                    .name(Text.literal("Sort Button Vertical Position"))
                                    .description(OptionDescription.of(Text.literal("Controls the vertical position of the Sort button (0 is top, 100 is bottom)")))
                                    .binding(
                                            5, // the default value
                                            () -> CONFIG_HANDLER.instance().sortBtnYCoordinatePercentage, // a field to get the current value from
                                            newVal -> CONFIG_HANDLER.instance().sortBtnYCoordinatePercentage = newVal
                                    )
                                    .controller(option -> IntegerSliderControllerBuilder.create(option)
                                            .range(0, 100)
                                            .step(1)
                                    );

                            return optionBuilder.build();
                        })
                        .build()
                )
                .save(CONFIG_HANDLER::save)
                .build();
        return configLib.generateScreen(parentScreen);

    }
}
