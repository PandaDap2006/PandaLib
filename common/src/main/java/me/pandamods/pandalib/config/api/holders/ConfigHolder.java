package me.pandamods.pandalib.config.api.holders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.architectury.platform.Platform;
import me.pandamods.pandalib.client.screen.api.ConfigScreen;
import me.pandamods.pandalib.config.api.Config;
import me.pandamods.pandalib.config.api.ConfigData;
import me.pandamods.pandalib.utils.ClassUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHolder<T extends ConfigData> {
	public final Logger logger;
	private final Gson gson;

	private final Class<T> configClass;
	private final Config definition;
	private final ResourceLocation resourceLocation;
	private final ResourceLocation configPacketId;
	private final boolean synchronize;
	private T config;

	public ConfigHolder(Class<T> configClass, Config config) {
		this.configClass = configClass;
		this.definition = config;
		this.logger = LoggerFactory.getLogger(config.modId() + " | Config");
		this.gson = getNewDefault().buildGson(new GsonBuilder()).setPrettyPrinting().create();

		this.resourceLocation = new ResourceLocation(config.modId(), config.name());
		this.synchronize = config.synchronize();
		this.configPacketId = new ResourceLocation(config.modId(), "config_packet");

		if (this.load()) {
			save();
		}
	}

	public Gson getGson() {
		return gson;
	}

	public Class<T> getConfigClass() {
		return configClass;
	}

	public Config getDefinition() {
		return definition;
	}

	public Path getConfigPath() {
		Path path = Platform.getConfigFolder();
		if (!definition.parentDirectory().isBlank()) path = path.resolve(definition.parentDirectory());
		return path.resolve(definition.name() + ".json");
	}

	public void save() {
		this.config.onSave(this);
		Path configPath = getConfigPath();
		try {
			Files.createDirectories(configPath.getParent());
			BufferedWriter writer = Files.newBufferedWriter(configPath);
			this.getGson().toJson(config, writer);
			writer.close();
			this.logger.info("successfully saved config '{}'", name());
		} catch (IOException e) {
			this.logger.info("Failed to save config '{}'", name());
			throw new RuntimeException(e);
		}
	}

	public boolean load() {
		Path configPath = getConfigPath();
		if (Files.exists(configPath)) {
			try (BufferedReader reader = Files.newBufferedReader(configPath)) {
				JsonObject jsonObject = this.getGson().fromJson(reader, JsonObject.class);
				this.config = this.getGson().fromJson(jsonObject, configClass);
				this.config.onLoad(this, jsonObject);
			} catch (IOException e) {
				this.logger.error("Failed to load config '{}', using default", name(), e);
				resetToDefault();
				return false;
			}
		} else {
			resetToDefault();
			save();
		}
		this.logger.info("successfully loaded config '{}'", name());
		return true;
	}

	public void resetToDefault() {
		this.config = getNewDefault();
	}

	public T getNewDefault() {
		return ClassUtils.constructUnsafely(configClass);
	}

	public ResourceLocation resourceLocation() {
		return resourceLocation;
	}

	public String name() {
		return getDefinition().name();
	}

	public T get() {
		return config;
	}

	public Screen createScreen(Screen parent) {
		return new ConfigScreen(parent, this, get(), getNewDefault());
	}
}
