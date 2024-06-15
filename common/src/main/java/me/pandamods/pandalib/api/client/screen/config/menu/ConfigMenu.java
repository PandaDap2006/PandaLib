package me.pandamods.pandalib.api.client.screen.config.menu;

import me.pandamods.pandalib.api.client.screen.PLScreen;
import me.pandamods.pandalib.api.client.screen.elements.UIElementHolder;
import me.pandamods.pandalib.api.client.screen.config.category.AbstractConfigCategory;
import me.pandamods.pandalib.api.client.screen.config.category.ConfigCategory;
import me.pandamods.pandalib.api.client.screen.elements.widgets.buttons.AbstractPLButton;
import me.pandamods.pandalib.api.client.screen.elements.widgets.buttons.PLButton;
import me.pandamods.pandalib.api.client.screen.widget.list.QuickListWidget;
import me.pandamods.pandalib.api.config.ConfigData;
import me.pandamods.pandalib.api.config.PandaLibConfig;
import me.pandamods.pandalib.api.config.holders.ConfigHolder;
import me.pandamods.pandalib.api.utils.PLCommonComponents;
import me.pandamods.pandalib.api.client.screen.layouts.PLGridLayout;
import me.pandamods.pandalib.api.utils.screen.PLGuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import java.awt.*;

public class ConfigMenu<T extends ConfigData> extends PLScreen {
	private final Screen parent;
	private final CategoryList categoryList = new CategoryList();
	private final CategoryAddress addressBar = new CategoryAddress();
	private final ConfigHolder<T> configHolder;
	private AbstractConfigCategory category;
	private final AbstractConfigCategory rootCategory;
	
	public ConfigMenu(Screen parent, Class<T> config, AbstractConfigCategory category) {
		this(parent, PandaLibConfig.getConfig(config), category);
	}

	protected ConfigMenu(Screen parent, ConfigHolder<T> configHolder, AbstractConfigCategory category) {
		this.parent = parent;
		this.configHolder = configHolder;
		this.category = this.rootCategory = category;

		this.load();
	}

	@Override
	public Component getTitle() {
		return getCategory().getName();
	}

	@Override
	public void init() {
		this.addressBar.setSize(this.getWidth(), 20);
		this.addElement(this.addressBar);

		this.categoryList.setPosition(0, this.addressBar.getHeight());
		this.categoryList.setSize(100, this.getHeight() - this.categoryList.getY());
		this.addElement(this.categoryList);

		this.category.setPosition(this.categoryList.getWidth(), this.addressBar.getHeight());
		this.category.setSize(this.getWidth() - this.category.getX(), this.getHeight() - this.category.getY());
		this.addElement(this.category);

		PLGridLayout actionGrid = new PLGridLayout();
		actionGrid.spacing(4).defaultCellSetting();
		PLGridLayout.ColumnHelper actionHelper = actionGrid.createColumnHelper(1);

		actionHelper.addChild(PLButton.builder(PLCommonComponents.SAVE, button -> this.save()).width(50).build());
		actionHelper.addChild(PLButton.builder(PLCommonComponents.RESET, button -> this.reset()).width(50).build());
		actionHelper.addChild(PLButton.builder(PLCommonComponents.CLOSE, button -> this.close()).width(50).build());

		actionGrid.quickArrange(this::addElement, this.category.getX(), this.category.maxY() - 30, this.category.getWidth(), 30,
				0.5f, 0.5f);
	}

