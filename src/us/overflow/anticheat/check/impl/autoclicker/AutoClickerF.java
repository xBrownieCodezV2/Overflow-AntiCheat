package us.overflow.anticheat.check.impl.autoclicker;

import org.bukkit.Bukkit;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInArmAnimation;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;

@CheckData(name = "AutoClicker (F)", threshold = 2)
public final class AutoClickerF extends PacketCheck {
    private int swings, ticks;

    public AutoClickerF(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            if (++ticks == 20) {
                final boolean invalid = swings > 20;

                if (invalid) {
                    this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                }

                ticks = swings = 0;
            }
        } else if (packet instanceof WrappedPacketPlayInArmAnimation) {
            final boolean digging = playerData.getActionManager().getSwinging().get();

            if (digging) {
                ++swings;
            }
        }
    }
}
