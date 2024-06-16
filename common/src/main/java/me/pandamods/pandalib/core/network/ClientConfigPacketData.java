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

package me.pandamods.pandalib.core.network;

import io.netty.buffer.ByteBuf;
import me.pandamods.pandalib.PandaLib;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ClientConfigPacketData(String resourceLocation, String configJson) implements CustomPacketPayload {
	public static final Type<ClientConfigPacketData> TYPE =
			new Type<>(ResourceLocation.fromNamespaceAndPath(PandaLib.MOD_ID, "client_config_sync"));

	public static final StreamCodec<ByteBuf, ClientConfigPacketData> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			ClientConfigPacketData::resourceLocation,
			ByteBufCodecs.STRING_UTF8,
			ClientConfigPacketData::configJson,
			ClientConfigPacketData::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
