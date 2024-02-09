package me.pandamods.pandalib.client.screen.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pandamods.pandalib.client.screen.GUI;
import me.pandamods.pandalib.client.screen.UIElement;
import me.pandamods.pandalib.client.screen.impl.ElementImpl;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

public abstract class AbstractButton extends UIElement {
	public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

	public AbstractButton(GUI gui, ElementImpl parent, double x, double y, double width, double height) {
		super(gui, parent);
		setPosition(x, y);
		setSize(width, height);
	}

	@Override
	public void initElement() {
	}

	@Override
	public void renderElement(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick) {
		Minecraft minecraft = Minecraft.getInstance();
		guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1);
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		guiGraphics.blitNineSliced(WIDGETS_LOCATION, (int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight(),
				20, 4, 200, 20, 0, getTextureY());
		guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		int i = this.isActive() ? 0xFFFFFF : 0xA0A0A0;
		this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(1 * 255.0f) << 24);
	}

	private int getTextureY() {
		int i = 1;
		if (!this.isActive()) {
			i = 0;
		} else if (this.isHoveredOrFocused()) {
			i = 2;
		}
		return 46 + i * 20;
	}

	public void renderString(GuiGraphics guiGraphics, Font font, int color) {
		this.renderScrollingString(guiGraphics, font, 2, color);
	}

	protected void renderScrollingString(GuiGraphics guiGraphics, Font font, int width, int color) {
		AbstractButton.renderScrollingString(guiGraphics, font, getMessage(), (int) getMinX(), (int) getMinY(), (int) (getMaxX() - width), (int) getMaxY(), color);
	}

	protected static void renderScrollingString(GuiGraphics guiGraphics, Font font, Component text, int minX, int minY, int maxX, int maxY, int color) {
		int i = font.width(text);
		int j = (minY + maxY - font.lineHeight) / 2 + 1;
		int k = maxX - minX;
		if (i > k) {
			int l = i - k;
			double d = (double) Util.getMillis() / 1000.0;
			double e = Math.max((double)l * 0.5, 3.0);
			double f = Math.sin(1.5707963267948966 * Math.cos(Math.PI * 2 * d / e)) / 2.0 + 0.5;
			double g = Mth.lerp(f, 0.0, (double)l);
			guiGraphics.enableScissor(minX, minY, maxX, maxY);
			guiGraphics.drawString(font, text, minX - (int)g, j, color);
			guiGraphics.disableScissor();
		} else {
			guiGraphics.drawCenteredString(font, text, (minX + maxX) / 2, j, color);
		}
	}

	public abstract void onClick(double mouseX, double mouseY);
	public abstract Component getMessage();

	@Override
	public boolean onMouseRelease(double mouseX, double mouseY, int button) {
		if (button == 0) {
			this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
			onClick(mouseX, mouseY);
			return true;
		}
		return false;
	}
}
