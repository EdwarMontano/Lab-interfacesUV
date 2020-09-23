
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stiven-Montaño
 */
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class Laboratorio_1 extends JComponent {
    public  static  void  main(String[] args)
	  {
	    JFrame  f = new JFrame(" Laboratorio 1 - interface UV");//Titulo de la ventana
	    f.setSize(800, 800);//tamaño de la ventana
	    f.getContentPane().add(new Laboratorio_1());//agrego mi clase al componente
	    f.setVisible(true);
	  }

	  public  void  paintComponent(Graphics g)
	  {
	    g.drawString(" Esta Vaina está Vacía", 400, 400);
	  }
}


