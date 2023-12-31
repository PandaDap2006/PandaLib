package me.pandamods.pandalib.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class RenderUtils {
	public static final BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

	public static void renderBlock(PoseStack poseStack, BlockState blockState, BlockPos blockPos,
								   Level level, VertexConsumer vertexConsumer, int lightColor, int overlay) {
		render(poseStack, blockState, blockPos, level, vertexConsumer, lightColor, overlay);
	}

	private static void render(PoseStack poseStack, BlockState blockState, BlockPos blockPos,
								   Level level, VertexConsumer vertexConsumer, int lightColor, int overlay) {
		BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState);
		int color = Minecraft.getInstance().getBlockColors().getColor(blockState, level, blockPos, 0);
		float red = (float)(color >> 16 & 0xFF) / 255.0f;
		float green = (float)(color >> 8 & 0xFF) / 255.0f;
		float blue = (float)(color & 0xFF) / 255.0f;

		Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(),
				vertexConsumer, blockState, bakedModel, red, green, blue, lightColor, overlay);
	}

	public static float getDeltaSeconds() {
		return Minecraft.getInstance().getDeltaFrameTime() / 20;
	}

	public static List<ResourceLocation> getBlockTextures(BlockState blockState) {
		ModelManager manager = Minecraft.getInstance().getModelManager();
		BakedModel model = manager.getBlockModelShaper().getBlockModel(blockState);
		List<BakedQuad> quads = model.getQuads(null, null, RandomSource.create());
		List<ResourceLocation> textures = new ArrayList<>();
		for (BakedQuad quad : quads) {
			if (!textures.contains(quad.getSprite().contents().name())) {
				textures.add(quad.getSprite().contents().name());
			}
		}
		return textures;
	}
}
