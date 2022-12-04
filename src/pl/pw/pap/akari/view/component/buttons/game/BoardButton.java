package pl.pw.pap.akari.view.component.buttons.game;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.attributes.BoardButtonAttributes;
import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.component.event.BoardButtonEvent;

import javax.swing.*;

public class BoardButton extends JButton {
    private BoardButtonAttributes attributes;
    private EventHandler eventHandler;
    private BoardButtonEvent event;

    public BoardButton(EventHandler eventHandler, BoardButtonAttributes attributes, BoardButtonEvent event) {
        this.eventHandler = eventHandler;
        this.attributes = attributes;
        this.event = event;

        Bounds bounds = attributes.getBounds();

        this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        updateButton(attributes);
        this.addActionListener(actionEvent -> this.eventHandler.handleEvent(this.event));
    }

    public BoardButtonAttributes getAttributes() {
        return attributes;
    }

    public void updateButton(BoardButtonAttributes attributes) {
        this.attributes = attributes;
        if (attributes.getBackground_path() != null) {
            this.setBackground(null);
            this.setIcon(new ImageIcon(attributes.getBackground_path().getValue()));
        } else if (attributes.getBackgroundColor() != null) {
            this.setIcon(null);
            this.setBackground(attributes.getBackgroundColor());
        }
    }
}
