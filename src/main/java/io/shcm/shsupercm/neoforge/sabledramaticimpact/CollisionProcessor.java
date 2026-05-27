package io.shcm.shsupercm.neoforge.sabledramaticimpact;

import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.server.level.ServerLevel;
import org.joml.Vector3d;

import java.util.LinkedHashMap;

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
                return this.position.div(Config.avoidDuplicatesRange.getAsDouble(), new Vector3d()).round().mul(Config.avoidDuplicatesRange.getAsDouble());

            return this;
        }

        public void process() {
            CollisionEffects.playAll(this);
        }
    }
}
