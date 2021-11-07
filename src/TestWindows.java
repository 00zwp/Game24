import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestWindows extends JFrame {
    private JButton Button1,Button2,Button3,Button4;
    private JButton GameButton;
    private ImageIcon icon1,icon2,icon3,icon4;
    private Thread thread;
    private JLabel label;
    private JTable table;
    private DefaultTableModel tablemodel;
    private Game24 firstgame;
    private Font font = new Font("楷体",Font.BOLD,22);
    private int[] numcount =new int[]{0,0,0,0};
    private int[] endarray = new int[]{0,0,0,0};
    private int[] numarray =new int[]{0,0,0,0};
    private Timer timer;
    private int nowtime = 10;
    String [][]possible = {};
    String [] columnname = {"所有可能结果"};


    public void initWindows(){

        this.setLayout(null);
        this.setTitle("24点-练习模式");
        this.setBounds(300,50,600,500);
        this.setVisible(true);

        //控件初始化
        this.addnumButton();
        this.addLabel();
        this.setThread();

        this.setnumButtonListener();
        this.setTimer();

        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                firstgame.show_initial_windows();
            }
        });
    }

    private void setTimer() {
        int delay=1000;
        ActionListener taskPerformer=new ActionListener()
        {
            public void actionPerformed (ActionEvent evt)
            {
                nowtime -= 1;
                for (int index = tablemodel.getRowCount() - 1; index >= 0; index--) {
                    tablemodel.removeRow(index);
                }
                String[] timetext = {"还剩''''''"+nowtime+"''''''秒"};
                tablemodel.addRow(timetext);
                if(nowtime<0){
                    timer.stop();
                    for (int index = tablemodel.getRowCount() - 1; index >= 0; index--) {
                        tablemodel.removeRow(index);
                    }
                    start_calculate();
                }
            }
        };
        this.timer = new javax.swing.Timer(delay,taskPerformer);
    }

    private void start_calculate() {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Algorithm algorithm =new Algorithm();
        int[] temp =new int[]{0,0,0,0};
        if (!algorithm.check(endarray, temp, 0)){
            String[] timetext = {"这组数字没有24点"};
            tablemodel.addRow(timetext);
        }
        else {
            for(int i=0;i<4;i++){array.add(endarray[i]);}
            algorithm.calcualte(array,24,"");
            for(String exp: algorithm.expressions){tablemodel.addRow(new String[]{exp});}
        }
    }

    public void Button_icon_initial(){
        Button1.setIcon(new ImageIcon("./imgs/back.PNG"));
        Button2.setIcon(new ImageIcon("./imgs/back.PNG"));
        Button3.setIcon(new ImageIcon("./imgs/back.PNG"));
        Button4.setIcon(new ImageIcon("./imgs/back.PNG"));
    }

    public void setnumButtonListener() {
        Button1.addActionListener(new ButtonEvent());
        Button2.addActionListener(new ButtonEvent());
        Button3.addActionListener(new ButtonEvent());
        Button4.addActionListener(new ButtonEvent());
        GameButton.addActionListener(new GameEvent());
        GameButton.setEnabled(false);
    }

    class ButtonEvent implements ActionListener {                     //定义牌按钮的监视器类
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==Button1){
                if(numcount[0]==0){endarray[0] = numarray[0];
                Button1.setIcon(new ImageIcon("./imgs/number_img/"+numarray[0]+".JPG"));}
                numcount[0] = 1 ;
            }
            if(e.getSource()==Button2){
                if (numcount[1]==0){endarray[1] = numarray[1];
                Button2.setIcon(new ImageIcon("./imgs/number_img/"+numarray[1]+".JPG"));}
                numcount[1] = 1 ;
            }
            if(e.getSource()==Button3){
                if(numcount[2]==0){endarray[2] = numarray[2];
                Button3.setIcon(new ImageIcon("./imgs/number_img/"+numarray[2]+".JPG"));}
                numcount[2] = 1 ;
            }
            if(e.getSource()==Button4){endarray[3] = numarray[3];
                if(numcount[3]==0){Button4.setIcon(new ImageIcon("./imgs/number_img/"+numarray[3]+".JPG"));}
                numcount[3] = 1 ;
            }
        }
    }

    class GameEvent implements ActionListener {                     //定义牌按钮的监视器类
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==GameButton){
                GameButton.setEnabled(false);
                Button_icon_initial();
                nowtime = 10;
                setThread();
                for(int i=0;i<4;i++){numcount[i]=0;}
                for (int index = tablemodel.getRowCount() - 1; index >= 0; index--) {
                    tablemodel.removeRow(index);
                }
                if(timer.isRunning()){
                timer.stop();}
            }
        }
    }

    public void setThread(){
        this.thread = new Thread(new Runnable(){
            public void run(){
                while(Arrays.stream(numcount).sum()!=4)
                {
                    int index = 0;
                    for(int i:numcount){
                        if(i==0){
                            int x = new Random().nextInt(10)+1;
                            numarray[index] = x ;
                            index += 1;
                        }
                    }
                    try{
                        thread.sleep(4);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                String[] timetext = {"还剩''''''"+nowtime+"''''''秒"};
                tablemodel.addRow(timetext);
                timer.start();
                GameButton.setEnabled(true);
                return ;
            }
        });
        this.thread.start();
    }

    public void addLabel(){
        this.label = new JLabel("训练模式，发牌后只有十秒思考时间");
        this.label.setBounds(10,10,500,50);
        this.label.setFont(this.font);
        this.add(label);

        this.tablemodel = new DefaultTableModel(this.possible, this.columnname);
        this.table = new JTable(this.tablemodel);
        this.table.setBounds(30,220,500,50);
        this.table.setEnabled(false);
        this.table.setRowHeight(50);

        JScrollPane scrollPane = new JScrollPane(this.table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.table.setFont(this.font);
//        this.add(table);
        scrollPane.setBounds(30,220,500,230);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    } //将label与Table一同安置

    public void addnumButton(){
        Button1 = new JButton();
        Button2 = new JButton();
        Button3 = new JButton();
        Button4 = new JButton();

        Button1.setBounds(30,60,100,150);
        Button2.setBounds(160,60,100,150);
        Button3.setBounds(290,60,100,150);
        Button4.setBounds(420,60,100,150);

        this.Button_icon_initial();

        this.add(Button1);
        this.add(Button2);
        this.add(Button3);
        this.add(Button4);

        GameButton = new JButton("下一轮");
        GameButton.setFont(font);
        GameButton.setBounds(420, 5, 150,50);

        this.add(GameButton);
    }

    public TestWindows() {}

    public TestWindows(Game24 firstgame) {
        this.firstgame = firstgame;
        this.initWindows();
    }

    public static void main(String []args)
    {
        new TestWindows(new Game24()).initWindows();
    }

}
