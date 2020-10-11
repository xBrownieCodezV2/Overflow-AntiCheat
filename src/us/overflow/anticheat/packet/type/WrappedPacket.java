package us.overflow.anticheat.packet.type;

import lombok.Getter;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.processor.impl.PacketProcessor;

@Getter
public abstract class WrappedPacket {
    private final String simpleName = this.getClass().getSimpleName();

    public synchronized void parse(final PlayerData playerData) {
        OverflowAPI.INSTANCE.getProcessorManager().getProcessor(PacketProcessor.class).process(playerData, this);
    }
}
