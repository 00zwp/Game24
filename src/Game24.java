import javax.swing.*;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Game24 extends JFrame {
    private int i ;
    private JLabel imgLabel;
    private JButton ButtonOne, ButtonTwo, ButtonThree;

    public static Game24 firstgame;

    public Game24() {   //利用构造函数将24点游戏的窗口界面显示
        this.i = 0 ;
        this.setLayout(null);
        this.setBounds(300,50,400,600);
        this.addelements();
        this.setResizable(false);
    }

    public void addelements() {//为初始化窗口中创建三个按钮，监听窗口变化
        this.ButtonOne = new JButton();
        this.ButtonOne.setBounds(75,125,256,100);
        this.add(this.ButtonOne);

        this.ButtonTwo = new JButton();
        this.ButtonTwo.setBounds(75,275,256,100);
        this.add(this.ButtonTwo);

        this.ButtonThree = new JButton();
        this.ButtonThree.setBounds(75,425,256,100);
        this.add(this.ButtonThree);
        //加入一个好看的元素
        Icon icon1 = new ImageIcon("./imgs/smile.PNG");
        imgLabel = new JLabel(icon1);
        imgLabel.setBounds(170,30,75,75);
        this.add(imgLabel);

        Font font = new Font("宋体",Font.BOLD,18);

        Icon icon2 = new ImageIcon("./imgs/robot_1.PNG");
        this.ButtonOne.setText("比赛模式");
        this.ButtonOne.setFont(font);
        this.ButtonOne.setIcon(icon2);

        this.ButtonTwo.setText("练习模式");
        this.ButtonTwo.setFont(font);
        this.ButtonTwo.setIcon(new ImageIcon("./imgs/robot.PNG"));

        this.ButtonThree.setText("趣味模式");
        this.ButtonThree.setFont(font);
        this.ButtonThree.setIcon(new ImageIcon("./imgs/robot_2.PNG"));

        this.ButtonOne.addActionListener(new ButtonstartEvent());
        this.ButtonTwo.addActionListener(new ButtonstartEvent());
        this.ButtonThree.addActionListener(new ButtonstartEvent());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //为了显示最初始化的界面
    public void show_initial_windows() {
        if (this.i == 0) {
            this.setTitle("欢迎来到初始选择界面");
        } else {
            this.setTitle("欢迎第%d次来到初始选择界面".formatted(i));
        }
        this.setVisible(true);
        this.i += 1;
    }

    public static void main(String[] args) {
        firstgame = new Game24();
        firstgame.setBackground(Color.black);
        firstgame.show_initial_windows();
    }

    class ButtonstartEvent implements ActionListener {                     //定义牌按钮的监视器类
        public void actionPerformed(ActionEvent e){
            setVisible(false);
            if(e.getSource()== ButtonOne){
                new CompetitionWindows(firstgame);
            }
            else if(e.getSource()== ButtonTwo){
                new TestWindows(firstgame);
            }
            else if(e.getSource()== ButtonThree){
                new FunWindows(firstgame);
            }
        }
    }

}
