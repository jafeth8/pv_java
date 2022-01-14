package Punto_de_venta;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;



public class ColorCeldaJtable extends JTable{
	/*
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			Component componente=super.prepareRenderer(renderer, row, column);
			if(getValueAt(row, column).getClass().equals(Double.class)) {
				getValueAt(row, column).getClass();
				double valor=Double.parseDouble(this.getValueAt(row, column).toString());
				if(valor<3) {
					componente.setBackground(Color.RED);
					componente.setForeground(Color.WHITE);
				}
			}else {
				componente.setBackground(Color.WHITE);
				componente.setForeground(Color.BLACK);
			}
			return componente;
		}*/
	
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component componente=super.prepareRenderer(renderer, row, column);
		if(getValueAt(row, column).getClass().equals(Double.class)) {
			getValueAt(row, column).getClass();
			double valor=Double.parseDouble(this.getValueAt(row, column).toString());
			if(valor<3) {
				componente.setBackground(Color.RED);
				componente.setForeground(Color.WHITE);
			}
		}else {
			componente.setBackground(Color.WHITE);
			componente.setForeground(Color.BLACK);
		}
		return componente;
	}


}
