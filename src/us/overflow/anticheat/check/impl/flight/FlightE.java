package us.overflow.anticheat.check.impl.flight;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffectType;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.PositionUpdate;
import us.overflow.anticheat.utils.MathUtil;
import us.overflow.anticheat.utils.ReflectionUtil;

@CheckData(name = "Flight (E)")
public final class FlightE extends PositionCheck {
    private int airTicks, buffer;
    private double total;

    public FlightE(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get() && !playerData.getPositionManager().getTouchingClimbable().get();

        final int jumpModifier = MathUtil.getPotionEffectLevel(playerData.getPlayer(), PotionEffectType.JUMP);

        final double motionY = ReflectionUtil.getMotionY(playerData);
        final double deltaY = to.getY() - from.getY();

        final double jumpLimit = jumpModifier > 0 ? 1.25220341408 + (Math.pow(jumpModifier + 4.2, 2D) / 16D) : 1.25220341408;

        if (playerData.getVelocityManager().getMaxVertical() > 0.0 || playerData.getVelocityManager().getMaxHorizontal() > 0.0) {
            return;
        }

        if (touchingAir) {
            ++airTicks;

            if (airTicks > 9 && deltaY > 0.0 && motionY < 0.0) {
                total += deltaY;

                if (total > jumpLimit) {
                    if (++buffer > 4) {
                        this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                    }
                } else {
                    buffer = 0;
                }
            }
        } else {
            buffer = 0;
            airTicks = 0;
        }
    }
}
