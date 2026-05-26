package io.shcm.shsupercm.neoforge.sablefunnyimpact.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.ryanhcode.sable.physics.impl.rapier.RapierPhysicsPipeline;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import io.shcm.shsupercm.neoforge.sablefunnyimpact.Config;
import io.shcm.shsupercm.neoforge.sablefunnyimpact.SableFunnyImpact;
import net.minecraft.server.level.ServerLevel;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RapierPhysicsPipeline.class)
public class RapierPhysicsPipelineMixin {
    @Shadow @Final private ServerLevel level;

    @Inject(method = "processCollisionEffects", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(DD)D"))
    private void sablefunnyimpact$processCollisionEffects(CallbackInfo ci, @Local(name = "localPointA") Vector3d localPointA, @Local(name = "forceAmount") double forceAmount, @Local(name = "subLevelA") ServerSubLevel subLevelA, @Local(name = "subLevelB") ServerSubLevel subLevelB) {
        if (!Config.enableEffects.getAsBoolean())
            return;

        Vector3d globalPointA = new Vector3d(localPointA);
        if (subLevelA != null)
            subLevelA.logicalPose().orientation().transform(localPointA, globalPointA).add(subLevelA.logicalPose().position());

        SableFunnyImpact.collisions.addCollision(this.level, globalPointA, forceAmount, subLevelA, subLevelB);
    }
}
