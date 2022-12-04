package pl.pw.pap.akari.model.component.attributes;

import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.resources.RESOURCES;

import java.awt.*;

public class BoardButtonAttributes {
    private Bounds bounds;
    private RESOURCES background_path;
    private Color backgroundColor;
    private Integer countBulbAround;


    public BoardButtonAttributes(Bounds bounds, RESOURCES background_path, Color backgroundColor, Integer countBulbAround) {
        this.bounds = bounds;
        this.background_path = background_path;
        this.backgroundColor = backgroundColor;
        this.countBulbAround = countBulbAround;
    }

    public RESOURCES getBackground_path() {
        return background_path;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public Integer getCountBulbAround() {
        return countBulbAround;
    }

    public boolean equals(BoardButtonAttributes attributes) {
        if (backgroundColor != null && attributes.backgroundColor != null) {
            return backgroundColor.equals(attributes.backgroundColor);
        } else if (background_path != null && attributes.background_path != null) {
            return background_path.equals(attributes.background_path);
        }
        return false;
    }
}
