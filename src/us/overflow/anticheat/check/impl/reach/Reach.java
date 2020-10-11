package us.overflow.anticheat.check.impl.reach;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInUseEntity;
import us.overflow.anticheat.packet.type.enums.EnumEntityUseAction;
import us.overflow.anticheat.update.box.PlayerPosition;

import java.util.List;

@CheckData(name = "Reach")
public final class Reach extends PacketCheck {
    private boolean attacked = false;
    private Player target = null;

    private double buffer;

    public Reach(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInUseEntity) {
            final WrappedPacketPlayInUseEntity wrapper = (WrappedPacketPlayInUseEntity) packet;

            if (wrapper.getUseAction() == EnumEntityUseAction.ATTACK && wrapper.getEntity() != null && wrapper.getEntity() instanceof Player) {
                final Player entityTarget = (Player) wrapper.getEntity();

                this.attacked = true;
                this.target = entityTarget;
            }
        } else if (packet instanceof WrappedPacketPlayInFlying) {
            final boolean valid = attacked && target != null && playerData.getPlayer().getGameMode() != GameMode.CREATIVE;

            if (valid) {
                final PlayerData targetData = OverflowAPI.INSTANCE.getPlayerDataManager().getData(target);
                final PlayerPosition playerPosition = playerData.getPlayerPosition();

                synchronized (targetData.getLocations()) {
                    final double reach = Math.sqrt(targetData.getLocations().stream().mapToDouble(d -> d.getDistanceSquared(playerPosition)).min().orElse(0.0));

                    if (reach > 3.1 && reach < 6.f) {
                        if (++buffer > 2) {
                            this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                        }
                    } else {
                        buffer = Math.max(buffer - 0.5, 0);
                    }
                }

                attacked = false;
                target = null;
            }
        }
    }
}
