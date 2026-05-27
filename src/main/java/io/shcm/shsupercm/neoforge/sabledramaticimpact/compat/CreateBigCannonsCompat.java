package io.shcm.shsupercm.neoforge.sabledramaticimpact.compat;

import net.minecraft.server.level.ServerLevel;
import rbasamoyai.createbigcannons.effects.particles.plumes.BigCannonPlumeParticleData;

public class CreateBigCannonsCompat {
    public static CreateBigCannonsCompat INSTANCE = new CreateBigCannonsCompat() { };

    public void spawnParticle(ServerLevel level, double x, double y, double z) {

    }

    public static class Impl extends CreateBigCannonsCompat {
        @Override
        public void spawnParticle(ServerLevel level, double x, double y, double z) {
            level.sendParticles(new BigCannonPlumeParticleData(5, 5, 1), x, y + 0.5, z, 1, 1, 1, 1, 0.00001);
        }
    }
}
