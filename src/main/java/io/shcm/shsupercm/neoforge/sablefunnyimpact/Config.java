package io.shcm.shsupercm.neoforge.sablefunnyimpact;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue enableEffects = BUILDER.define("enableEffects", true);

    public static final ModConfigSpec.BooleanValue avoidDuplicates = BUILDER.define("avoidDublicates", true);

    public static final ModConfigSpec.DoubleValue minBPS = BUILDER.defineInRange("minBPS", 10d, 0, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue minForce = BUILDER.defineInRange("minForce", 200d, 0, Double.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
