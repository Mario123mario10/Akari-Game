package pl.pw.pap.akari.view.component.buttons.game;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.component.event.CommonEvent;

import javax.swing.*;
import java.awt.*;

public class TimerButton extends JButton {
    private EventHandler eventHandler;
    private CommonEvent event;
    // 0.1 sec
    private int time = 1;
    private Timer timer;

    public TimerButton(EventHandler eventHandler, Bounds bounds, CommonEvent event) {
        this.eventHandler = eventHandler;
        this.event = event;
        this.timer = generateTimer();

        setText(0);

        this.addActionListener(actionEvent -> this.eventHandler.handleEvent(this.event));
        this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }


    public void stop() {
        timer.stop();
        this.setBackground(Color.GREEN);
    }

    private void setText(int time) {
        this.setText("<html><center> <small>" +  prepareTime() + " SEC</center></small><br>" + "CHECK" + "</html>");
    }

    private String prepareTime(){
        int second = time/10;
        int tenthSecond = time % 10;

        return second + ":" + tenthSecond;
    }
    private void setBackground() {
        this.setBackground(Color.getHSBColor(217, 30 + calcColorSeed(), 100));
    }

    private float calcColorSeed() {
        return (float) time / 100;
    }

    private Timer generateTimer() {
        Timer timer = new Timer(100, actionEvent -> {
            setText(time);
            setBackground();
            time++;
        });
        timer.start();
        return timer;
    }
}

