package us.overflow.anticheat.check.type;

import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;

public class PacketCheck extends Check<WrappedPacket> {

    public PacketCheck(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(WrappedPacket packet) {

    }
}
