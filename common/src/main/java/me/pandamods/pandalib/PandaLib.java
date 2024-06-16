/*
 * Copyright (C) 2024 Oliver Froberg (The Panda Oliver)
 *
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 * You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.pandamods.pandalib;

import com.mojang.logging.LogUtils;
import me.pandamods.pandalib.core.event.EventHandler;
import me.pandamods.pandalib.core.network.ConfigNetworking;
import me.pandamods.test.PandaLibTest;
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
}
