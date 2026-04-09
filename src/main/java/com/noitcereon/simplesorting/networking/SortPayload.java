package com.noitcereon.simplesorting.networking;

import com.noitcereon.simplesorting.SimpleSortingMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * A simple payload, which represents only it's {@link net.minecraft.resources.ResourceLocation}.
 * It is only used to send a Sort signal , thus further information is not required.
 */
public class SortPayload implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SortPayload> PAYLOAD_ID = new Type<>(SimpleSortingMod.INVENTORY_SORT_REQUEST_ID);
    public static final StreamCodec<FriendlyByteBuf, SortPayload> SORT_PAYLOAD_CODEC = StreamCodec.unit(new SortPayload());
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PAYLOAD_ID;
    }


    /**
     * The equals method is overridden to make the {@link StreamCodec#unit} able to work.
     * If it is not overridden it will cause a crash, when pressing Sort button due to inequal SortPayload.
     * @param that Another object
     * @return True, if the other object is a SortPayload with same PAYLOAD_ID. Otherwise, false.
     * @see StreamCodec#unit(Object)
     */
    @Override
    public boolean equals(Object that) {
        if(that == null) return false;
        if(!(that instanceof SortPayload thatSortPayload)) return false;
        return thatSortPayload.type().equals(PAYLOAD_ID);
    }

    @Override
    public int hashCode() {
        // It doesn't seem relevant to change the hashcode. Overridden to get rid of SonarLint popup.
        return super.hashCode();
    }
}