	@Override
	public void render(PLGuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	public void save() {
		this.rootCategory.save();
		this.configHolder.save();
		this.close();
	}

	public void load() {
		this.rootCategory.load();
	}

	public void reset() {
		this.rootCategory.reset();
	}

	public void setCategory(AbstractConfigCategory category) {
		this.category = category;
		this.rebuildWidgets();
	}

	public AbstractConfigCategory getCategory() {
		return category;
	}

	@Override
	public void close() {
		this.getMinecraft().setScreen(parent);
	}

	public class CategoryList extends UIElementHolder {
		@Override
		public void init() {
			PLGridLayout categoryGrid = new PLGridLayout().spacing(2);
			PLGridLayout.RowHelper categoryHelper = categoryGrid.createRowHelper(1);
			for (AbstractConfigCategory category : ConfigMenu.this.getCategory().getCategories()) {
				categoryHelper.addChild(PLButton.builder(category.getName(), button -> setCategory(category)).width(90).build());
			}
			categoryGrid.quickArrange(this::addElement, getX(), getY() + 5, this.getWidth(), this.getHeight() - 55, 0.5f, 0);
			super.init();
		}

		@Override
		public void render(PLGuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
			guiGraphics.renderSeparatorLine(this.maxX(), this.getRelativeY(), this.height, true);
			super.render(guiGraphics, mouseX, mouseY, partialTick);
		}
	}

	public class CategoryAddress extends UIElementHolder {
		@Override
		public void init() {
			PLGridLayout grid = new PLGridLayout().spacing(2);
			grid.defaultCellSetting().alignVerticallyMiddle();
			PLGridLayout.ColumnHelper helper = grid.createColumnHelper(1);

			AbstractConfigCategory previousCategory = ConfigMenu.this.getCategory().getParentCategory();
			PLButton backButton = PLButton.builder(PLCommonComponents.BACK, button -> setCategory(previousCategory)).build();
			backButton.setSize(50, this.getHeight() - 4);
			backButton.setActive(previousCategory != null);
			helper.addChild(backButton);
			helper.addChild(SpacerElement.width(4));

			addCategoryDist(helper, ConfigMenu.this.category);

			grid.quickArrange(this::addElement, getX() + 4, getY(), this.getWidth() - 10, this.getHeight(), 0f, 0.5f);
			super.init();
		}

		private void addCategoryDist(PLGridLayout.ColumnHelper helper, AbstractConfigCategory abstractCategory) {
			AbstractConfigCategory parentCategory = abstractCategory.getParentCategory();
			if (parentCategory != null)
				addCategoryDist(helper, parentCategory);

			helper.addChild(new CategoryButton(abstractCategory));
			if (abstractCategory instanceof ConfigCategory category && !category.getCategories().isEmpty())
				helper.addChild(new CategoryArrowButton(category));
		}

		@Override
		public void render(PLGuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
			guiGraphics.renderSeparatorLine(getRelativeX(), maxY(), getWidth(), false);
			super.render(guiGraphics, mouseX, mouseY, partialTick);
		}

		private class CategoryButton extends AbstractPLButton {
			private final Font font;
			private final AbstractConfigCategory category;

			public CategoryButton(AbstractConfigCategory category) {
				super(category.getName());
				this.category = category;
				this.font = Minecraft.getInstance().font;
				this.setSize(font.width(getMessage()), 20);
				this.width = font.width(getMessage());
			}

			@Override
			public void render(PLGuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
				this.checkHoverState(mouseX, mouseY);
				int y = this.getY() + (this.height - this.font.lineHeight) / 2;
				guiGraphics.drawString(this.font, getMessage(), this.getX(), y, Color.white.getRGB());

				y += this.font.lineHeight;
				if (this.isHoveredOrFocused())
					guiGraphics.fill(this.getX(), y, this.getX() + this.width, y + 1, Color.white.getRGB());
			}

			@Override
			public void onPress() {
				ConfigMenu.this.setCategory(category);
			}
		}

		private class CategoryArrowButton extends UIElementHolder {
			private final Font font;
			private final QuickListWidget categoryList;

			public CategoryArrowButton(ConfigCategory category) {
				this.font = Minecraft.getInstance().font;
				this.width = font.width(">");
				this.height = 20;

				this.categoryList = QuickListWidget.builder(1)
						.addElements(category.getCategories().stream().map(child ->
								PLButton.builder(child.getName(), button -> ConfigMenu.this.setCategory(child))
										.size(150, font.lineHeight + 8)
										.build()
						).toList()).build();
			}

			@Override
			public void init() {
				this.addElement(this.categoryList);
				this.categoryList.setVisible(false);
				this.categoryList.setPosition(this.getX(), this.getY() + this.getHeight() + 2);
			}

			@Override
			public void render(PLGuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
				this.checkHoverState(mouseX, mouseY);
				int y = this.getRelativeY() + (this.height - this.font.lineHeight) / 2;
				guiGraphics.drawString(this.font, ">", this.getRelativeX(), y, Color.white.getRGB());

				y += this.font.lineHeight;
				if (this.isHoveredOrFocused())
					guiGraphics.fill(this.getRelativeX(), y, this.getRelativeX() + this.width, y + 1, Color.white.getRGB());

				this.categoryList.setVisible(this.isFocused());

				guiGraphics.pose().pushPose();
				guiGraphics.pose().translate(0,0, 999);
				super.render(guiGraphics, mouseX, mouseY, partialTick);
				guiGraphics.pose().popPose();
			}

			protected void onPress() {
				this.playDownSound(Minecraft.getInstance().getSoundManager());
			}

			@Override
			public boolean isFocused() {
				for (LayoutElement element : this.categoryList.elements) {
					if (element instanceof GuiEventListener listener && listener.isFocused())
						return true;
				}
				return super.isFocused() || this.categoryList.isFocused();
			}

			@Override
			public boolean mouseClicked(double mouseX, double mouseY, int button) {
				if (super.mouseClicked(mouseX, mouseY, button))
					return true;

				if (this.isActiveAndVisible()) {
					if (this.isValidClickButton(button)) {
						this.onPress();
						return true;
					}
					return false;
				}
				return false;
			}

			@Override
			public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
				if (super.keyReleased(keyCode, scanCode, modifiers))
					return true;

				if (!this.isActiveAndVisible())
					return false;
				if (CommonInputs.selected(keyCode)) {
					this.onPress();
					return true;
				}
				return false;
			}

			public void playDownSound(SoundManager handler) {
				handler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
			}

			protected boolean isValidClickButton(int button) {
				return button == 0;
			}
		}
	}
}