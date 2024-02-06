package me.pandamods.pandalib.resources;

import org.joml.Vector3f;

import java.util.Map;

public record ArmatureData(Map<String, Bone> bones) {
	public record Bone(Vector3f position, String parent) {}
}
