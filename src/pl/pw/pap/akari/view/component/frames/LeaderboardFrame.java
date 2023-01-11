package pl.pw.pap.akari.view.component.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.component.event.EVENT_TYPE;
import pl.pw.pap.akari.model.game.tournament.TournamentInfo;
import pl.pw.pap.akari.model.resources.RESOURCES;

public class LeaderboardFrame extends AbstractAkariFrame {

    public LeaderboardFrame(EventHandler eventHandler, TournamentInfo tournamentInfo) {
        super(eventHandler, null);
        this.getContentPane().removeAll();
        JLabel background = new JLabel(new ImageIcon(getBackgroundPath().getValue()));
        this.setContentPane(background);
        this.getContentPane().setLayout(new GridBagLayout());

//        var widthLabel = new JLabel("Width", SwingConstants.CENTER);
//        widthSlider = makeSlider(gameSettings.getMinWidth(), gameSettings.getMaxWidth(), gameSettings.getX());
//        addRow(widthLabel, widthSlider);
//
//        var heightLabel = new JLabel("Height", SwingConstants.CENTER);
//        heightSlider = makeSlider(gameSettings.getMinHeight(), gameSettings.getMaxHeight(), gameSettings.getY());
//        addRow(heightLabel, heightSlider);

//        var difficultyLevelLabel = new JLabel("Difficulty", SwingConstants.CENTER);
//        difficultyLevelComboBox = new JComboBox<>(DIFFICULTY_LEVEL.values());
//        difficultyLevelComboBox.setSelectedItem(gameSettings.getDifficultyLevel());
//        difficultyLevelComboBox.addItemListener(e -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                eventHandler.handleEvent(createUpdateEvent());
//            }
//        });
//        addRow(difficultyLevelLabel, difficultyLevelComboBox);

        JList<String> scoreList = new JList<>(tournamentInfo.getScores());
        
        

        //scoreList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scoreList.setLayoutOrientation(JList.VERTICAL);
//        scoreList.setVisibleRowCount(-1);
        
        scoreList.setOpaque(false); // przejrzyste
        scoreList.setAlignmentX(CENTER_ALIGNMENT);
        scoreList.setFont(new Font("Arial", Font.BOLD, 20));
        
        JScrollPane scrollPane = new JScrollPane(scoreList);
        
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) scoreList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
//        scrollPane.setBackground(new Color(0,0,0,0));
        addRow(scrollPane);
//        addRow(scoreList);
        
        
        var backButton = new JButton("Back");
        backButton.setBackground(Color.white);
        backButton.addActionListener(e -> this.eventHandler.handleEvent(new CommonEvent(EVENT_TYPE.CHANGE_CONTEXT_TO_MENU_EVENT)));
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

   
}
