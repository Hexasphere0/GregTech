package gregtech.common.terminal.app.prospector;

import javax.annotation.Nonnull;

public enum ProspectorMode {
    ORE("ore_prospector", "metaitem.prospector.mode.ores"),
    FLUID("fluid_prospector", "metaitem.prospector.mode.fluid");

    public static final ProspectorMode[] VALUES = values();

    public final String terminalName;
    public final String unlocalizedName;

    ProspectorMode(@Nonnull String terminalName, @Nonnull String unlocalizedName) {
        this.terminalName = terminalName;
        this.unlocalizedName = unlocalizedName;
    }

    @Nonnull
    public ProspectorMode next() {
        int next = ordinal() + 1;
        if (next >= VALUES.length) {
            return ProspectorMode.VALUES[0];
        }
        return VALUES[next];
    }
}
