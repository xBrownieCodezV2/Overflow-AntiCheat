package us.overflow.anticheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public final class WrappedPacketPlayInHeldItemSlot extends WrappedPacket{
    private final int slot;
}
