package us.overflow.anticheat;

import org.bukkit.plugin.java.JavaPlugin;

public final class OverflowPlugin extends JavaPlugin {
	public static OverflowPlugin instance;
    @Override
    public void onEnable() {
    	instance = this;
        OverflowAPI.INSTANCE.start(this);
    }

    @Override
    public void onDisable() {
        OverflowAPI.INSTANCE.shutdown();
    }
}
