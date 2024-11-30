package style;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class IconCellRenderer extends JLabel implements TableCellRenderer {

    public IconCellRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JLabel) {
            JLabel label = (JLabel) value;
            setIcon(label.getIcon());
            setText(""); // Ne pas afficher de texte
        } else {
            setIcon(null);
            setText(value != null ? value.toString() : "");
        }

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        setOpaque(true);
        return this;
    }
}