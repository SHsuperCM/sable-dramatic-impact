package io.shcm.shsupercm.neoforge.sabledramaticimpact;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

@Mod(value = "sabledramaticimpact")
@EventBusSubscriber
public class SableDramaticImpact {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static CollisionProcessor collisions = new CollisionProcessor();

    public SableDramaticImpact(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public static void tick(ServerTickEvent.Post event) {
        if (!Config.enableEffects.getAsBoolean())
            return;

        collisions.tick(event.getServer().getTickCount());
    }
}
