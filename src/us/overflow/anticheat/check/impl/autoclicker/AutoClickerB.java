package us.overflow.anticheat.check.impl.autoclicker;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInArmAnimation;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.utils.EvictingList;
import us.overflow.anticheat.utils.MathUtil;

@CheckData(name = "AutoClicker (B)", threshold = 9)
public final class AutoClickerB extends PacketCheck {
    private int ticks, buffer;
    private double lastSkewnessTicks, lastKurtosisTicks, lastAverage, lastDeviation;

    private final EvictingList<Integer> samples = new EvictingList<>(20);

    public AutoClickerB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInArmAnimation) {
            if (!playerData.getActionManager().getDigging().get() && ticks < 4 && samples.add(ticks) && samples.size() > 10) {
                final double average = samples.stream().mapToDouble(d -> d).average().orElse(0.0);

                final double stdDeviation = MathUtil.deviationSquared(samples);

                final double skewnessTicks = MathUtil.getSkewness(samples);
                final double kurtosisTicks = MathUtil.getKurtosis(samples);

                final double sqrtDeviation = Math.sqrt(stdDeviation);

                if (skewnessTicks > 2.05 && kurtosisTicks < 0.0 && skewnessTicks == lastSkewnessTicks && kurtosisTicks == lastKurtosisTicks && average == lastAverage && sqrtDeviation == lastDeviation && sqrtDeviation < 0.46) {
                    if (++buffer > 2) {
                        this.handleViolation().addViolation(ViolationLevel.LOW).create();
                    }
                } else {
                    buffer = 0;
                }

                this.lastDeviation = sqrtDeviation;
                this.lastSkewnessTicks = skewnessTicks;
                this.lastKurtosisTicks = kurtosisTicks;
                this.lastAverage = average;
            }

            this.ticks = 0;
        } else if (packet instanceof WrappedPacketPlayInFlying) {
            ++ticks;
        }
    }
}
