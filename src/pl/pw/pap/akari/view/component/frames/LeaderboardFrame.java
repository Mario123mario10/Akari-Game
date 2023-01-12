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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

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

        Object [][] scores = tournamentInfo.getScores();
        
        String [] columnNames = {"ranking", "name", "time"};
        
        DefaultTableModel scoreModel = new DefaultTableModel(scores, columnNames);
        JTable scoreTable = new JTable(scoreModel);
        
        scoreTable.setFont(new Font("Arial", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(scoreTable);

        
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        addRow(scrollPane);

        
        
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
