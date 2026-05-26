package io.shcm.shsupercm.neoforge.sabledramaticimpact;

import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.joml.Vector3d;

import java.util.LinkedHashMap;

import static java.lang.Math.clamp;

public class CollisionProcessor {
    public LinkedHashMap<Object, Collision> collisions = new LinkedHashMap<>();

    public void addCollision(ServerLevel level, Vector3d position, double force, ServerSubLevel subLevelA, ServerSubLevel subLevelB) {
        if (!Config.enableEffects.getAsBoolean())
            return;

        if (force < Config.minForce.getAsDouble())
            return;

        Vector3d velocityA = new Vector3d(0), velocityB = new Vector3d(0);

        if (subLevelA != null)
            subLevelA.logicalPose().position().sub(subLevelA.lastPose().position(), velocityA);
        if (subLevelB != null)
            subLevelB.logicalPose().position().sub(subLevelB.lastPose().position(), velocityB);

        double speed = velocityB.sub(velocityA, new Vector3d()).length() * 20;

        if (speed < Config.minBPS.getAsDouble())
            return;

        Collision collision = new Collision(level, position, force, speed, velocityA, velocityB);
        Collision recentCollision = this.collisions.get(collision.key());
        if (recentCollision == null || collision.impactForce > recentCollision.impactForce) {
            collision.process();
            this.collisions.put(collision.key(), collision);
        }
    }

    public void tick(int ticks) {
        if (ticks % 20 == 0)
            collisions.clear();
    }

    public record Collision(ServerLevel level, Vector3d position, double impactForce, double speed, Vector3d velocitySubLevelA, Vector3d velocitySubLevelB) {
        public Object key() {
            if (Config.avoidDuplicates.getAsBoolean())
                return this.position.mul(0.1, new Vector3d()).round();

            return this;
        }

        public void process() {
            level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, position.x, position.y, position.z, Config.effectsExplosionParticles.getAsInt(), 1, 1, 1, 1);
            level.sendParticles(ParticleTypes.WHITE_SMOKE, position.x, position.y, position.z, Config.effectsSmokeParticles.getAsInt(), 1, 1, 1, 0.0001);
            if (Config.effectsExplosionSound.getAsBoolean())
                level.playSound(null, position.x, position.y, position.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, clamp(0.5f * ((float)impactForce / (500 * (float)Config.minForce.getAsDouble())), 0.1f, 1f), 1f);
        }
    }
}
