package com.palight.playerinfo.mixin.render;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemRenderer.class)
public interface IMixinItemRenderer {
    @Invoker void callRotateArroundXAndY(float angle, float angleY);
    @Invoker void callSetLightMapFromPlayer(AbstractClientPlayer clientPlayer);
    @Invoker void callRotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks);
    @Invoker void callRenderItemMap(AbstractClientPlayer clientPlayer, float pitch, float equipmentProgress, float swingProgress);
    @Invoker void callPerformDrinking(AbstractClientPlayer clientPlayer, float partialTicks);
    @Invoker void callDoBlockTransformations();
    @Invoker void callDoBowTransformations(float partialTicks, AbstractClientPlayer clientPlayer);
    @Invoker void callDoItemUsedTransformations(float swingProgress);
    @Invoker void callRenderPlayerArm(AbstractClientPlayer clientPlayer, float equipProgress, float swingProgress);
}
