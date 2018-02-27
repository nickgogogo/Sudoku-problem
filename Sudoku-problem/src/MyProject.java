import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.ListSelectionModel;

public class MyProject extends JFrame implements ActionListener, ComponentListener {
	Thread t;//main thread
	private JFrame frame;
	private JTable table;
	private JScrollPane sp;
	private JTextArea textArea;
	String[][] cells = new String[9][9];
	/* Int characters calculate speed is much swift than String 
	 * in Violent crack processing, the huge calculation lead to we choose Integer replaced of string to crack*/
	Integer[][] Trace = new Integer[9][9];
	String[][] storedcells = new String[9][9];
	String[] Column = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };//call TableCellRenderer
	String possible = "123456789";//excluding impossible numbers from 1-9 are possible numbers
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyProject window = new MyProject();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public MyProject() {
		initialize();

	}

	
	
	/**
	 * Get value of table and check the number of existing figures are valid .
	 */
	
	private void gettable() {// get each point position
		int count = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (table.getValueAt(i, j) == null || table.getValueAt(i, j).toString().equals(" ")) {
					cells[i][j] = " ";
					storedcells[i][j] = " ";
					count++;
				} else {
					cells[i][j] = table.getValueAt(i, j).toString();
					storedcells[i][j] = cells[i][j];
					
//					if (check(i, j, Integer.valueOf(cells[i][j])) == false) {
//
////						System.out.println(Trace[i][j]);
//						JOptionPane.showMessageDialog(null, "The sudoku is contradictory, please check it again",
//								"InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
//						textArea.append("Contradictory sudoku" + "\n");
//						count = 0;
//						table.setEnabled(true);
//						t.stop();
//					}
					
					Trace[i][j] = Integer.valueOf(table.getValueAt(i, j).toString());
				}
			}
		}

		if (count > 64 && count < 81) {
			JOptionPane.showMessageDialog(null, "The solutions are more than one", "InfoBox: " + "Warning",
					JOptionPane.INFORMATION_MESSAGE);
			textArea.append("The solutions are more than one" + "\n");
			count = 0;
			table.setEnabled(true);
			t.stop();
		}
		if (count == 81) {
			JOptionPane.showMessageDialog(null, "The sudoku is empty, please check it again", "InfoBox: " + "Warning",
					JOptionPane.INFORMATION_MESSAGE);
			textArea.append("The sudoku is empty, please check it again" + "\n");
			count = 0;
			table.setEnabled(true);
			t.stop();
		}

		count = 0;

	}

	
	


	int row, flag1 = 0;
	int flag2 = 0;
	/**
	 * use X mark the impossible position for each number.
	 */
	public void mark() throws InterruptedException {// mark impossible position
		for (int a = 1; a < 10; a++) {
			row = a;

			for (int i = 0; i < 9; i++) { 
				for (int j = 0; j < 9; j++) {

					if (cells[i][j].equals(String.valueOf(row))) {

						for (int b = 0; b < 9; b++) {
							storedcells[b][j] = "X";
							storedcells[i][b] = "X";

							storedcells[i][j] = String.valueOf(row);
						}

						int x = i / 3;
						int y = j / 3;
						for (int m = 0; m < 3; m++) {
							for (int n = 0; n < 3; n++) {
								storedcells[m + 3 * x][n + 3 * y] = "X";
							}
						}

					}

				}

			}

			checkmark();
			
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					storedcells[i][j] = cells[i][j];
				}
			}
			
		}
	}

	
	
	
	
	public void Crosshatching() throws InterruptedException {
		textArea.append("Method 1: Crosshatching..." + "\n");
		textArea.selectAll();
		gettable();
		mark();
		// show();// first step show
		// possible();// check completed condition and show possible figure

	}
	/**
	 * According to last step marks to preliminary judge result
	 */
	public void checkmark() throws InterruptedException {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// System.out.println(storedcells[0][0]);
				if (storedcells[i][j].equals(" ")) {
					// System.out.print(i);
					// System.out.println(j);

					int x = i / 3;
					int y = j / 3;
					int count = 0;
					for (int m = 0; m < 3; m++) {
						for (int n = 0; n < 3; n++) {

							if (storedcells[m + 3 * x][n + 3 * y].equals(String.valueOf(" "))) {

								count++;
							}
						}
					}
					// System.out.println(count);

					if (count == 1) {//十字法每次解出数字的个数

						cells[i][j] = String.valueOf(row);
						flag1 = 1;
						flag2++;

//						table.revalidate();
//						show();
//						table.updateUI();
						// Thread.currentThread().sleep(1000);
					}
					count = 0;
				}
				// storedcells[i][j] = cells[i][j];
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				storedcells[i][j] = cells[i][j];
			}
		}
		
		/**
		 * if we could get new cell, use Checkmark method again to make sure
		 * every cell is completed in crosshatching algorithm
		 */
		if (flag1 == 1) {
			flag1 = 0;
			// textArea.append(flag2+"th time Crosshatching" + "\n");
			// Thread.sleep(1);
			show();
			mark();

		}

	}

	
	
	
	/**
	 * record possible number and show it in cell 
	 */
	public void possible() throws InterruptedException {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (cells[i][j].equals(" ")) {
					for (int a = 0; a < 9; a++) {
						if (possible.contains(cells[a][j]))
							possible = possible.replace(cells[a][j], "");
					}
					for (int b = 0; b < 9; b++) {

						if (possible.contains(cells[i][b]))
							possible = possible.replace(cells[i][b], "");

					}
					int x = i / 3;
					int y = j / 3;

					for (int m = 0; m < 3; m++) {
						for (int n = 0; n < 3; n++) {
							if (possible.contains(cells[m + 3 * x][n + 3 * y]))
								possible = possible.replace(storedcells[m + 3 * x][n + 3 * y], "");
						}
					}
					storedcells[i][j] = possible;
					possible = "123456789";
				} else
					storedcells[i][j] = cells[i][j];
			}
		}
		textArea.append("List possible number" + "\n");
		textArea.selectAll();
		t.sleep(1000);
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				table.setValueAt(storedcells[i][j], i, j);
			}
		}
		t.sleep(1000);
	}
	
	
	/**
	 * several Display function
	 */
	public void show() {
		try {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					table.setValueAt(cells[i][j], i, j);
				}
			}
			
			t.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showTrace() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				cells[i][j] = String.valueOf(Trace[i][j]);
				table.setValueAt(cells[i][j], i, j);
				cells[i][j] = null;
			}
		}
	}
	private void impossibleshow() {
		for(int i=0; i<9;i++) {
			for(int j=0;j<9;j++) {
				if (table.getValueAt(i, j).toString().length()>1) {
					cells[i][j]="Error";
					//System.out.println(cells[i][j]);
					//System.out.println(table.getValueAt(i, j));
					table.setValueAt(cells[i][j], i, j);
					
				}
				
			}table.getColumn(Column[i]).setCellRenderer(tcr);
		}
	}
	
	
	
	
	
	

	/**
	 * detect whether Sudoku is solved by Crosshatching method
	 * if not, do Violent cracking 
	 */
	public void laststep() throws InterruptedException {

		int stop = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (cells[i][j].equals(" "))
					stop = 1;// check completed
			}
		}
		if (stop == 0) {
			textArea.append("Solved the puzzle " + "\n");
			textArea.selectAll();
			table.setEnabled(true);
			t.stop();
		}
		if (stop == 1) {

			textArea.append("Stuck" + "\n");
			textArea.selectAll();
			t.sleep(3000);
			possible();
			textArea.selectAll();

			textArea.append("Crosshatching methond can not solve problem" + "\n");
			textArea.selectAll();
			t.sleep(1000);
			textArea.append("Method 2: Violent cracking..." + "\n");
			textArea.selectAll();
			t.sleep(1000);
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (!cells[i][j].equals(" "))
						Trace[i][j] = Integer.parseInt(cells[i][j]);
					else
						Trace[i][j] = null;
				}
			}
			/**
			 * Guess result one by one from the (0,0) position
			 */
			EnumerativeAlgorithm(0, 0);

		}
		stop = 0;
	}

	
	
	
	/**
	 * Guess result one by one from the (0,0) position
	 */
	int mm = 0;
	private void EnumerativeAlgorithm(int x, int y) throws InterruptedException {
		// System.out.println(n1.hashCode());
		
		/**
		 * Even if excessive cost, programming still have no solution
		 * it proved the Sodoku is no solution
		 */
		if (mm > 4000000) {
			
			showTrace();
			impossibleshow();
			
			JOptionPane.showMessageDialog(null, "The solution of puzzle is not exists",
					"InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
			textArea.append("The solution of puzzle is not exists" + "\n");
			
			textArea.selectAll();
			table.setEnabled(true);
			mm = 0;
			t.sleep(1000);
			
			t.stop();
		}
		mm++;

		if (y > 8) {
			x++;
			y = 0;
		}
		if (x == 9 && y == 0) {
			textArea.append("Solved the puzzle " + "\n");
			textArea.selectAll();
			showTrace();
			table.setEnabled(true);
			t.stop();
		}
		if (Trace[x][y] == null && x < 9 && y < 9) {
			// System.out.println(storedcells[x][y].hashCode());
			for (int a = 0; a < storedcells[x][y].length(); a++) {
				if (check(x, y, Integer.parseInt(storedcells[x][y].substring(a, a + 1))) == true) {
					Trace[x][y] = Integer.parseInt(storedcells[x][y].substring(a, a + 1));
					// System.out.print(k);
					// cells[x][y] = storedcells[x][y].substring(a, storedcells[x][y].length());
					EnumerativeAlgorithm(x, y + 1);
					Trace[x][y] = null;
				} else {
					cells[x][y] = storedcells[x][y].substring(a, storedcells[x][y].length());
				}
			}

		}

		else
			/**
			 * Iterative operation
			 */
			EnumerativeAlgorithm(x, y + 1);

	}

	
	
	/**
	 * check all number in cell is valid
	 */
	public boolean check(int i, int j, Integer k) {// check k is suitable for position(i,j)

		for (int a = 0; a < 9; a++) {
			if (Trace[a][j] == k) {
			//	System.out.println("1.x" + a + "y" + j + "k" + k+ " " + Trace[a][j]);
				return false;
			}
		}
		for (int b = 0; b < 9; b++) {
			if (Trace[i][b] == k) {
			//	System.out.println("2.x" + i + "y" + b + "k" + k+ " " + Trace[i][b]);
				return false;
			}
		}

		int a = i / 3;
		int b = j / 3;
		for (int m = 0; m < 3; m++) {
			for (int n = 0; n < 3; n++) {
				if (Trace[a * 3 + m][b * 3 + n] == k) {
			//		System.out.println("3.");
					return false;
				}
			}
		}
		//System.out.println("x" + a + "y" + j + "k" + k + " " + Trace[i][j]);
		return true;

	}

	/**
	 * Initialize the contents of the frame.
	 */
	Object n1 = null;

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 435, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		JButton btnLoad = new JButton("Load");
		panel.add(btnLoad);
		btnLoad.addActionListener(this);

		JButton btnRun = new JButton("Run");
		panel.add(btnRun);
		btnRun.addActionListener(this);

		JButton btnInterrupt = new JButton("Interrupt");
		panel.add(btnInterrupt);
		btnInterrupt.addActionListener(this);

		JButton btnClear = new JButton("Clear");
		panel.add(btnClear);
		btnClear.addActionListener(this);

		JButton btnQuit = new JButton("Quit");
		panel.add(btnQuit);
		btnQuit.addActionListener(this);

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table.setGridColor(Color.BLACK);
		
		/**
		 * Jtable cellrenderer setting and details
		 */
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		table.setDefaultRenderer(Integer.class, tcr);
		table.setDefaultRenderer(String.class, tcr);
		table.setDefaultRenderer(Object.class, new BorderColorRenderer());
		
		

		table.setModel(new DefaultTableModel(
				new Object[][] { { n1, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null }, },
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }) {
			/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { Object.class, Object.class, Object.class, Object.class, Object.class,
					Object.class, Object.class, Object.class, Integer.class };
			
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setColumnHeaderView(table);
		
		JPanel panel_1 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(20, Short.MAX_VALUE)));

		textArea = new JTextArea();
		textArea.setBackground(Color.GREEN);
		textArea.setRows(6);
		textArea.setColumns(35);
		panel_1.add(textArea);
		panel_1.add(getJTextArea(), null);
		frame.getContentPane().setLayout(groupLayout);
		textArea.append("Ready." + "\n");
		textArea.selectAll();
	}

	private JScrollPane getJTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			// jtextarea.setBounds(5, 45, 650, 400);

		}

		// jtextarea.setLineWrap(true);
		sp = new JScrollPane(textArea);
		sp.setBounds(5, 45, 650, 400);
		// sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return sp;
	}
	
	
	
	
	/**
	 * Choose corresponding action
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("Quit")) {
			System.exit(0);
		}
		if (command.equals("Load")) {
			table.removeAll();
			table.removeEditor();
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select an file");
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt files", "txt");
			jfc.addChoosableFileFilter(filter);
			int returnValue = jfc.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {

				File file = jfc.getSelectedFile();

				textArea.append("Loading " + jfc.getName(file) + "\n");
				textArea.selectAll();
				try {
					t.sleep(100);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				// System.out.println("Loading "+jfc.getName(file));
				try {

					FileReader reader = new FileReader(file);
					BufferedReader br = new BufferedReader(reader);

					// System.out.println(table.getValueAt(4, 0));

					// read(0,0);

					for (int i = 0; i < 9; i++) {
						String s = br.readLine();

						int j = 0;
						int index = 0;
						while (j <= 8) {
							/**
							 * Other characters except _ and 1-9 number are not read
							 */
							if (s.substring(index, index + 1).equals("_")) {
								cells[i][j] = " ";
								j++;
								index++;
							} else if (Character.isDigit(s.substring(index, index + 1).charAt(0))) {
								cells[i][j] = s.substring(index, index + 1);
								j++;
								index++;
							} else
								index++;

						}

					}
					br.close();

					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 9; j++) {
							table.setValueAt(cells[i][j], i, j);
							
							//fireTableCellUpdated(i, j);
						}

						table.getColumn(Column[i]).setCellRenderer(tcr);
						// table.getColumn(Column[i]).setCellRenderer(new MyTableCellRenderer());
					}

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}

		if (command.equals("Run")) {

			t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						table.setEnabled(false);
						textArea.append("Solving procedure starts :" + "\n");
						textArea.selectAll();
						t.sleep(1000);
						
						Crosshatching();
						laststep();
						table.setEnabled(true);
						
						t.stop();

						// trynumber();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			t.start();
			
			if (t.isAlive()) {
				t.resume();
				 textArea.append("Continue" + "\n");
				 textArea.selectAll();
			}
			table.setEnabled(true);
		}
		
		
		if (command.equals("Interrupt")) {
			table.setEnabled(true);
			t.suspend();
			textArea.append("User interrupted" + "\n");
			textArea.selectAll();
		}

		if (command.equals("Clear")) {
			table.setEnabled(true);
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					table.setValueAt("", i, j);

				}
				// table.getColumn(Column[i]).setCellRenderer(tcr);
			}
			textArea.append("Clear sudoku table" + "\n");
			textArea.selectAll();
			t.stop();
		}
	}

	
	
	/**
	 * Processing cells view including color and stronger grid
	 */
	DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {//Processing cells view including color and stonger grid
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JComponent cell = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (!table.getValueAt(row, column).equals("")) {
				String ss = table.getValueAt(row, column).toString();//the figure showed in cells
				setHorizontalAlignment(JLabel.CENTER);
				Border border;
				if(row==3 && column !=3 &&column!=6) {
					border = BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black);
				}
				else if(row==6&& column !=3 &&column!=6) {
					border = BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black);
				}
				else if(column ==3&& row != 3 && row != 6) {
					border = BorderFactory.createMatteBorder(0, 3, 0, 0, Color.black);
				}
				else if(column ==6&& row != 3 && row != 6) {
					border = BorderFactory.createMatteBorder(0, 3, 0, 0, Color.black);
				
				}
				else if(column ==3&&row == 3) {
					border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
				
				}
				else if(column ==3&&row == 6) {
					border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
				
				}
				else if(column ==6&&row == 3) {
					border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
				
				}
				else if(column ==6&&row == 6) {
					border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
				
				}else
					border = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);
				cell.setBorder(border);
				
				
				if (table.getValueAt(row, column).equals(" ") 
						|| table.getValueAt(row, column).equals("") || table.getValueAt(row, column).equals("0")) {
					cell.setBackground(Color.WHITE);
				}
				else if ((ss.length() > 1)&& !ss.equals("Error")) {
					cell.setBackground(Color.WHITE);
				}
				else if (ss.length()==1) {
					cell.setBackground(Color.YELLOW);
				}
				else if (ss.equals("Error")){
					cell.setBackground(Color.RED);
				}
			}
			else {
				cell.setBackground(Color.white);
				setHorizontalAlignment(JLabel.CENTER);
				Border border;
				if(row==3 && column !=3 &&column!=6) {
					border = BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black);
				}
				else if(row==6&& column !=3 &&column!=6) {
					border = BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black);
				}
				else if(column ==3&& row != 3 && row != 6) {
					border = BorderFactory.createMatteBorder(0, 3, 0, 0, Color.black);
				}
				else if(column ==6&& row != 3 && row != 6) {
					border = BorderFactory.createMatteBorder(0, 3, 0, 0, Color.black);
				
				}
				else if(column ==3&&row == 3) {
					border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
				
				}
				else if(column ==3&&row == 6) {
					border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
				
				}
				else if(column ==6&&row == 3) {
					border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
				
				}
				else if(column ==6&&row == 6) {
					border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
				
				}else
					border = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);
				cell.setBorder(border);
			}return cell;
		}
	};
	
	
	/**
	 * First cells view before loading
	 */
	private static class BorderColorRenderer extends DefaultTableCellRenderer {//First cells view before loading

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Border border;
			JComponent comp = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			if(row==3 && column !=3 &&column!=6) {
				border = BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black);
			}
			else if(row==6&& column !=3 &&column!=6) {
				border = BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black);
			}
			else if(column ==3&& row != 3 && row != 6) {
				border = BorderFactory.createMatteBorder(0, 3, 0, 0, Color.black);
			}
			else if(column ==6&& row != 3 && row != 6) {
				border = BorderFactory.createMatteBorder(0, 3, 0, 0, Color.black);
			
			}
			else if(column ==3&&row == 3) {
				border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
			
			}
			else if(column ==3&&row == 6) {
				border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
			
			}
			else if(column ==6&&row == 3) {
				border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
			
			}
			else if(column ==6&&row == 6) {
				border = BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black);
			
			}else
				border = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);

			comp.setBorder(border);
			return comp;
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
}
