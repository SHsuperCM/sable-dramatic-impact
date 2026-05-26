package io.shcm.shsupercm.neoforge.sablefunnyimpact;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = "sablefunnyimpact", dist = Dist.CLIENT)
public class SableFunnyImpactClient {

    public SableFunnyImpactClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
