package us.overflow.anticheat.check.impl.autoclicker;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInArmAnimation;
import us.overflow.anticheat.utils.MathUtil;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "AutoClicker (C)", threshold = 9)
public final class AutoClickerC extends PacketCheck {
    private long lastArmAnimation;
    private double lastDeviation, lastAverage;
    private final Deque<Long> samples = new LinkedList<>();

    public AutoClickerC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInArmAnimation) {
            final long now = System.currentTimeMillis();
            final long delay = now - lastArmAnimation;

            if (delay > 1L && delay <= 150L && !playerData.getActionManager().getDigging().get()) {
                samples.add(delay);
            }

            if (samples.size() == 20) {
                final double average = samples.stream().mapToDouble(d -> d).average().orElse(0.0);
                final double stdDeviation = MathUtil.deviationSquared(samples);

                final double averageDelta = Math.abs(average - lastAverage);

                if (stdDeviation < 16.d && average > 8.d && stdDeviation == lastDeviation && averageDelta <= 1.5) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }

                lastAverage = average;
                lastDeviation = stdDeviation;
                samples.clear();
            }

            this.lastArmAnimation = now;
        }
    }
}
