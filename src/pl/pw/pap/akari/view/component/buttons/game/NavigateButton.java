package pl.pw.pap.akari.view.component.buttons.game;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.component.event.CommonEvent;

import javax.swing.*;

public class NavigateButton extends JButton {
    private EventHandler eventHandler;
    private CommonEvent event;

    public NavigateButton(EventHandler eventHandler, Bounds bounds, CommonEvent event, String displayedText) {
        this.eventHandler = eventHandler;
        this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        this.event = event;
        this.addActionListener(actionEvent -> this.eventHandler.handleEvent(this.event));
        this.setText(displayedText);
    }
}
