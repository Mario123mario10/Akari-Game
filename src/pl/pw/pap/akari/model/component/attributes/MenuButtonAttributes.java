package pl.pw.pap.akari.model.component.attributes;

import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.resources.RESOURCES;

public class MenuButtonAttributes {
    private Bounds bounds;
    private RESOURCES backgroundPath;

    public MenuButtonAttributes(Bounds bounds, RESOURCES backgroundPath) {
        this.bounds = bounds;
        this.backgroundPath = backgroundPath;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public RESOURCES getBackgroundPath() {
        return backgroundPath;
    }
}
