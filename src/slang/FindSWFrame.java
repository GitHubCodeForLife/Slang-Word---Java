package slang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FindSWFrame extends JFrame implements ActionListener, TableModelListener {
	JButton btnBack, btnFind;
	JTextField textField;
	JTable jt;
	JLabel titleLabel1;
	DefaultTableModel model;
	SlangWord slangword;
	final JOptionPane optionPane = new JOptionPane("The only way to close this dialog is by\n"
			+ "pressing one of the following buttons.\n" + "Do you understand?", JOptionPane.QUESTION_MESSAGE,
			JOptionPane.YES_NO_OPTION);
	String data[][] = { { "", "", "" } };

	FindSWFrame() throws Exception {
		Container con = this.getContentPane();
		slangword = SlangWord.getInstance();
		// Title Label
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Find Slang Words");
		titleLabel.setForeground(Color.green);
		titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);

		// Result Label
		titleLabel1 = new JLabel();
		titleLabel1.setText("Enter slang word to find out Meaning ");
		titleLabel1.setForeground(Color.black);
		titleLabel1.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		titleLabel1.setAlignmentX(CENTER_ALIGNMENT);

		// Form
		JPanel form = new JPanel();
		// form.setBackground(Color.black);
		JLabel formLabel = new JLabel("Input Slang word");
		textField = new JTextField();
		btnFind = new JButton("Find");
		btnFind.addActionListener(this);
		btnFind.setMnemonic(KeyEvent.VK_ENTER);
		// SpringLayout layout = new SpringLayout();
		form.setLayout(new BorderLayout(10, 10));

		form.add(formLabel, BorderLayout.LINE_START);
		form.add(textField, BorderLayout.CENTER);
		form.add(btnFind, BorderLayout.LINE_END);
		Dimension size = new Dimension(700, 50);
		form.setMaximumSize(size);
		form.setPreferredSize(size);
		form.setMinimumSize(size);
		// Table result
		JPanel panelTable = new JPanel();
		panelTable.setBackground(Color.black);

//		String data[][] = {};
		String column[] = { "STT", "Slag", "Meaning" };

		jt = new JTable(new DefaultTableModel(column, 0));
		jt.setRowHeight(30);
		model = (DefaultTableModel) jt.getModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		jt.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		jt.getModel().addTableModelListener(this);
		JScrollPane sp = new JScrollPane(jt);
		panelTable.setLayout(new GridLayout(1, 1));
		panelTable.add(sp);

		// Button Back
		JPanel bottomPanel = new JPanel();
		btnBack = new JButton("Back ");
		// btnBack.addActionListener(this);
		btnBack.setFocusable(false);
		bottomPanel.add(btnBack);
		btnBack.addActionListener(this);
		btnBack.setAlignmentX(CENTER_ALIGNMENT);

		// Setting Content
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(titleLabel);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(titleLabel1);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(form);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(panelTable);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(btnBack);
		// Setting JFrame
		this.setTitle("Find Slang Words");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnFind) {
			String key = textField.getText();
			// System.out.println("a" + key + "a");
			if (key.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please input slang word you want to find", "Inane error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Object[] options = { "Find Flow Slang Word", "Find Slang word have key" };
			int n = JOptionPane.showOptionDialog(this, "Choose mode " + "you want to excute?", "Choose mode find",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

			if (n == 0) {
				long startTime = System.currentTimeMillis();
				String result = slangword.getMeaning(key);
				long endTime = System.currentTimeMillis();
				long timeElapsed = endTime - startTime;
				if (result != null)
					titleLabel1.setText(
							"Execution time in milliseconds(1 Results ): " + String.valueOf(timeElapsed) + " ms");
				else
					titleLabel1.setText("Can't not find that slangWord");

				// System.out.println(data[0] + data[1] + data[2]);
				// Add Row
				this.clearTable();
				String ss[] = { "1", key, result };
				model.addRow(ss);

			} else if (n == 1) {
				this.clearTable();
				// Find a list
				long startTime = System.currentTimeMillis();
				String[][] result = slangword.findContain(key);

				int size = result.length;
				for (int i = 0; i < size; i++) {
					String ss[] = { String.valueOf(i + 1), result[i][0], result[i][1] };
					model.addRow(ss);
				}
				long endTime = System.currentTimeMillis();
				long timeElapsed = endTime - startTime;
				titleLabel1.setText("Execution time in milliseconds(" + String.valueOf(size) + " Results ): "
						+ String.valueOf(timeElapsed) + " ms");
			}
			try {
				slangword.saveHistory(key);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == btnBack) {
			this.dispose();
			new MenuFrame();
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {

		int row = jt.getSelectedRow();
		int col = jt.getSelectedColumn();
		if (row == col && row == -1)
			return;
		String Data = (String) jt.getValueAt(row, col);
		System.out.println("Table element selected is: " + row + col + " : " + Data);
//		if (col == 1) {
//			// edit slag
//			slangword.set((String) jt.getValueAt(row, 1), (String) jt.getValueAt(row, 2));
//			JOptionPane.showMessageDialog(this, "Addded a row.");
//		}
		if (col == 2) {
			// edit meaning
			slangword.set((String) jt.getValueAt(row, 1), (String) jt.getValueAt(row, 2));
			JOptionPane.showMessageDialog(this, "Updated a row.");
		}
		jt.setFocusable(false);
	}

	void clearTable() {
		int rowCount = model.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}
}
