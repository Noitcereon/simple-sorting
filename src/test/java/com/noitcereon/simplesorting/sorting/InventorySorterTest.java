package com.noitcereon.simplesorting.sorting;

import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class InventorySorterTest {

    @BeforeAll
    public static void setup(){
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
    }
    @Test
    void given100DirtBlocks_WhenSortingInventory_ThenCombineIntoTwoStacksInFirstAndSecondSlot() {
        // Arrange
        Inventory inventory = new SimpleInventory(32);
        inventory.setStack(0, new ItemStack(Items.DIRT, 5));
        inventory.setStack(4, new ItemStack(Items.DIRT, 32));
        inventory.setStack(20, new ItemStack(Items.DIRT, 32));
        inventory.setStack(6, new ItemStack(Items.DIRT, 32));
        int expectedDirtInSlotOne = 64;
        int expectedDirtInSlotTwo = 37;
        String expectedItemName = "Dirt";

        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack stackInSlotOne = inventory.getStack(0);
        ItemStack stackInSlotTwo = inventory.getStack(1);
        int actualAmountInSlotOne = stackInSlotOne.getCount();
        int actualAmountInSlotTwo = stackInSlotTwo.getCount();
        String actualItemNameSlotOne = stackInSlotOne.getName().getString();
        String actualItemNameSlotTwo = stackInSlotTwo.getName().getString();
        assertEquals(expectedItemName, actualItemNameSlotOne);
        assertEquals(expectedItemName, actualItemNameSlotTwo);
        assertEquals(expectedDirtInSlotOne, actualAmountInSlotOne);
        assertEquals(expectedDirtInSlotTwo, actualAmountInSlotTwo);
    }
}