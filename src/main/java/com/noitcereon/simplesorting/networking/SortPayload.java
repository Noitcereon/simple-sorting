package com.noitcereon.simplesorting.networking;

import com.noitcereon.simplesorting.SimpleSortingMod;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

/**
 * A simple payload, which represents only it's {@link net.minecraft.util.Identifier}.
 * It is only used to send a Sort signal , thus further information is not required.
 */
public class SortPayload implements CustomPayload {
    public static final CustomPayload.Id<SortPayload> PAYLOAD_ID = new Id<>(SimpleSortingMod.INVENTORY_SORT_REQUEST_ID);
    public static final PacketCodec<PacketByteBuf, SortPayload> CODEC = PacketCodec.unit(new SortPayload());
    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }


    /**
     * The equals method is overridden to make the {@link PacketCodec#unit} able to work.
     * If it is not overridden it will cause a crash, when pressing Sort button due to inequal SortPayload.
     * @param that Another object
     * @return True, if the other object is a SortPayload with same PAYLOAD_ID. Otherwise, false.
     * @see PacketCodec#unit(Object)
     */
    @Override
    public boolean equals(Object that) {
        if(that == null) return false;
        if(!(that instanceof SortPayload thatSortPayload)) return false;
        return thatSortPayload.getId().equals(PAYLOAD_ID);
    }

    @Override
    public int hashCode() {
        // It doesn't seem relevant to change the hashcode. Overridden to get rid of SonarLint popup.
        return super.hashCode();
    }
}
