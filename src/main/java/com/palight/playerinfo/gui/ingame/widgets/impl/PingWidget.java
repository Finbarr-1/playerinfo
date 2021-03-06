package com.palight.playerinfo.gui.ingame.widgets.impl;

import com.palight.playerinfo.PlayerInfo;
import com.palight.playerinfo.gui.ingame.widgets.GuiIngameWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class PingWidget extends GuiIngameWidget {

    public PingWidget() {
        super(-1, -1, 16, 11);
    }

    @Override
    public void render(Minecraft mc) {
        super.render(mc);

        NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().getSession().getProfile().getId());

        long ping = networkPlayerInfo == null ? 0 : networkPlayerInfo.getResponseTime();

        String displayString = ping + " ms";

        this.width = (int) (PlayerInfo.instance.fontRendererObj.getWidth(displayString) + 4);
        this.height = (int) PlayerInfo.instance.fontRendererObj.getHeight(displayString);

        drawTextVerticallyCentered(displayString, getPosition().getX() + 2, getPosition().getY() + this.height / 2 + 1);
    }
}