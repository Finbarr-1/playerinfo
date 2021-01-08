package com.palight.playerinfo.gui.ingame.widgets.impl;

import com.palight.playerinfo.PlayerInfo;
import com.palight.playerinfo.events.HypixelEvent;
import com.palight.playerinfo.gui.ingame.widgets.GuiIngameWidget;
import com.palight.playerinfo.options.ModConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HypixelEventWidget extends GuiIngameWidget {
    private int ticksToExist = 0;
    private String title = "";
    private String subtitle = "";
    private final ScaledResolution res;

    public HypixelEventWidget() {
        super(-1, -1, 64, 32);
        res = new ScaledResolution(Minecraft.getMinecraft());
        this.getPosition().setX((res.getScaledWidth() - this.width) / 2);
        this.getPosition().setY((res.getScaledHeight() - this.height) / 2);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void render(Minecraft mc) {
        if (ticksToExist <= 0) {
            title = "";
            subtitle = "";
        }
        if (this.getState() == WidgetEditingState.EDITING) {
            this.title = "palight";
            this.subtitle = "joined the server.";
        }
        if ((!title.equals("") && !subtitle.equals("")) || this.getState() == WidgetEditingState.EDITING) {
            this.width = (int) Math.max(PlayerInfo.instance.fontRendererObj.getWidth(title) + 8, PlayerInfo.instance.fontRendererObj.getWidth(subtitle) + 8);
//            this.xPosition = (res.getScaledWidth() - this.width) / 2;
            super.render(mc);
            drawText(title, (int) (getPosition().getX() + (this.width - PlayerInfo.instance.fontRendererObj.getWidth(title)) / 2), getPosition().getY() + (PlayerInfo.instance.fontRendererObj.FONT_HEIGHT / 2));
            drawText(subtitle, (int) (getPosition().getX() + (this.width - PlayerInfo.instance.fontRendererObj.getWidth(subtitle)) / 2), getPosition().getY() + (this.height + PlayerInfo.instance.fontRendererObj.FONT_HEIGHT) / 2);
        }
    }

    @SubscribeEvent
    public void onFriendJoin(HypixelEvent.FriendEvent event) {
        if (!ModConfiguration.friendAlertsEnabled) return;
        this.title = event.getUsername();
        this.subtitle = HypixelEvent.FriendEvent.FriendEventType.getStringToUse(event.getType()) + " the server.";
        this.ticksToExist = 4 * 20; // seconds * ticks per second
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (ticksToExist > 0) ticksToExist--;
    }
}
