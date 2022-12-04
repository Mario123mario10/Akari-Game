package pl.pw.pap.akari.view.component.buttons.menu;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.attributes.MenuButtonAttributes;
import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.resources.RESOURCES;

import javax.swing.*;

public class MenuButton extends JButton {
    private EventHandler handler;
    private CommonEvent event;

    public MenuButton(EventHandler eventHandler, MenuButtonAttributes attributes, CommonEvent event) {
        this.handler = eventHandler;
        this.event = event;

        Bounds bounds = attributes.getBounds();
        this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());

        RESOURCES backgroundPath = attributes.getBackgroundPath();
        this.setIcon(new ImageIcon(backgroundPath.getValue()));

        this.addActionListener(actionEvent -> this.handler.handleEvent(this.event));
    }
}
