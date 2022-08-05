package com.noitcereon.simplesorting.itemgroup;

import com.noitcereon.simplesorting.ModInfo;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import com.noitcereon.simplesorting.item.Items;

public class ItemGroup {
    public static final net.minecraft.item.ItemGroup MY_ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(ModInfo.MOD_ID, "general"),
            () -> new ItemStack(Items.MY_ITEM));

    public static final net.minecraft.item.ItemGroup MY_OTHER_ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(ModInfo.MOD_ID, "other"))
            .icon(() -> new ItemStack(Blocks.ANVIL))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(Items.COAL));
                stacks.add(new ItemStack(Items.MY_ITEM));
            })
            .build();
}
