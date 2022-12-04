package pl.pw.pap.akari.view.component.buttons.game;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.attributes.BoardButtonAttributes;
import pl.pw.pap.akari.model.component.data.Bounds;

import javax.swing.*;

public class WallButton extends JButton {
    private BoardButtonAttributes attributes;
    private EventHandler eventHandler;

    public WallButton(EventHandler eventHandler, BoardButtonAttributes attributes) {
        this.eventHandler = eventHandler;
        this.attributes = attributes;
        Bounds bounds = attributes.getBounds();
        this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        setWallText(attributes.getCountBulbAround());
        this.setBackground(attributes.getBackgroundColor());
    }


    public BoardButtonAttributes getAttributes() {
        return attributes;
    }

    public void updateButton(BoardButtonAttributes attributes) {
        this.attributes = attributes;
        this.setBackground(attributes.getBackgroundColor());
    }

    private void setWallText(int countBulbAround) {
        this.setText(String.valueOf(countBulbAround));
    }
}
