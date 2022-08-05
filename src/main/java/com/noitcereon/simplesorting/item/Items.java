package com.noitcereon.simplesorting.item;

import com.noitcereon.simplesorting.itemgroup.ItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class Items extends net.minecraft.item.Items {
    public static final Item MY_ITEM = new MyItem(new FabricItemSettings().group(ItemGroup.MY_ITEM_GROUP));

}
