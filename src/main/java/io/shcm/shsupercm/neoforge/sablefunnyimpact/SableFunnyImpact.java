package io.shcm.shsupercm.neoforge.sablefunnyimpact;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(value = "sablefunnyimpact")
public class SableFunnyImpact {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static CollisionProcessor collisions = new CollisionProcessor();

    public SableFunnyImpact(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
