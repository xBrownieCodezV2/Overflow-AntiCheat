package us.overflow.anticheat.packet.register;

import lombok.Getter;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_7_R4.PacketPlayOutTransaction;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.handlers.PacketHandler1_7_R4;
import us.overflow.anticheat.packet.type.WrappedPacket;

public final class PacketRegister1_7_R4 {

    public PacketRegister1_7_R4(final PlayerData playerData) {
        final EntityPlayer entityPlayer = ((CraftPlayer) playerData.getPlayer()).getHandle();

        OverflowAPI.INSTANCE.getPacketExecutor().execute(() -> new PacketHandler1_7_R4(entityPlayer.server, entityPlayer.playerConnection.networkManager, entityPlayer, playerData));
    }
}
