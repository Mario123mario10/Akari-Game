package pl.pw.pap.akari.view.component.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.component.event.EVENT_TYPE;
import pl.pw.pap.akari.model.component.event.TournamentUpdateEvent;
import pl.pw.pap.akari.model.resources.RESOURCES;

public class TournamentFrame extends AbstractAkariFrame {

	private final DefaultListModel<String> listModel;
	private JCheckBox checkBox;

	public TournamentFrame(EventHandler eventHandler) {
		super(eventHandler, null);
		this.getContentPane().removeAll();
		JLabel background = new JLabel(new ImageIcon(getBackgroundPath().getValue()));
		this.setContentPane(background);
		this.getContentPane().setLayout(new GridBagLayout());
//		JLabel checkBoxText = new JLabel();
		checkBox = new JCheckBox("Generate new board for each player?");

		var nameDescription = new JLabel("List of players", SwingConstants.LEFT);
		addRow(nameDescription);

		listModel = new DefaultListModel<String>();

		var list = new JList(listModel); // data has type Object[]
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(3);
		list.setSize(new Dimension(40, 40));
		list.setPreferredSize(new Dimension(40, 40));

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(40, 40));
		listScroller.setMinimumSize(new Dimension(40, 140));

		var playButton = new JButton("Play");
		var deleteButton = new JButton("Remove player");
		var addButton = new JButton("Add player");

		deleteButton.setEnabled(false);
		deleteButton.setBackground(Color.white);
		deleteButton.setSize(new Dimension(1, 1));
		deleteButton.addActionListener(e -> {
			int index = list.getSelectedIndex();
			listModel.remove(index);
			if (listModel.size() == 0) {
				playButton.setEnabled(false);
			}
		});
		addRow(listScroller, deleteButton);

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedIndex() == -1) {
					deleteButton.setEnabled(false);
				} else {
					deleteButton.setEnabled(true);
				}
			}
		});

		var label = new JLabel("New player name:", SwingConstants.LEFT);
		label.setName("New player name:");
		label.setVisible(true);

		var textField = new JTextField(40);
		textField.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (textField.getText().replace(" ", "").length() == 0) {
					addButton.setEnabled(false);
				} else {
					addButton.setEnabled(true);
				}
			}
		});

		addButton.setBackground(Color.white);
		addButton.setEnabled(false);
		addButton.addActionListener(e -> {
			listModel.addElement(textField.getText().trim());
			textField.setText("");
			playButton.setEnabled(true);
		});
		addRow(label);
		addRow(textField, addButton);
		playButton.setEnabled(false);
		playButton.setBackground(Color.white);
		playButton.addActionListener(e -> {
			eventHandler.handleEvent(createUpdateEvent());
			this.eventHandler.handleEvent(new CommonEvent(EVENT_TYPE.TOURNAMENT_GAME_EVENT));
		});
		
		addRow(checkBox);
		
		addRow(playButton);

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
		var size = listModel.size();
		String[] array = new String[size];
		listModel.copyInto(array);
		return new TournamentUpdateEvent(array, checkBox.isSelected());
	}

}
