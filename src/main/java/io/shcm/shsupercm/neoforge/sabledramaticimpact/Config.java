package io.shcm.shsupercm.neoforge.sabledramaticimpact;

import net.minecraft.Util;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static ModConfigSpec.BooleanValue enableEffects;

    public static ModConfigSpec.BooleanValue avoidDuplicates;
    public static ModConfigSpec.DoubleValue avoidDuplicatesRange;
    public static ModConfigSpec.DoubleValue minBPS;
    public static ModConfigSpec.DoubleValue minForce;


    static final ModConfigSpec SPEC = Util.make(new ModConfigSpec.Builder(), builder -> {
        enableEffects = builder.define("enableEffects", true);

        builder.push("trigger"); {
            avoidDuplicates = builder.define("avoidDuplicates", true);
            avoidDuplicatesRange = builder.defineInRange("avoidDuplicatesRange", 5d, 0, Double.MAX_VALUE);
            minBPS = builder.defineInRange("minBPS", 6d, 0, Double.MAX_VALUE);
            minForce = builder.defineInRange("minForce", 400d, 0, Double.MAX_VALUE);
        } builder.pop();

        builder.push("effects"); {
            for (CollisionEffects effect : CollisionEffects.values()) {
                builder.push(effect.id);
                effect.registerConfig(builder);
                builder.pop();
            }
        } builder.pop();

    }).build();
}
