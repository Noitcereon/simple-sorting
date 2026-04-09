package com.noitcereon.simplesorting.sorting;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOG = LoggerFactory.getLogger(InventorySorterTest.class);

    @BeforeAll
    public static void setup() {
        SharedConstants.tryDetectVersion(); // correct mapping?
        Bootstrap.bootStrap();
    }

    @Test
    void given100DirtBlocks_WhenSortingInventory_ThenCombineIntoTwoStacksInFirstAndSecondSlot() {
        // Arrange
        Container inventory = new SimpleContainer(32);
        inventory.setItem(slotOne, new ItemStack(Items.DIRT, 5));
        inventory.setItem(slotFour, new ItemStack(Items.DIRT, 32));
        inventory.setItem(slotSix, new ItemStack(Items.DIRT, 32));
        inventory.setItem(slotThirtyTwo, new ItemStack(Items.DIRT, 32));
        int expectedDirtInSlotOne = 64;
        int expectedDirtInSlotTwo = 37;
        String expectedItemName = "Dirt";

        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack stackInSlotOne = inventory.getItem(slotOne);
        ItemStack stackInSlotTwo = inventory.getItem(slotTwo);
        int actualAmountInSlotOne = stackInSlotOne.getCount();
        int actualAmountInSlotTwo = stackInSlotTwo.getCount();
        String actualItemNameSlotOne = stackInSlotOne.getItemName().getString();
        String actualItemNameSlotTwo = stackInSlotTwo.getItemName().getString();
        assertEquals(expectedItemName, actualItemNameSlotOne);
        assertEquals(expectedItemName, actualItemNameSlotTwo);
        assertEquals(expectedDirtInSlotOne, actualAmountInSlotOne);
        assertEquals(expectedDirtInSlotTwo, actualAmountInSlotTwo);
    }

    @Test
    void given69Eggs_WhenSortingInventory_ThenCombineIntoFiveStacks() {
        // Arrange
        Container inventory = new SimpleContainer(32);
        int eggAmountSixteen = 16;
        int eggAmountThree = 3;
        int eggAmountTwo = 2;
        inventory.setItem(slotOne, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setItem(slotFour, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setItem(slotTen, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setItem(slotSix, new ItemStack(Items.EGG, eggAmountSixteen));
        inventory.setItem(slotEight, new ItemStack(Items.EGG, eggAmountThree));
        inventory.setItem(slotThirtyTwo, new ItemStack(Items.EGG, eggAmountTwo));

        String expectedItemName = "Egg";
        int expectedEggsInFirstFourSlots = 16;
        int expectedEggsInSlotFive = 5;

        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack stackInSlotOne = inventory.getItem(slotOne);
        ItemStack stackInSlotTwo = inventory.getItem(slotTwo);
        ItemStack stackInSlotThree = inventory.getItem(slotThree);
        ItemStack stackInSlotFour = inventory.getItem(slotFour);
        ItemStack stackInSlotFive = inventory.getItem(slotFive);

        int actualAmountInSlotOne = stackInSlotOne.getCount();
        int actualAmountInSlotTwo = stackInSlotTwo.getCount();
        int actualAmountInSlotThree = stackInSlotThree.getCount();
        int actualAmountInSlotFour = stackInSlotFour.getCount();
        int actualAmountInSlotFive = stackInSlotFive.getCount();
        String actualItemNameSlotOne = stackInSlotOne.getItemName().getString();
        String actualItemNameSlotTwo = stackInSlotTwo.getItemName().getString();
        String actualItemNameSlotThree = stackInSlotThree.getItemName().getString();
        String actualItemNameSlotFour = stackInSlotFour.getItemName().getString();
        String actualItemNameSlotFive = stackInSlotFive.getItemName().getString();

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
    void given3Pickaxes_WhenSortingInventory_ThenDoNotCombine() {
        // Arrange
        Container inventory = new SimpleContainer(32);
        int expectedStackSize = 1;

        ItemStack expectedItemSlotOne = new ItemStack(Items.DIAMOND_PICKAXE, expectedStackSize);
        ItemStack expectedItemSlotTwo = new ItemStack(Items.DIAMOND_PICKAXE, expectedStackSize);
        ItemStack expectedItemSlotThree = new ItemStack(Items.DIAMOND_PICKAXE, expectedStackSize);
        inventory.setItem(slotTwo, expectedItemSlotOne);
        inventory.setItem(slotFour, expectedItemSlotTwo);
        inventory.setItem(slotTen, expectedItemSlotThree);

        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack actualItemSlotOne = inventory.getItem(slotOne);

        assertEquals(expectedItemSlotOne, actualItemSlotOne);
        assertEquals(expectedStackSize, actualItemSlotOne.getCount());
    }

    @Test
    void givenNamedShulkerAndNormalShulkers_WhenSortingInventory_DontCombine() {
        // Arrange
        Container inventory = new SimpleContainer(32);
        int expectedStackSize = 1;
        ItemStack renamedShulker = new ItemStack(Items.SHULKER_BOX, expectedStackSize);
        String shulkerCustomName = "Renamed Shulker";

        renamedShulker.update(DataComponents.CUSTOM_NAME, Component.literal(shulkerCustomName), text -> text);
        ItemStack defaultShulker = new ItemStack(Items.SHULKER_BOX, expectedStackSize);
        inventory.setItem(slotFour, renamedShulker);
        inventory.setItem(slotTwo, defaultShulker);
        inventory.setItem(slotThirtyTwo, defaultShulker);

        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack firstStackInInventory = inventory.getItem(slotOne);
        assertEquals(expectedStackSize, firstStackInInventory.getCount());
        assertTrue(inventory.hasAnyMatching(
                stack -> stack.getHoverName().getString()
                        .equals(shulkerCustomName)));
    }

    @Test
    void givenFilledShulkerAndNormalShulker_WhenSortingInventory_DontCombineFilledShulker() {
        // This test case is not implemented, since I could not find a way to put a shulkerbox with items into an inventory.
        // I tried by ItemStack from ShulkerBoxEntity.createNbt(), but this created a "0 Air" ItemStack.
        // Feel free to make a pull request with this test case implemented.
        LOG.warn("Test not implemented: givenFilledShulkerAndNormalShulker_WhenSortingInventory_DontCombineFilledShulker");
        assertTrue(true);
    }

    @Test
    void givenRenamedNameTagAndDefaultNameTags_WhenSortingInventory_DontCombineRenamedTagWithDefaultNameTags() {
        // Arrange
        Container inventory = new SimpleContainer(32);
        ItemStack defaultNameTag = new ItemStack(Items.NAME_TAG, 1);
        String tagName = "Bob";
        ItemStack renamedNameTag = new ItemStack(Items.NAME_TAG, 1);
        DataComponentPatch renamePatch = DataComponentPatch.builder().set(DataComponents.CUSTOM_NAME, Component.literal(tagName)).build();
        renamedNameTag.applyComponents(renamePatch);
        inventory.setItem(slotThree, defaultNameTag);
        inventory.setItem(slotEight, defaultNameTag);
        inventory.setItem(slotTen, defaultNameTag);
        inventory.setItem(slotFive, renamedNameTag);
        int expectedAccumulatedStacks = 3;
        // Act
        InventorySorter.sortInventory(inventory);

        // Assert
        ItemStack stackInSlotTwo = inventory.getItem(slotTwo);
        assertTrue(inventory.hasAnyMatching(itemStack -> itemStack.getHoverName().getString().equals(tagName)));
        assertEquals(expectedAccumulatedStacks, stackInSlotTwo.getCount());
    }
}