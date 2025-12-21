package com.noitcereon.simplesorting.sorting;

import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.OminousBottleAmplifierComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;

public class InventorySorter {
    private static final Logger log = LoggerFactory.getLogger(InventorySorter.class);

    private InventorySorter() {
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
        for (Map.Entry<Integer, ItemStack> entry : entriesWithValueSorted) {
            if (entryKeysMarkedForSkipping.contains(entry.getKey())) continue;
            ItemStack stack = entry.getValue();
            if (itemStacksOfTheSameItemExists(entriesWithValueSorted, stack.getItem()) && stack.getItem().getMaxCount() != 1) {
                List<Map.Entry<Integer, ItemStack>> stacksOfTheSameItem = entriesWithValueSorted
                        .stream()
                        .filter(entryValueHasTheSameItemId(stack))
                        // TODO: make entryValueHasSameAmplifier work. Currently I expect it to at least partially work, but break everything else.
                        .filter(entryValueHasSameAmplifier(stack))
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

    /**
     * Handles the non-uniqueness of the rawId on {@link Items#OMINOUS_BOTTLE}.
     *
     * @param stack
     * @return True, if the Ominious Bottle amplifier matches. Otherwise, false.
     */
    protected static Predicate<Map.Entry<Integer, ItemStack>> entryValueHasSameAmplifier(ItemStack stack) {
        /*
         * The Ominous bottle item has a DataComponent called OMINOUS_BOTTLE_AMPLIFIER, which has an integer value.
         * Ominous bottle stacks should only be combined if their OMINOUS_BOTTLE_AMPLIFIER matches others.
         */
        int stackAmplifier = findOminousBottleAmplifier(stack);
        return inventoryEntry -> {
            int entryAmplifier = findOminousBottleAmplifier(inventoryEntry.getValue());
            log.warn("Comparing stackAmplifier={} vs {} entryAmplifier", stackAmplifier, entryAmplifier);
            return stackAmplifier == entryAmplifier;
        };
    }

    /**
     * @param stack
     * @return Ominous bottle amplifier or -1 if it is not present on the ItemStack.
     */
    private static int findOminousBottleAmplifier(ItemStack stack) {
        boolean isOminousBottle = Item.getRawId(stack.getItem()) == Item.getRawId(Items.OMINOUS_BOTTLE);
        if (!isOminousBottle) {
            /* Early return, because it's not an ominous bottle and thus does not have a relevant amplifier. */
            return -1;
        }
        Item ominousBottle = stack.getItem();
        log.warn("Ominous bottle found! Item={}, rawId={}", ominousBottle, Item.getRawId(ominousBottle));
        ComponentMap componentMap = ominousBottle.getComponents();
        Component<OminousBottleAmplifierComponent> amplifierComponent = componentMap.getTyped(DataComponentTypes.OMINOUS_BOTTLE_AMPLIFIER);
        int stackAmplifier;
        for (Component<?> component : ominousBottle.getComponents()){
            log.warn("bottle component: {}", component);
        }
        if (amplifierComponent == null) {
            log.warn("Ominous bottle has no amplifier component!");
            stackAmplifier = -1;
        } else {
            stackAmplifier = amplifierComponent.value().value();
        }
        return stackAmplifier;
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
            if (isRenamedItem(entry)) {
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
     * @param entry A map entry representing a slot in an inventory {@code Map.Entry<Slot, ItemStack>}
     * @return True if it is a renamed item. Otherwise, false.
     */
    private static boolean isRenamedItem(Map.Entry<Integer, ItemStack> entry) {
        String defaultName = entry.getValue().getItem().getDefaultStack().getName().getString();
        String entryName = entry.getValue().getName().getString();
        return !entryName.equals(defaultName);
    }
}
