
package proiect;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class ManagementApp implements Runnable
{
    @Override
    public void run()
    {
        JFrame frame = new Settings();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}

class YellowSoundsApp implements Runnable
{
    @Override
    public void run()
    {
        JFrame frame = new YellowSounds();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

public class Shop 
{
    public static void main(String[] args) 
    {
        
        /*ManagementApp r = new ManagementApp();
        //YellowSoundsApp ys1 = new YellowSoundsApp();
       //YellowSoundsApp ys2 = new YellowSoundsApp();
       Thread rt = new Thread(r);
        //Thread ys1t = new Thread(ys1);
        //Thread ys2t = new Thread(ys2);
       rt.start();
        //ys1t.start();
        //ys2t.start();
        /*
        try
        {
            rt.join();
            ys1t.join();
          //  ys2t.join();
        }
        catch(InterruptedException e)
        {
            System.out.println(e.getMessage());
        }*/
        
        SwingUtilities.invokeLater(new ManagementApp());
        SwingUtilities.invokeLater(new YellowSoundsApp());
           
    } 
    
    
}
