package io.shcm.shsupercm.neoforge.sabledramaticimpact;

import io.shcm.shsupercm.neoforge.sabledramaticimpact.compat.CreateBigCannonsCompat;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.common.ModConfigSpec;

import static java.lang.Math.clamp;

public enum CollisionEffects {
    EXPLOSION_EMITTER("ParticleExplosionEmitter", true, 3, 1, 0.7, new Effect.SimpleParticle(ParticleTypes.EXPLOSION_EMITTER)),
    WHITE_SMOKE("ParticleWhiteSmoke", true, 3, 1, 0.0001, new Effect.SimpleParticle(ParticleTypes.WHITE_SMOKE)),
    CBC_BIG_CANNON_PLUME("ParticleBigCannonPlume", true, 1, 1, 0.00001, (effect, collision) -> {
        CreateBigCannonsCompat.INSTANCE.spawnParticle(collision.level(), collision.position().x(), collision.position().y() + 0.5, collision.position().z(), effect);
    }),
    EXPLOSION_SOUND("SoundExplosion", true, (effect, collision) -> {
        collision.level().playSound(null, collision.position().x, collision.position().y, collision.position().z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, clamp(0.5f * ((float)collision.impactForce() / (500 * (float)Config.minForce.getAsDouble())), 0.1f, 1f), 1f);
    }) {
        @Override
        public void registerExtraConfig(ModConfigSpec.Builder builder) {

        }
    },
    ;

    public final String id;
    public ModConfigSpec.BooleanValue enabled;
    public ModConfigSpec.IntValue amount;
    public ModConfigSpec.DoubleValue range;
    public ModConfigSpec.DoubleValue speed;
    private final Effect effect;

    private final boolean defaultEnabled;
    private final int defaultAmount;
    private final double defaultRange;
    private final double defaultSpeed;

    CollisionEffects(String id, Effect effect) {
        this(id, true, effect);
    }

    CollisionEffects(String id, boolean enabled, Effect effect) {
        this(id, enabled, 1, 1, 1, effect);
    }

    CollisionEffects(String id, boolean enabled, int amount, double range, double speed, Effect effect) {
        this.id = id;
        this.defaultEnabled = enabled;
        this.defaultAmount = amount;
        this.defaultRange = range;
        this.defaultSpeed = speed;
        this.effect = effect;
    }

    public void registerConfig(ModConfigSpec.Builder builder) {
        this.enabled = builder.define("enable" + id + "Effect", defaultEnabled);
        registerExtraConfig(builder);
    }

    public void registerExtraConfig(ModConfigSpec.Builder builder) {
        this.amount = builder.defineInRange("amount" + id + "Effect", defaultAmount, 1, Integer.MAX_VALUE);
        this.range = builder.defineInRange("range" + id + "Effect", defaultRange, 0, Double.MAX_VALUE);
        this.speed = builder.defineInRange("speed" + id + "Effect", defaultSpeed, 0, Double.MAX_VALUE);
    }

    public void play(CollisionProcessor.Collision collision) {
        if (!this.enabled.getAsBoolean())
            return;

        this.effect.play(this, collision);
    }

    public static void playAll(CollisionProcessor.Collision collision) {
        for (CollisionEffects effect : values())
            effect.play(collision);
    }

    private interface Effect {
        void play(CollisionEffects collisionEffects, CollisionProcessor.Collision collision);
        
        record SimpleParticle(ParticleOptions particleOptions) implements Effect {
            @Override
            public void play(CollisionEffects effect, CollisionProcessor.Collision collision) {
                collision.level().sendParticles(this.particleOptions, collision.position().x, collision.position().y, collision.position().z, effect.amount.getAsInt(), effect.range.getAsDouble(), effect.range.getAsDouble(), effect.range.getAsDouble(), effect.speed.getAsDouble());
            }
        }
    }
}
