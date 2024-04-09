package me.pandamods.pandalib.api.client.screen.config;

import me.pandamods.pandalib.api.client.screen.config.option.ConfigOption;
import me.pandamods.pandalib.api.config.ConfigData;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ConfigCategory extends AbstractConfigCategory {
	private final Component name;
	private final List<ConfigOption<?>> options;

	public ConfigCategory(Component name, List<ConfigOption<?>> options) {
		this.name = name;
		this.options = options;
	}

	@Override
	public Component getName() {
		return name;
	}

	@Override
	protected void init() {
		int y = 0;
		for (ConfigOption<?> option : this.options) {
			option.setY(y);
			this.addElement(option);
			y += option.getHeight();
		}
		super.init();
	}

	public static Builder builder(Component name) {
		return new Builder(name);
	}

	public static class Builder {
		private final Component name;
		private final List<ConfigOption<?>> options = new ArrayList<>();

		public Builder(Component name) {
			this.name = name;
		}

		public void addOption(ConfigOption<?> configOption) {
			this.options.add(configOption);
		}

		public ConfigCategory build() {
			return new ConfigCategory(this.name, this.options);
		}
	}
}