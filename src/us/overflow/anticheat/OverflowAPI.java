package us.overflow.anticheat;

import lombok.Getter;
import org.bukkit.Bukkit;

import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.command.CommandManager;
import us.overflow.anticheat.config.ConfigManager;
import us.overflow.anticheat.config.impl.MessageConfig;
import us.overflow.anticheat.config.impl.WebConfig;
import us.overflow.anticheat.data.Observable;
import us.overflow.anticheat.data.manager.PlayerDataManager;
import us.overflow.anticheat.data.type.CheckManager;
import us.overflow.anticheat.hook.ClassManager;
import us.overflow.anticheat.hook.DiscordManager;
import us.overflow.anticheat.judgement.JudgementManager;
import us.overflow.anticheat.listener.PlayerListener;
import us.overflow.anticheat.packet.VersionHandler;
import us.overflow.anticheat.processor.ProcessorManager;
import us.overflow.anticheat.trait.Startable;
import us.overflow.anticheat.utils.KeyFile;
import us.overflow.anticheat.utils.OSUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public enum OverflowAPI {
    INSTANCE;

    private OverflowPlugin plugin;
    
    private final ScheduledExecutorService judgementExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledExecutorService alertExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledExecutorService packetExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledExecutorService positionExecutor = Executors.newSingleThreadScheduledExecutor();

    private final ScheduledExecutorService authExecutor = Executors.newSingleThreadScheduledExecutor();

    private final Observable<Boolean> debug = new Observable<>(true);

    private final ProcessorManager processorManager = new ProcessorManager();
    private final PlayerDataManager playerDataManager = new PlayerDataManager();
    private final ConfigManager configManager = new ConfigManager();
    private final CommandManager commandManager = new CommandManager();
    private final JudgementManager judgementManager = new JudgementManager();

    private DiscordManager discordManager = null;

    private final VersionHandler versionHandler = new VersionHandler();
    public final List<Startable> startables = new ArrayList<>();

    public String key;

    public boolean disabled = false;
    
    public ClassManager classManager = new ClassManager();

    public void start(final OverflowPlugin plugin) {
        this.plugin = plugin;

            try {
                if (configManager.getConfig(WebConfig.class).getEnabled()) {
                    discordManager = (DiscordManager) Class.forName("us.overflow.anticheat.hook.DiscordManager").getConstructor().newInstance();
                } else {
                    discordManager = null;
                }
            }
            catch (Exception e) {
                discordManager = null;
            }

            classManager.start();

         
    }

    public void shutdown() {
        this.plugin = null;
        authExecutor.shutdownNow();
        judgementExecutor.shutdownNow();
        alertExecutor.shutdownNow();
        packetExecutor.shutdownNow();
        positionExecutor.shutdownNow();
    }
}
