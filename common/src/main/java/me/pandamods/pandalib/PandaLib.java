package me.pandamods.pandalib;

import com.mojang.logging.LogUtils;
import me.pandamods.pandalib.event.EventHandler;
import me.pandamods.pandalib.network.ConfigNetworking;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class PandaLib {
    public static final String MOD_ID = "pandalib";
	public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
		ConfigNetworking.registerPackets();
		EventHandler.Register();
    }

	public static ResourceLocation ID(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
