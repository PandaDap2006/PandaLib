package me.pandamods.pandalib.client.model;

import me.pandamods.pandalib.resources.MeshData;
import org.joml.Vector3f;

public record CompiledVertex(
		Vector3f position,
		MeshData.Object.Vertex data) {
}
