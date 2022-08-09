package com.noitcereon.simplesorting.mixin;

import com.noitcereon.simplesorting.sorting.InventorySorter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ChestBlock.class)
public abstract class SortInventory {

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    protected ActionResult sortChestOnInteraction(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> callbackInfo) {
        if (world.isClient) return ActionResult.SUCCESS;
        Inventory chestInventory = ChestBlock.getInventory((ChestBlock) state.getBlock(), state, world, pos, false);

        if (chestInventory == null) return ActionResult.PASS;
        chestInventory.markDirty();

        boolean isSorted = InventorySorter.sortInventory(chestInventory);
        if (isSorted) return ActionResult.CONSUME_PARTIAL;
        else return ActionResult.PASS;
    }

}
