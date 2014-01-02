/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage.ui.settings;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author isaac
 */
public class IPEditor extends AbstractCellEditor implements TableCellEditor{

    JFormattedTextField component;
    RegexFormatter ipmask;
    
    public IPEditor(){
        
    }
    
    @Override
    public Object getCellEditorValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
