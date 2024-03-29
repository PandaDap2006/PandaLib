package me.pandamods.pandalib.core.network;

import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PacketHandlerClient {
	public static void init() {
		NetworkManager.registerReceiver(NetworkManager.serverToClient(), PacketHandler.CONFIG_PACKET, ConfigPacketClient::configReceiver);
	}
}
