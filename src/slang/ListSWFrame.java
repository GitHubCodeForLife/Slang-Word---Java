package slang;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

public class ListSWFrame extends JFrame implements ActionListener, TableModelListener {
	JButton btnBack;
	JTable jt;
	SlangWord slangWord;

	public ListSWFrame() throws Exception {
		Container con = this.getContentPane();
		slangWord = SlangWord.getInstance();

		// Label
		JLabel titleLabel = new JLabel();
		titleLabel.setText("List Slang Words");
		titleLabel.setForeground(Color.green);
		titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		// titleLabel.setBackground(Color.black);
		// titleLabel.setOpaque(true);

		// Label
		JLabel resultLabel = new JLabel();
		resultLabel.setForeground(Color.black);
		resultLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		resultLabel.setAlignmentX(CENTER_ALIGNMENT);
		// resultLabel.setBackground(Color.blue);
		// resultLabel.setOpaque(true);

		// List Slang Words
		JPanel panelTable = new JPanel();
		panelTable.setBackground(Color.black);
		String data[][] = slangWord.getData();
		String column[] = { "STT", "Slag", "Meaning" };
		resultLabel.setText("We have " + data.length + " slang words");
		jt = new JTable(data, column);
		jt.setRowHeight(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		jt.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		// jt.putClientProperty("terminateEditOnFocusLost", true);
		jt.getModel().addTableModelListener(this);

		JScrollPane sp = new JScrollPane(jt);
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.X_AXIS));
		panelTable.add(sp);

		// Button Back
		JPanel bottomPanel = new JPanel();
		btnBack = new JButton("Back ");
		btnBack.addActionListener(this);
		btnBack.setFocusable(false);
		btnBack.setAlignmentX(CENTER_ALIGNMENT);
		bottomPanel.add(btnBack);

		// Add to con
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(titleLabel);
		con.add(Box.createRigidArea(new Dimension(0, 20)));
		con.add(resultLabel);
		con.add(Box.createRigidArea(new Dimension(0, 20)));
		con.add(panelTable);
		con.add(Box.createRigidArea(new Dimension(0, 20)));
		con.add(bottomPanel);
		// Setting JFrame
		this.setTitle("List Slang Words");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnBack) {
			this.dispose();
			new MenuFrame();
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = jt.getSelectedRow();
		int col = jt.getSelectedColumn();
		String Data = (String) jt.getValueAt(row, col);
		System.out.println("Table element selected is: " + Data);
//		if (col == 1) {
//			// edit slag
//			slangWord.set((String) jt.getValueAt(row, 1), (String) jt.getValueAt(row, 2));
//			JOptionPane.showMessageDialog(this, "Addded a row.");
//		}
		if (col == 2) {
			// edit meaning
			slangWord.set((String) jt.getValueAt(row, 1), (String) jt.getValueAt(row, 2));
			JOptionPane.showMessageDialog(this, "Updated a row.");
		}
	}
}
