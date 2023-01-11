package pl.pw.pap.akari.view.component.frames;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.component.event.EVENT_TYPE;
import pl.pw.pap.akari.model.component.event.SettingsUpdateEvent;
import pl.pw.pap.akari.model.game.settings.DIFFICULTY_LEVEL;
import pl.pw.pap.akari.model.game.settings.GameSettings;
import pl.pw.pap.akari.model.resources.RESOURCES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class SettingsFrame extends AbstractAkariFrame {
    private final JSlider widthSlider;
    private final JSlider heightSlider;
    private final JComboBox<DIFFICULTY_LEVEL> difficultyLevelComboBox;

    public SettingsFrame(EventHandler eventHandler, GameSettings gameSettings) {
        super(eventHandler, null);
        this.getContentPane().removeAll();
        JLabel background = new JLabel(new ImageIcon(getBackgroundPath().getValue()));
        this.setContentPane(background);
        this.getContentPane().setLayout(new GridBagLayout());

        var widthLabel = new JLabel("Width", SwingConstants.CENTER);
        widthSlider = makeSlider(gameSettings.getMinWidth(), gameSettings.getMaxWidth(), gameSettings.getX());
        addRow(widthLabel, widthSlider);

        var heightLabel = new JLabel("Height", SwingConstants.CENTER);
        heightSlider = makeSlider(gameSettings.getMinHeight(), gameSettings.getMaxHeight(), gameSettings.getY());
        addRow(heightLabel, heightSlider);

        var difficultyLevelLabel = new JLabel("Difficulty", SwingConstants.CENTER);
        difficultyLevelComboBox = new JComboBox<>(DIFFICULTY_LEVEL.values());
        difficultyLevelComboBox.setSelectedItem(gameSettings.getDifficultyLevel());
        difficultyLevelComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                eventHandler.handleEvent(createUpdateEvent());
            }
        });
        addRow(difficultyLevelLabel, difficultyLevelComboBox);

        var jTextArea = new JTextArea(10, 40);
        addRow(jTextArea);
        
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

    public void updateSettings(GameSettings gameSettings) {
        widthSlider.setMinimum(gameSettings.getMinWidth());
        widthSlider.setMaximum(gameSettings.getMaxWidth());
        widthSlider.setValue(gameSettings.getX());

        heightSlider.setMinimum(gameSettings.getMinHeight());
        heightSlider.setMaximum(gameSettings.getMaxHeight());
        heightSlider.setValue(gameSettings.getY());

        difficultyLevelComboBox.setSelectedItem(gameSettings.getDifficultyLevel());
    }

    private JSlider makeSlider(int min, int max, int value) {
        var slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        slider.setOpaque(false);
        slider.addChangeListener(e -> {
            if (!slider.getValueIsAdjusting()) {
                eventHandler.handleEvent(createUpdateEvent());
            }
        });
        return slider;
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

    private SettingsUpdateEvent createUpdateEvent() {
        return new SettingsUpdateEvent(widthSlider.getValue(), heightSlider.getValue(), (DIFFICULTY_LEVEL) difficultyLevelComboBox.getSelectedItem());
    }
}
