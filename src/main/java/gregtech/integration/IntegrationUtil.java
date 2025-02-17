package gregtech.integration;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegrationUtil {

    /** Should only be called after {@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent} */
    public static void throwIncompatibilityIfLoaded(String modID, String... customMessages) {
        if (Loader.isModLoaded(modID)) {
            String modName = TextFormatting.BOLD + modID + TextFormatting.RESET;
            List<String> messages = new ArrayList<>();
            messages.add(modName + " mod detected, this mod is incompatible with Gregification.");
            messages.addAll(Arrays.asList(customMessages));
            throwIncompatibility(messages);
        }
    }

    /** Should only be called after {@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent} */
    public static void throwIncompatibility(List<String> messages) {
        if (FMLLaunchHandler.side() == Side.SERVER) {
            throw new RuntimeException(String.join(",", messages));
        } else {
            throwClientIncompatibility(messages);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void throwClientIncompatibility(List<String> messages) {
        throw new ModIncompatibilityException(messages);
    }

    @SideOnly(Side.CLIENT)
    private static class ModIncompatibilityException extends CustomModLoadingErrorDisplayException {

        private static final long serialVersionUID = 1L;

        private final List<String> messages;

        public ModIncompatibilityException(List<String> messages) {
            this.messages = messages;
        }

        @Override
        public void initGui(GuiErrorScreen guiErrorScreen, FontRenderer fontRenderer) {
        }

        @Override
        public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseX, int mouseY, float time) {
            int x = errorScreen.width / 2;
            int y = 75;
            for (String message : messages) {
                errorScreen.drawCenteredString(fontRenderer, message, x, y, 0xFFFFFF);
                y += 15;
            }
        }
    }
}
