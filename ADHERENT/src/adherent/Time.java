package adherent;

import java.awt.*;
import javax.swing.*;

public class Time extends JWindow {
  private String noms[]={"","",
      "",""
  ,"",""};
  private ImageIcon []icones={new ImageIcon(noms[0]),new ImageIcon(noms[1]),new ImageIcon(noms[2]),new ImageIcon(noms[3])
  ,new ImageIcon(noms[4]),new ImageIcon(noms[5])};
  public Time(final Frame f, int waitTime) {
      super(f);
      //cree un label avec notre image

      final JLabel jlabel = new JLabel("Connexion au serveur de chat en cours...",
                                     new javax.swing.ImageIcon(getClass().getResource("/img/accueil.png")), SwingConstants.CENTER);
      jlabel.setFont(new Font("serif",Font.BOLD,14));
      jlabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/accueil.png")));
      jlabel.setBackground(Color.white);
      
      //ajoute le label au panel
      getContentPane().add(jlabel, BorderLayout.CENTER);
      pack();
      //centre le splash screen
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension labelSize = jlabel.getPreferredSize();
      setLocation(screenSize.width / 2 - (labelSize.width / 2),
              screenSize.height / 2 - (labelSize.height / 2));
      //afin d'acceder � la valeur WaitTime
      final int pause = waitTime;

      //thread pour fermer le splash screen
      final Runnable closeRunner = new Runnable() {
          public void run() {
              setVisible(false);
              dispose();
              f.setVisible(true);
          }
      };


      Runnable waitRunner = new Runnable() {
          public void run() {
            for(int j=1;j<=5;j++)
                          {jlabel.setIcon(icones[j]);
                            try {
                              Thread.sleep(pause / 5);
                            }
                            catch (InterruptedException ex) {
                            }
                          }

              try {//Thread.sleep(pause);
                  //lance le thread qui ferme le splash screen
                  SwingUtilities.invokeAndWait(closeRunner);
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      };


      //affiche le splash screen
      setVisible(true);

      //lance le thread qui ferme le splash screen apres un certain temps
      Thread splashThread = new Thread(waitRunner, "SplashThread");
      splashThread.start();
  }

}
