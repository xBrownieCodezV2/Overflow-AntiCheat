package us.overflow.anticheat.packet.register;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_9_R2.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_9_R2.PacketPlayOutTransaction;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.handlers.PacketHandler1_9_R2;

public final class PacketRegister1_9_R2 {

    public PacketRegister1_9_R2(final PlayerData playerData) {
        final EntityPlayer entityPlayer = ((CraftPlayer) playerData.getPlayer()).getHandle();

        OverflowAPI.INSTANCE.getPacketExecutor().execute(() -> new PacketHandler1_9_R2(entityPlayer.server, entityPlayer.playerConnection.networkManager, entityPlayer, playerData));
    }

    public void sendTransactionPacket(final PlayerData playerData, final short action) {
        final PacketPlayOutTransaction transaction = new PacketPlayOutTransaction((byte) 0, action, false);

        ((CraftPlayer) playerData.getPlayer()).getHandle().playerConnection.sendPacket(transaction);
    }

    public void sendKeepAlivePacket(final PlayerData playerData, int action) {
        final PacketPlayOutKeepAlive keepAlive = new PacketPlayOutKeepAlive(action);

        ((CraftPlayer) playerData.getPlayer()).getHandle().playerConnection.sendPacket(keepAlive);
    }
}
