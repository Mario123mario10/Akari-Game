package pl.pw.pap.akari.view.component.frames;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.resources.RESOURCES;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


import java.awt.*;

public abstract class AbstractAkariFrame extends JFrame {

    protected EventHandler eventHandler;

    protected abstract void customize(Object frameDataInfo);

    protected abstract Dimension getDimension();

    /**
     * nullable
     */
    protected abstract RESOURCES getBackgroundPath();

    public AbstractAkariFrame(EventHandler eventHandler, Object frameDataInfo) {
        super("Akari");
        this.eventHandler = eventHandler;

        customize(frameDataInfo);
        JButton jButton = new JButton();
        jButton.setVisible(false);
        this.add(jButton);
        addBackground(getBackgroundPath());

        this.pack();
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(getDimension());
    }

    protected void addButton(JButton button) {
        this.add(button);
    }

    private void addBackground(RESOURCES backgroundPath) {
        if (backgroundPath != null) {
            JLabel jLabel = new JLabel(new ImageIcon(backgroundPath.getValue()));
            this.add(jLabel);
        }
    }
}
