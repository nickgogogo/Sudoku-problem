import java.awt.*;   
import javax.swing.*;   
import javax.swing.border.*;   
import javax.swing.table.*;   

public class TableExample{   
    public static void main(String[] args) {   
        final JFrame f = new JFrame("TableExample");   
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        f.getContentPane().add(createTable());   
        f.pack();   
        SwingUtilities.invokeLater(new Runnable(){   
            public void run() {   
                f.setLocationRelativeTo(null);   
                f.setVisible(true);   
            }   
        });   
    }   

    static JComponent createTable() {   
        final JTable table = new JTable(9,9) {   
            private static final long serialVersionUID = 0;   

            Color B = Color.RED;   
            Color C = this.getGridColor();   
            final Border[][] borders = {   
                {new ZoneBorder(C,B,B,B), new ZoneBorder(B,C,C,C), new ZoneBorder(B,B,C,C)},   
                {new ZoneBorder(C,C,C,B), new ZoneBorder(C,C,C,C), new ZoneBorder(C,B,C,C)},   
                {new ZoneBorder(C,C,B,B), new ZoneBorder(C,C,B,C), new ZoneBorder(C,B,B,C)}   
            };   

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {   
                Component result = super.prepareRenderer(renderer, row, column);   
                if (result instanceof JComponent) {   
                    if(row == 0 && column == 0)
                    ((JComponent) result).setBorder(borders[0][0]);   
                }   
                return result;   
            }   
        };   
        table.setRowHeight(28);   
        //table.setGridColor(Color.BLACK);   
        TableColumnModel tcm = table.getColumnModel();   
        for(int c = 0; c<table.getColumnCount(); ++c) {   
            TableColumn tc = tcm.getColumn(c);   
            tc.setPreferredWidth(28);   
        }   
        JPanel inner = new JPanel(new GridLayout());   
        //inner.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));   
        inner.add(table);   
        return inner;   
    }   
}  