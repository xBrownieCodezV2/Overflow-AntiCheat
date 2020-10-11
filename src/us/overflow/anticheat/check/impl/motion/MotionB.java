package us.overflow.anticheat.check.impl.motion;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffectType;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.PositionUpdate;
import us.overflow.anticheat.utils.ReflectionUtil;

@CheckData(name = "Motion (B)")
public final class MotionB extends PositionCheck {

    public MotionB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double deltaY = to.getY() - from.getY();
        final double motionY = ReflectionUtil.getMotionY(playerData);

        if (getPlayerData().getPositionManager().getTouchingSlab().get() || getPlayerData().getPositionManager().getTouchingStair().get() || getPlayerData().getPositionManager().getTouchingHalfBlocks().get() || (getPlayerData().getClientTicks()) < 100 || playerData.getPlayer().hasPotionEffect(PotionEffectType.JUMP) || playerData.getPositionManager().getTouchingIllegalBlocks().get() || playerData.getVelocityManager().getMaxVertical() > 0.0 || playerData.getVelocityManager().getMaxHorizontal() > 0.0) {
            return;
        }

        if (deltaY > 0.5 && motionY <= 0.0) {
          //  this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
        }
    }
}

