package me.pandamods.pandalib;

import com.mojang.logging.LogUtils;
import me.pandamods.pandalib.core.event.EventHandler;
import me.pandamods.pandalib.core.network.ConfigNetworking;
import me.pandamods.test.PandaLibTest;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class PandaLib {
    public static final String MOD_ID = "pandalib";
	public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
		ConfigNetworking.registerPackets();
		EventHandler.Register();

		if (PandaLibTest.shouldInit()) {
			PandaLibTest.init();
		}
    }

	public static ResourceLocation ID(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
