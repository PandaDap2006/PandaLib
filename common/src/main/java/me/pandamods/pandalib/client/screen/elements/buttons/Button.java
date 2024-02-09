package me.pandamods.pandalib.client.screen.elements.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pandamods.pandalib.client.screen.GUI;
import me.pandamods.pandalib.client.screen.UIElement;
import me.pandamods.pandalib.client.screen.impl.ElementImpl;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.awt.event.KeyEvent;
import java.util.concurrent.Callable;

public class Button extends AbstractButton {
	private final Component message;
	private final Runnable onClick;

	public Button(GUI gui, ElementImpl parent, double x, double y, double width, double height, Component message, Runnable onClick) {
		super(gui, parent, x, y, width, height);
		this.message = message;
		this.onClick = onClick;
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		onClick.run();
	}

	@Override
	public Component getMessage() {
		return message;
	}
}
