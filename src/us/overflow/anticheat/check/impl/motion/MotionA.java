package us.overflow.anticheat.check.impl.motion;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.utils.MathUtil;

@CheckData(name = "Motion (A)")
public final class MotionA extends PacketCheck {
    public MotionA(final PlayerData playerData) {
        super(playerData);
    }

    private boolean lastOnGround;
    private double lastPosY;
    private int jumpPotionTicks, buffer;

    @Override
    public void process(final WrappedPacket wrappedPacket) {

        if (wrappedPacket instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) wrappedPacket;

            if (wrapper.isHasPos()) {
                final double posY = wrapper.getX();
                final double deltaY = (posY - lastPosY);

                final boolean clientGround = wrapper.isOnGround();

                final boolean jumpBoost = playerData.getPlayer().hasPotionEffect(PotionEffectType.JUMP);

                if (jumpBoost) {
                    ++jumpPotionTicks;
                } else {
                    jumpPotionTicks = Math.max(jumpPotionTicks - 1, 0);
                }

                if (!clientGround && lastOnGround) {
                    double threshold = 0.41999998688697815F;

                    if (jumpPotionTicks > 0) {
                        threshold = (threshold + MathUtil.getPotionEffectLevel(getPlayerData().getPlayer(), PotionEffectType.JUMP) * 0.1F);
                    }

                    if (Math.abs(deltaY) > 0.0 && ((deltaY > threshold || deltaY < threshold))) {
                        if (++buffer > 1) {
                            //this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                        }
                    } else {
                        buffer = 0;
                    }
                }

                this.lastPosY = posY;
                this.lastOnGround = clientGround;
            }
        }
    }
}