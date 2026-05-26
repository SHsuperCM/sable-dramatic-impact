package io.shcm.shsupercm.neoforge.sablefunnyimpact;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue enableEffects = BUILDER.define("enableEffects", true);

    // Trigger
    public static final ModConfigSpec.BooleanValue avoidDuplicates = BUILDER.push("trigger").define("avoidDuplicates", true);

    public static final ModConfigSpec.DoubleValue minBPS = BUILDER.defineInRange("minBPS", 6d, 0, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue minForce = BUILDER.defineInRange("minForce", 400d, 0, Double.MAX_VALUE);

    // Effects
    public static final ModConfigSpec.IntValue effectsExplosionParticles = BUILDER.pop().push("effects").defineInRange("effectsExplosionParticles", 3, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue effectsSmokeParticles = BUILDER.defineInRange("effectsSmokeParticles", 3, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.BooleanValue effectsExplosionSound = BUILDER.define("effectsExplosionSound", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}
