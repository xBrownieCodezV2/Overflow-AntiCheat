package us.overflow.anticheat.packet.type;

import lombok.Getter;
import lombok.Setter;
import us.overflow.anticheat.packet.type.enums.EnumPlayerAction;

@Getter @Setter
public final class WrappedPacketPlayInEntityAction extends WrappedPacket {
    private EnumPlayerAction useAction;
}
