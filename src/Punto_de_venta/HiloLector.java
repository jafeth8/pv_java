package Punto_de_venta;

import java.io.InputStream;

import javax.swing.JOptionPane;

public class HiloLector extends Thread {
	
	private final InputStream is;
	
	public HiloLector(InputStream is) {
		this.is = is;
	}
	
	@Override
	public void run() {
		try {
			byte[] buffer = new byte[1000];
			int leido = is.read(buffer);
			while (leido > 0) {
				String texto = new String(buffer, 0, leido);
				System.out.print(texto);
				leido = is.read(buffer);
			}
			
			} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(),"Al parecer hubo un error",JOptionPane.ERROR_MESSAGE);	
			System.out.println(e.toString());
			
		}
	}
}
