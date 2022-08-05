package com.noitcereon.simplesorting;

import com.noitcereon.simplesorting.item.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
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
	}
}
