package pl.pw.pap.akari.view.component.frames;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.component.event.EVENT_TYPE;
import pl.pw.pap.akari.model.component.event.SettingsUpdateEvent;
import pl.pw.pap.akari.model.component.event.TournamentUpdateEvent;
import pl.pw.pap.akari.model.game.settings.DIFFICULTY_LEVEL;
import pl.pw.pap.akari.model.game.settings.GameSettings;
import pl.pw.pap.akari.model.resources.RESOURCES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class TournamentFrame extends AbstractAkariFrame {
    private final JTextArea nameList;

    public TournamentFrame(EventHandler eventHandler) {
        super(eventHandler, null);
        this.getContentPane().removeAll();
        JLabel background = new JLabel(new ImageIcon(getBackgroundPath().getValue()));
        this.setContentPane(background);
        this.getContentPane().setLayout(new GridBagLayout());

        var nameDescription = new JLabel ("Enter players names", SwingConstants.CENTER);
        addRow(nameDescription);
        
        nameList = new JTextArea(10, 40);
        addRow(nameList);
        
        var backButton = new JButton("Play");
        backButton.setBackground(Color.white);
        backButton.addActionListener(e -> 
        {
        	eventHandler.handleEvent(createUpdateEvent());
        	this.eventHandler.handleEvent(new CommonEvent(EVENT_TYPE.TOURNAMENT_GAME_EVENT));
        });
        
        addRow(backButton);

        Font font = new Font("sans", Font.BOLD, 22);
        for (var component : this.getContentPane().getComponents()) {
            component.setFont(font);
        }
    }

    private static GridBagConstraints getDefaultGridBagConstraints() {
        var constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 20, 0, 20);
        return constraints;
    }

    @Override
    protected void customize(Object frameDataInfo) {

    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(600, 800);
    }

    @Override
    protected RESOURCES getBackgroundPath() {
        return RESOURCES.MENU_FRAME_BACKGROUND_PATH;
    }

    private void addRow(Component... components) {
        if (components.length == 0)
            return;
        var constraints = getDefaultGridBagConstraints();
        constraints.gridwidth = 1;
        for (int i = 0; i < components.length - 1; i++) {
            add(components[i], constraints);
        }
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        add(components[components.length - 1], constraints);
    }

    private TournamentUpdateEvent createUpdateEvent() {
        return new TournamentUpdateEvent(nameList.getText().split("\n"));
    }
    
    
}
