package us.overflow.anticheat.packet.type;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import us.overflow.anticheat.packet.type.enums.EnumEntityUseAction;

@Getter @Setter
public final class WrappedPacketPlayInUseEntity extends WrappedPacket {
    private EnumEntityUseAction useAction;
    private Entity entity;
    private final String packetName = "PacketPlayInUseEntity";
}
