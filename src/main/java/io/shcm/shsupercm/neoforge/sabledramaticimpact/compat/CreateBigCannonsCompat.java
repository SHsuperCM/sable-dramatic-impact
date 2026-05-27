package io.shcm.shsupercm.neoforge.sabledramaticimpact.compat;

import io.shcm.shsupercm.neoforge.sabledramaticimpact.CollisionEffects;
import net.minecraft.server.level.ServerLevel;
import rbasamoyai.createbigcannons.effects.particles.plumes.BigCannonPlumeParticleData;

public class CreateBigCannonsCompat {
    public static CreateBigCannonsCompat INSTANCE = new CreateBigCannonsCompat() { };

    public void spawnParticle(ServerLevel level, double x, double y, double z, CollisionEffects effect) {

    }

    public static class Impl extends CreateBigCannonsCompat {
        @Override
        public void spawnParticle(ServerLevel level, double x, double y, double z, CollisionEffects effect) {
            level.sendParticles(new BigCannonPlumeParticleData(5, 5, 1), x, y, z, effect.amount.getAsInt(), effect.range.getAsDouble(), effect.range.getAsDouble(), effect.range.getAsDouble(), effect.speed.getAsDouble());
        }
    }
}
