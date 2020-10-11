
package us.overflow.anticheat.check.impl.speed;

import org.bukkit.potion.PotionEffectType;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.packet.type.WrappedPacketPlayOutEntityVelocity;
import us.overflow.anticheat.packet.type.WrappedPacketPlayOutPosition;
import us.overflow.anticheat.utils.CustomLocation;

@CheckData(name = "Speed (E)")
public final class SpeedE extends PacketCheck {

	public SpeedE(final PlayerData playerData) {
		super(playerData);
	}

	private double lastX, lastZ, movementSpeed;

	private CustomLocation to;

	private boolean jumpPad, clientGround;

	private int vl, tpTicks;

	@Override
	public void process(WrappedPacket packet) {

		if (playerData.getPlayer().getAllowFlight())
			return;

		if (packet instanceof WrappedPacketPlayOutPosition) {
			tpTicks = 20;
		}

		if (packet instanceof WrappedPacketPlayInFlying) {
			WrappedPacketPlayInFlying wrappedPacketPlayInFlying = (WrappedPacketPlayInFlying) packet;
			if (!playerData.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
				if (wrappedPacketPlayInFlying.isHasPos()) {

					if (tpTicks > 0) {
						tpTicks--;
					}

					double x = wrappedPacketPlayInFlying.getX();
					double z = wrappedPacketPlayInFlying.getZ();

					CustomLocation location = new CustomLocation(wrappedPacketPlayInFlying.getX(),
							wrappedPacketPlayInFlying.getY(), wrappedPacketPlayInFlying.getZ());

					// Using packet even because its better, fuck you

					this.clientGround = wrappedPacketPlayInFlying.isOnGround();
					this.to = location;

					this.movementSpeed = getSpeed(x, z);
					this.lastX = x;
					this.lastZ = z;

					this.doCheck();
				}
			}
		}
	}

	private void doCheck() {
		if (this.movementSpeed > .622 && tpTicks < 1) {
			this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
		}
	}

	private double getSpeed(double cx, double cz) {
		double x = Math.abs(Math.abs(cx) - Math.abs(this.lastX));
		double z = Math.abs(Math.abs(cz) - Math.abs(this.lastZ));
		return Math.sqrt(x * x + z * z);
	}
}