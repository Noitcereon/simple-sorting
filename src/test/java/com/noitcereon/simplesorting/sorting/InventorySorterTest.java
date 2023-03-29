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
    @Test
    void given69Eggs_WhenSortingInventory_ThenCombineIntoFiveStacks(){
        // Arrange
        Inventory inventory = new SimpleInventory(32);
        int eggAmountSixteen = 16;
        int eggAmountThree = 3;
        int eggAmountTwo = 2;
        inventory.setStack(0, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setStack(4, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setStack(20, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setStack(6, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setStack(8, new ItemStack(Items.EGG, eggAmountThree));
        inventory.setStack(12, new ItemStack(Items.EGG, eggAmountTwo));

        String expectedItemName = "Egg";
        int expectedEggsInFirstFourSlots = 16;
        int expectedEggsInSlotFive = 5;

        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack stackInSlotOne = inventory.getStack(0);
        ItemStack stackInSlotTwo = inventory.getStack(1);
        ItemStack stackInSlotThree = inventory.getStack(2);
        ItemStack stackInSlotFour = inventory.getStack(3);
        ItemStack stackInSlotFive = inventory.getStack(4);

        int actualAmountInSlotOne = stackInSlotOne.getCount();
        int actualAmountInSlotTwo = stackInSlotTwo.getCount();
        int actualAmountInSlotThree = stackInSlotThree.getCount();
        int actualAmountInSlotFour = stackInSlotFour.getCount();
        int actualAmountInSlotFive = stackInSlotFive.getCount();
        String actualItemNameSlotOne = stackInSlotOne.getName().getString();
        String actualItemNameSlotTwo = stackInSlotTwo.getName().getString();
        String actualItemNameSlotThree = stackInSlotThree.getName().getString();
        String actualItemNameSlotFour = stackInSlotFour.getName().getString();
        String actualItemNameSlotFive = stackInSlotFive.getName().getString();

        assertEquals(expectedItemName, actualItemNameSlotOne);
        assertEquals(expectedItemName, actualItemNameSlotTwo);
        assertEquals(expectedItemName, actualItemNameSlotThree);
        assertEquals(expectedItemName, actualItemNameSlotFour);
        assertEquals(expectedItemName, actualItemNameSlotFive);

        assertEquals(expectedEggsInFirstFourSlots, actualAmountInSlotOne);
        assertEquals(expectedEggsInFirstFourSlots, actualAmountInSlotTwo);
        assertEquals(expectedEggsInFirstFourSlots, actualAmountInSlotThree);
        assertEquals(expectedEggsInFirstFourSlots, actualAmountInSlotFour);
        assertEquals(expectedEggsInSlotFive, actualAmountInSlotFive);
    }

    @Test
    void given3Pickaxes_WhenSortingInventory_ThenDoNotCombine(){
        fail("Test not implemented");
    }
    @Test
    void givenNamedShulkerAndNormalShulkers_WhenSortingInventory_DontCombine(){
        fail("Test not implemented");
    }
    @Test
    void givenFilledShulkerAndNoramlShulker_WhenSortingInventory_DontCombineFilledShulker(){
        fail("Test not implemented");
    }
    @Test
    void givenRenamedNameTagAndDefaultNameTags_WhenSortingInventory_DontCombineRenamedTagWithDefaultNameTags(){
        fail("Test not implemented");
    }
}