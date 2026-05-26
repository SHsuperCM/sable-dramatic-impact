package io.shcm.shsupercm.neoforge.sablefunnyimpact;

import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = "sablefunnyimpact", dist = Dist.CLIENT)
public class SableFunnyImpactClient {

    public SableFunnyImpactClient(ModContainer container) {
        try {
            container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new BaseConfigScreen(parent, mod.getModId()));
        } catch (Exception e) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}
