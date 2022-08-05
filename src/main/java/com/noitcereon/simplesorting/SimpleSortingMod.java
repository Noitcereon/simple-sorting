package com.noitcereon.simplesorting;

import com.noitcereon.simplesorting.item.MyItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSortingMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);
	public static final Item MY_ITEM = new MyItem(new FabricItemSettings().group(SimpleSortingMod.MY_ITEM_GROUP));
	public static final ItemGroup MY_ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier(ModInfo.MOD_ID, "general"),
			() -> new ItemStack(Blocks.COBBLED_DEEPSLATE));

	public static final ItemGroup MY_OTHER_ITEM_GROUP = FabricItemGroupBuilder.create(
			new Identifier(ModInfo.MOD_ID, "other"))
			.icon(() -> new ItemStack(Items.AMETHYST_SHARD))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(Items.COAL));
				stacks.add(new ItemStack(MY_ITEM));
				stacks.add(new ItemStack(MY_ITEM, 64));
			})
			.build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initialising {}", ModInfo.MOD_ID);
		Registry.register(Registry.ITEM, new Identifier(ModInfo.MOD_ID, "my_item"), MY_ITEM);
		FuelRegistry.INSTANCE.add(MY_ITEM, 600);
	}
}
