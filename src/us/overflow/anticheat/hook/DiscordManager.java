package us.overflow.anticheat.hook;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.config.impl.WebConfig;

import java.awt.*;

public final class DiscordManager {

    public void log(final Check check) {
        final String header = OverflowAPI.INSTANCE.getConfigManager().getConfig(WebConfig.class).getHeader();
        final String body = OverflowAPI.INSTANCE.getConfigManager().getConfig(WebConfig.class).getBody().replace("%check%", check.getCheckName()).replace("%vl%", String.valueOf(check.getAlert().getViolations())).replace("%player%", check.getPlayerData().getPlayer().getName());

        final String link = OverflowAPI.INSTANCE.getConfigManager().getConfig(WebConfig.class).getLink();

        final WebhookClient webhookClient = WebhookClient.withUrl(link);
        final WebhookEmbed embed = new WebhookEmbedBuilder().setColor(getIntFromColor(Color.RED)).setTitle(new WebhookEmbed.EmbedTitle(header, null)).setDescription(body).build();

        OverflowAPI.INSTANCE.getAlertExecutor().execute(() -> {
            webhookClient.send(embed);
            webhookClient.close();
        });
    }

    private int getIntFromColor(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        red = (red << 16) & 0x00FF0000;
        green = (green << 8) & 0x0000FF00;
        blue = blue & 0x000000FF;
        return 0xFF000000 | red | green | blue;
    }
}
