package adherent;

import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;
//new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))
public class Fenetre
    extends JPanel{
    private String image = "/img/accueil.png";
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(Toolkit.getDefaultToolkit().getImage(image),0,0,this);
    }
    public Fenetre(){
      super();
    }
}
