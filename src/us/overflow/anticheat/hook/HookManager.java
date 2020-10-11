package us.overflow.anticheat.hook;

import org.bukkit.Bukkit;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.listener.PlayerListener;
import us.overflow.anticheat.trait.Startable;

/**
 * Created on 28/04/2020 Package us.overflow.anticheat.hook
 */
public final class HookManager {
    public HookManager() {
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.getProcessorManager());
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.getPlayerDataManager());
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.getJudgementManager());
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.getConfigManager());
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.getCommandManager());
        OverflowAPI.INSTANCE.startables.forEach(Startable::start);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), OverflowAPI.INSTANCE.getPlugin());
    }
}
