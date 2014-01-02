/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage.ui.settings;

import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
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
       RegexFormatter ipmask = new RegexFormatter("\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}");
       component = new JFormattedTextField(ipmask);
    }
    
    @Override
    public Object getCellEditorValue() {
        return (String)component.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(isSelected){
            component.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        }
        
        component.setText((String)value);
        
        return component;
    }
    
}
