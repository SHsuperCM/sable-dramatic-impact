package io.shcm.shsupercm.neoforge.sablefunnyimpact;

import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import org.joml.Vector3d;

public class CollisionProcessor {
    public void process(ServerLevel level, Vector3d position, double force, ServerSubLevel subLevelA, ServerSubLevel subLevelB) {
        if (force < 200)
            return;

        Vector3d speedA = new Vector3d(0), speedB = new Vector3d(0);

        if (subLevelA != null)
            subLevelA.logicalPose().position().sub(subLevelA.lastPose().position(), speedA);
        if (subLevelB != null)
            subLevelB.logicalPose().position().sub(subLevelB.lastPose().position(), speedB);

        double bpsDiff = speedB.sub(speedA, new Vector3d()).length() * 20;

        if (bpsDiff > 10) {
            level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, position.x, position.y, position.z, 1, 0, 0, 0, 1);
        }
    }
}
