import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.*;

public class FunWindows extends JFrame{
    public Game24 firstgame;
    public String path="./imgs/Fun_img/";
    public  int x;
    public Timer timer;
    public void initial_windows()
    {
        this.setLayout(null);
        this.setBounds(300,50,400,600);

        this.setVisible(true);
        this.setResizable(true);
        x = new Random().nextInt(19)+1;
        changeIcon(x,path);
        this.setTimer();
    }
    public void setTimer() {
        int delay=1000;
        ActionListener taskPerformer=new ActionListener()
        {
            public void actionPerformed (ActionEvent evt)
            {
                x = new Random().nextInt(2)+1;
                changeIcon(x,path);
            }
        };
        this.timer = new Timer(delay, taskPerformer);
        timer.start();
    }

    public void changeIcon(int x,String path)
    {
        int height = this.getHeight();
        int width = this.getWidth();

        Graphics g = this.getGraphics();
        ImageIcon bgImageIcon = new ImageIcon(path+x+".JPG");
        Image image = bgImageIcon.getImage();

        g.drawImage(image, 0, 0, width, height, null);
    }
    public FunWindows()
    {
        this.initial_windows();
    }
    public static void main(String[] args)
    {
        new FunWindows();
    }
    public FunWindows(Game24 firstgame)
    {
        this.firstgame = firstgame;
        this.initial_windows();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                firstgame.show_initial_windows();
            }
        });
    }
}
