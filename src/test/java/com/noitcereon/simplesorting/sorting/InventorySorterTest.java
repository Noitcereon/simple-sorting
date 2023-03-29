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
    private final int slotOne = 0;
    private final int slotTwo = 1;
    private final int slotThree = 2;
    private final int slotFour = 3;
    private final int slotFive = 4;
    private final int slotSix = 5;
    private final int slotEight = 7;
    private final int slotTen = 9;
    private final int slotThirtyTwo = 31;

    @BeforeAll
    public static void setup(){
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
    }
    @Test
    void given100DirtBlocks_WhenSortingInventory_ThenCombineIntoTwoStacksInFirstAndSecondSlot() {
        // Arrange
        Inventory inventory = new SimpleInventory(32);
        inventory.setStack(slotOne, new ItemStack(Items.DIRT, 5));
        inventory.setStack(slotFour, new ItemStack(Items.DIRT, 32));
        inventory.setStack(slotSix, new ItemStack(Items.DIRT, 32));
        inventory.setStack(slotThirtyTwo, new ItemStack(Items.DIRT, 32));
        int expectedDirtInSlotOne = 64;
        int expectedDirtInSlotTwo = 37;
        String expectedItemName = "Dirt";

        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack stackInSlotOne = inventory.getStack(slotOne);
        ItemStack stackInSlotTwo = inventory.getStack(slotTwo);
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
        inventory.setStack(slotOne, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setStack(slotFour, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setStack(slotTen, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setStack(slotSix, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setStack(slotEight, new ItemStack(Items.EGG, eggAmountThree));
        inventory.setStack(slotThirtyTwo, new ItemStack(Items.EGG, eggAmountTwo));

        String expectedItemName = "Egg";
        int expectedEggsInFirstFourSlots = 16;
        int expectedEggsInSlotFive = 5;

        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack stackInSlotOne = inventory.getStack(slotOne);
        ItemStack stackInSlotTwo = inventory.getStack(slotTwo);
        ItemStack stackInSlotThree = inventory.getStack(slotThree);
        ItemStack stackInSlotFour = inventory.getStack(slotFour);
        ItemStack stackInSlotFive = inventory.getStack(slotFive);

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
    void givenFilledShulkerAndNormalShulker_WhenSortingInventory_DontCombineFilledShulker(){
        fail("Test not implemented");
    }
    @Test
    void givenRenamedNameTagAndDefaultNameTags_WhenSortingInventory_DontCombineRenamedTagWithDefaultNameTags(){
        fail("Test not implemented");
    }
}