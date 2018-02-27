import java.awt.Component;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 *
 * @author Bart Cremers
 * @date Feb 6, 2006
 */
public class TableBorders extends JFrame {

	private JTable table;

	protected void frameInit() {
		super.frameInit();

		table = new JTable(5, 6);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new BorderColorRenderer());
		add(table);
	}

	public static void main(String[] args) {
		JFrame f = new TableBorders();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	private static class BorderColorRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Border border;
			switch (column) {
			case 1:
				border = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GREEN);
				break;
			case 2:
				border = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.RED);
				break;
			case 3:
				border = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLUE);
				break;
			case 4:
				border = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.PINK);
				break;
			case 5:
				border = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.CYAN);
				break;
			default:
				border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
				break;
			}
			JComponent comp = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
		
			comp.setBorder(border);
			return comp;
		}
	}
}