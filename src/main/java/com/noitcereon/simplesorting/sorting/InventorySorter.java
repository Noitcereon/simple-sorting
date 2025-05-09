package com.noitcereon.simplesorting.sorting;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;
import java.util.function.Predicate;

public class InventorySorter {
    private InventorySorter(){
        // Prevent instantiation of utility method
    }
    public static void sortInventory(Inventory inventory) {
        Map<Integer, ItemStack> inventoryMap = convertInventoryToMap(inventory);
        List<Map.Entry<Integer, ItemStack>> entriesWithValueSorted = inventoryMap.entrySet().stream().sorted((entry, entry2) -> {
            int itemId = Item.getRawId(entry.getValue().getItem());
            int itemId2 = Item.getRawId(entry2.getValue().getItem());

            return Integer.compare(itemId, itemId2);
        }).toList();

        Map<Integer, ItemStack> sortedInventoryMap = combineAndSortItemStacks(entriesWithValueSorted);
        inventory.clear();
        int slot = 0;
        for (Map.Entry<Integer, ItemStack> entry : sortedInventoryMap.entrySet()) {
                inventory.setStack(slot, entry.getValue());
                slot++;
        }
    }

    /**
     *
     * @param inventory An object that implements the default Minecraft Inventory interface.
     * @return <p>Map<Integer, ItemStack></p> <p>Integer = slot </p><p> ItemStack = the ItemStack.</p>
     */
    private static Map<Integer, ItemStack> convertInventoryToMap(Inventory inventory) {
        Map<Integer, ItemStack> inventoryMap = new HashMap<>(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            inventoryMap.put(i, stack);
        }
        return inventoryMap;
    }
    private static Map<Integer, ItemStack> combineAndSortItemStacks(List<Map.Entry<Integer, ItemStack>> entriesWithValueSorted) {
        Map<Integer, ItemStack> sortedInventoryMap = new HashMap<>();
        int nextSpot = 1;
        ArrayList<Integer> entryKeysMarkedForSkipping = new ArrayList<>();
        for (var entry : entriesWithValueSorted) {
            if (entryKeysMarkedForSkipping.contains(entry.getKey())) continue;
            ItemStack stack = entry.getValue();
            if (itemStacksOfTheSameItemExists(entriesWithValueSorted, stack.getItem()) && stack.getItem().getMaxCount() != 1) {
                List<Map.Entry<Integer, ItemStack>> stacksOfTheSameItem = entriesWithValueSorted
                        .stream()
                        .filter(entryValueHasTheSameItemId(stack))
                        .toList();

                Collection<ItemStack> combinedStacks = combineStacksOfTheSameItem(stacksOfTheSameItem);
                // Mark entries used, so we don't add additional items to the inventory.
                stacksOfTheSameItem.forEach(x -> entryKeysMarkedForSkipping.add(x.getKey()));

                for (ItemStack combinedStack : combinedStacks) {
                    sortedInventoryMap.put(nextSpot, combinedStack);
                    nextSpot++;
                }
            } else {
                sortedInventoryMap.putIfAbsent(nextSpot, entry.getValue());
            }
            nextSpot++;
        }
        return sortedInventoryMap;
    }
    private static boolean itemStacksOfTheSameItemExists(List<Map.Entry<Integer, ItemStack>> entriesWithValueSorted, Item item) {
        int count = entriesWithValueSorted.stream().filter(entry -> entry.getValue().getItem().equals(item)).toList().size();
        return count > 1;
    }

    private static Predicate<Map.Entry<Integer, ItemStack>> entryValueHasTheSameItemId(ItemStack stack) {
        return anyEntry -> Item.getRawId(anyEntry.getValue().getItem()) == Item.getRawId(stack.getItem());
    }

    private static Collection<ItemStack> combineStacksOfTheSameItem(List<Map.Entry<Integer, ItemStack>> stacksOfTheSameItem) {
        Item item = stacksOfTheSameItem.get(1).getValue().getItem();
        ItemStack nonFullStack = new ItemStack(item, 0);
        Collection<ItemStack> combinedStacks = new ArrayList<>();
        for (Map.Entry<Integer, ItemStack> entry : stacksOfTheSameItem) {
            if(isRenamedItem(entry)){
                combinedStacks.add(entry.getValue());
                continue;
            }
            int entryItemAmount = entry.getValue().getCount();
            int newAmount = nonFullStack.getCount() + entryItemAmount;
            if (newAmount >= item.getMaxCount()) {
                ItemStack fullStack = new ItemStack(item, item.getMaxCount());
                combinedStacks.add(fullStack);
                newAmount -= item.getMaxCount();
            }
            nonFullStack.setCount(newAmount);
        }
        if (nonFullStack.getCount() > 0) combinedStacks.add(nonFullStack);
        return combinedStacks;
    }

    /**
     *
     * @param entry A map entry representing a slot in an inventory {@code Map.Entry<Slot, ItemStack>}
     * @return True if it is a renamed item. Otherwise, false.
     */
    private static boolean isRenamedItem(Map.Entry<Integer, ItemStack> entry) {
        String defaultName = entry.getValue().getItem().getDefaultStack().getName().getString();
        String entryName = entry.getValue().getName().getString();
        return !entryName.equals(defaultName);
    }
}
