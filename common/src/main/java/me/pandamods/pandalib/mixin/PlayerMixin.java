package me.pandamods.pandalib.mixin;

import me.pandamods.pandalib.api.config.ConfigData;
import me.pandamods.pandalib.api.config.holders.ConfigHolder;
import me.pandamods.pandalib.core.extensions.PlayerExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerExtension {
	@Unique
	private static final EntityDataAccessor<CompoundTag> PLAYER_CLIENT_CONFIGS =
			SynchedEntityData.defineId(Player.class, EntityDataSerializers.COMPOUND_TAG);

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "defineSynchedData", at = @At("RETURN"))
	public void defineSynchedData(CallbackInfo ci) {
		this.getEntityData().define(PLAYER_CLIENT_CONFIGS, new CompoundTag());
	}

	@Override
	public void pandaLib$setConfig(ResourceLocation resourceLocation, byte[] configBytes) {
		CompoundTag compoundTag = this.getEntityData().get(PLAYER_CLIENT_CONFIGS);
		compoundTag.putByteArray(resourceLocation.toString(), configBytes);
		this.getEntityData().set(PLAYER_CLIENT_CONFIGS, compoundTag);
	}

	@Override
	public <T extends ConfigData> T pandaLib$getConfig(ConfigHolder<T> holder) {
		CompoundTag compoundTag = this.getEntityData().get(PLAYER_CLIENT_CONFIGS);
		if (compoundTag.contains(holder.resourceLocation().toString()))
			return holder.getGson().fromJson(new String(compoundTag.getByteArray(holder.resourceLocation().toString())), holder.getConfigClass());
		return holder.get();
	}
}
