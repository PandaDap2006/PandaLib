package me.pandamods.pandalib.core.extensions;

import me.pandamods.pandalib.api.config.ConfigData;
import me.pandamods.pandalib.api.config.holders.ConfigHolder;
import net.minecraft.resources.ResourceLocation;

public interface PlayerExtension {
	default void pandaLib$setConfig(ResourceLocation resourceLocation, byte[] configBytes) {}
	default <T extends ConfigData> T pandaLib$getConfig(ConfigHolder<T> holder) {
		return null;
	}
}
