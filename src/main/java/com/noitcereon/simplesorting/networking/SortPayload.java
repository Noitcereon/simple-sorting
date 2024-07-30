package com.noitcereon.simplesorting.networking;

import com.noitcereon.simplesorting.SimpleSortingMod;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class SortPayload implements CustomPayload {
    public static final CustomPayload.Id<SortPayload> PAYLOAD_ID = new Id<>(SimpleSortingMod.INVENTORY_SORT_REQUEST_ID);
    public static final PacketCodec<PacketByteBuf, SortPayload> CODEC = PacketCodec.unit(new SortPayload());
    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
