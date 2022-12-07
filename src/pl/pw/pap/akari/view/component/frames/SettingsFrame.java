package pl.pw.pap.akari.view.component.frames;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.component.event.EVENT_TYPE;
import pl.pw.pap.akari.model.game.settings.DIFFICULTY_LEVEL;
import pl.pw.pap.akari.model.resources.RESOURCES;
import pl.pw.pap.akari.service.SettingsService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class SettingsFrame extends AbstractAkariFrame {
    private final SettingsService settingsService;
    private final Font font;

    public SettingsFrame(EventHandler eventHandler) {
        super(eventHandler, null);
        settingsService = eventHandler.getSettingsService();
        this.getContentPane().removeAll();
        JLabel background = new JLabel(new ImageIcon(getBackgroundPath().getValue()));
        this.setContentPane(background);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(new GridBagLayout());
        this.font = new Font("sans", Font.PLAIN, 22);
        this.addComponentListener(new BackOnHideComponentListener());

        var c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridheight = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 20, 0, 20);

        c.gridwidth = 1;
        this.add(new JLabel("Width", SwingConstants.CENTER), c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        this.add(makeSlider(this::updateWidth, settingsService.getGameSettings().getX()), c);

        c.gridwidth = 1;
        this.add(new JLabel("Height", SwingConstants.CENTER), c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        this.add(makeSlider(this::updateHeight, settingsService.getGameSettings().getY()), c);

        c.gridwidth = 1;
        this.add(new JLabel("Difficulty", SwingConstants.CENTER), c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        var difficultyComboBox = new JComboBox<>(new DIFFICULTY_LEVEL[] {
                DIFFICULTY_LEVEL.EASY,
                DIFFICULTY_LEVEL.NORMAL,
                DIFFICULTY_LEVEL.HARD});
        difficultyComboBox.setSelectedItem(settingsService.getGameSettings().getDifficultyLevel());
        difficultyComboBox.addItemListener(e -> updateDifficulty((DIFFICULTY_LEVEL) difficultyComboBox.getSelectedItem()));
        this.add(difficultyComboBox, c);

        c.gridheight = 1;
        var backButton = new JButton("Back");
        backButton.setBackground(Color.white);
        backButton.addActionListener(e -> this.eventHandler.handleEvent(new CommonEvent(EVENT_TYPE.CHANGE_CONTEXT_TO_MENU_EVENT)));
        this.add(backButton, c);
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
        return RESOURCES.SETTINGS_BACKGROUND_PATH;
    }

    private void updateWidth(int newWidth) {
        var settings = settingsService.getGameSettings();
        settings.setX(newWidth);
        settingsService.setGameSettings(settings);
    }

    private void updateHeight(int newHeight) {
        var settings = settingsService.getGameSettings();
        settings.setY(newHeight);
        settingsService.setGameSettings(settings);
    }

    private void updateDifficulty(DIFFICULTY_LEVEL difficultyLevel) {
        var settings = settingsService.getGameSettings();
        settings.setDifficultyLevel(difficultyLevel);
        settingsService.setGameSettings(settings);
    }

    private interface IUpdateable {
        void update(int value);
    }

    private JSlider makeSlider(IUpdateable updateable, int value) {
        var slider = new JSlider(JSlider.HORIZONTAL, 5, 10, value);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        slider.setOpaque(false);
        slider.addChangeListener(e -> {
            if (slider.getValueIsAdjusting()) {
                updateable.update(slider.getValue());
                updateWidth(slider.getValue());
            }
        });
        return slider;
    }

    @Override
    public Component add(Component c) {
        c.setFont(font);
        super.add(c);
        return c;
    }

    @Override
    public void add(Component c, Object constraints) {
        c.setFont(font);
        super.add(c, constraints);
    }

    private class BackOnHideComponentListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent componentEvent) {

        }

        @Override
        public void componentMoved(ComponentEvent componentEvent) {

        }

        @Override
        public void componentShown(ComponentEvent componentEvent) {

        }

        @Override
        public void componentHidden(ComponentEvent componentEvent) {
            eventHandler.handleEvent(new CommonEvent(EVENT_TYPE.CHANGE_CONTEXT_TO_MENU_EVENT));
        }
    }
}
