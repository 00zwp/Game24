import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.http.WebSocket;
import java.util.Random;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CompetitionWindows extends JFrame {
    public int count = 0, life = 100, nowtime = 60, score = 0; // i-用于记录提交次数
    private JButton Button1,Button2,Button3,Button4; //四个数字图片
    private JButton OperateButton1,OperateButton2,OperateButton3,OperateButton4,OperateButton5,OperateButton6;
    private JButton GameButton1,GameButton2,GameButton3,GameButton4;
    private JProgressBar lifeBar;
    private JTextField textfield;
    private Thread thread;
    private Boolean isClicked;
    public String s="";
    public int a,b,c,d;
    public JLabel label, label1, label2, label3, label4, labelscore; //label 提示label ， label1-4 时间生命
    private Timer timer;
    private Game24 firstgame;

    public CompetitionWindows() {}
    // Button 数字 GameButton 游戏操作  OperateButton +

    public void initWindows()  {
        this.setLayout(null);
        this.setTitle("24点-比赛模式");
        this.setBounds(300,50,800,650);
        this.setVisible(true);

        //添加数字图片按钮
        this.addnumButton();
        this.addoperateBuntton();
        this.addgameButton();
        this.addText();
        this.addActionall();
        //设置时间控件
        this.addlabel();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                firstgame.show_initial_windows();
            }
        });
        this.setResizable(false);
    }//初始化各种控件

    private void addlabel() {
        // label 调整时间生命值的见监听器
        label = new JLabel("欢迎来到24点的闯关模式,努力直到耗尽一切吧！");
        label1 = new JLabel("目前生命值：");
        label2 = new JLabel(String.valueOf(life));
        label3 = new JLabel("剩余时间：");
        label4 = new JLabel(String.valueOf(nowtime));

        label.setBounds(10,10,700,50);
        label1.setBounds(50,50,200,50);
        label2.setBounds(235,50,100,50);
        label3.setBounds(500,50,200,50);
        label4.setBounds(650,50,100,50);

        Font font = new Font("楷体",Font.BOLD,26);
        label.setFont(font);
        label1.setFont(font);
        label2.setFont(font);
        label3.setFont(font);
        label4.setFont(font);

        this.add(label);
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);

        int delay=1000;
        ActionListener taskPerformer=new ActionListener()
        {
            public void actionPerformed (ActionEvent evt)
            {
                nowtime -= 1;
                label4.setText(String.valueOf(nowtime));
                if(nowtime<0){
                    //进入最后画面
                    getEndWindows();
                    timer.stop();
                }
            }
        };
        timer = new Timer(delay,taskPerformer);

        lifeBar = new JProgressBar();
        lifeBar.setOrientation(JProgressBar.HORIZONTAL);
        lifeBar.setMaximum(100);
        lifeBar.setMinimum(0);
        lifeBar.setValue(100);
        lifeBar.setBounds(280,60,200,30);
        lifeBar.setBorderPainted(true);
        lifeBar.setBackground(Color.blue);
        lifeBar.setForeground(Color.red);
        lifeBar.setVisible(true);
        this.add(lifeBar);

        labelscore = new JLabel("目前得分："+score);
        labelscore.setBounds(120,550,500,50);
        labelscore.setFont(font);
        this.add(labelscore);
    }

    public void addActionall() {
        this.GameButton1.addActionListener(new gameEvent());
    }

    public void addnumButton() {// 1-new 对象，2-设置位置，3-填充图片， 4-监听事件(由于一开始图片未显示，需要在开始按钮后添加监听)
        this.Button1 =new JButton();
        this.Button2 =new JButton();
        this.Button3 =new JButton();
        this.Button4 =new JButton();

        this.Button1.setBounds(50,100,100,150);
        this.Button2.setBounds(250,100,100,150);
        this.Button3.setBounds(450,100,100,150);
        this.Button4.setBounds(650,100,100,150);

        this.Button1.setIcon(new ImageIcon("./imgs/number_img/first.JPG"));
        this.Button2.setIcon(new ImageIcon("./imgs/number_img/first.JPG"));
        this.Button3.setIcon(new ImageIcon("./imgs/number_img/first.JPG"));
        this.Button4.setIcon(new ImageIcon("./imgs/number_img/first.JPG"));

        this.add(this.Button1);
        this.add(this.Button2);
        this.add(this.Button3);
        this.add(this.Button4);
    }//初始化数字图片的按钮

    public void addoperateBuntton() {
        this.OperateButton1 =new JButton();
        this.OperateButton2 =new JButton();
        this.OperateButton3 =new JButton();
        this.OperateButton4 =new JButton();
        this.OperateButton5 =new JButton();
        this.OperateButton6 =new JButton();

        this.OperateButton1.setBounds(60,270,60,60);
        this.OperateButton2.setBounds(180,270,60,60);
        this.OperateButton3.setBounds(300,270,60,60);
        this.OperateButton4.setBounds(420,270,60,60);
        this.OperateButton5.setBounds(540,270,60,60);
        this.OperateButton6.setBounds(660,270,60,60);

        this.OperateButton1.setIcon(new ImageIcon("./imgs/operate_img/1.PNG"));
        this.OperateButton2.setIcon(new ImageIcon("./imgs/operate_img/2.PNG"));
        this.OperateButton3.setIcon(new ImageIcon("./imgs/operate_img/3.PNG"));
        this.OperateButton4.setIcon(new ImageIcon("./imgs/operate_img/4.PNG"));
        this.OperateButton5.setIcon(new ImageIcon("./imgs/operate_img/5.PNG"));
        this.OperateButton6.setIcon(new ImageIcon("./imgs/operate_img/6.PNG"));

        this.add(this.OperateButton1);
        this.add(this.OperateButton2);
        this.add(this.OperateButton3);
        this.add(this.OperateButton4);
        this.add(this.OperateButton5);
        this.add(this.OperateButton6);
    }

    public void addgameButton() {
        this.GameButton1 =new JButton("开始");
        this.GameButton2 =new JButton("提交");
        this.GameButton3 =new JButton("清空");
        this.GameButton4 =new JButton("提示");

        Font font = new Font("楷体",Font.BOLD,22);
        this.GameButton1.setFont(font);
        this.GameButton2.setFont(font);
        this.GameButton3.setFont(font);
        this.GameButton4.setFont(font);

        this.GameButton1.setBounds(120,480,100,50);
        this.GameButton2.setBounds(260,480,100,50);
        this.GameButton3.setBounds(390,480,100,50);
        this.GameButton4.setBounds(530,480,100,50);

        this.add(this.GameButton1);
        this.add(this.GameButton2);
        this.add(this.GameButton3);
        this.add(this.GameButton4);
    }

    public void addText() {
        this.textfield = new JTextField();
        this.textfield.setOpaque(false);                             //设置文本框透明
        this.textfield.setEditable(false);                           //设置文本框不可编辑
        this.textfield.setBounds(100,350,620,100);
        Font font = new Font("宋体",Font.BOLD,100);
        this.textfield.setFont(font);
        this.add(this.textfield);
    }

    public CompetitionWindows(Game24 firstgame) {
        this.firstgame = firstgame;
        this.initWindows();
    }

    class ButtonEvent implements ActionListener {                     //定义牌按钮的监视器类
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==Button1){
                s+=""+a;
                textfield.setText(s);
                Button1.setEnabled(false);
            }
            if(e.getSource()==Button2){
                s+=""+b;
                textfield.setText(s);
                Button2.setEnabled(false);
            }
            if(e.getSource()==Button3){
                s+=""+c;
                textfield.setText(s);
                Button3.setEnabled(false);
            }
            if(e.getSource()==Button4){
                s+=""+d;
                textfield.setText(s);
                Button4.setEnabled(false);
            }
        }
    }

    class OperateEvent implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==OperateButton1){
                s+="+";
                textfield.setText(s);
            }
            if(e.getSource()==OperateButton2){
                s+="-";
                textfield.setText(s);
            }
            if(e.getSource()==OperateButton3){
                s+="*";
                textfield.setText(s);
            }
            if(e.getSource()==OperateButton4){
                s+="/";
                textfield.setText(s);
            }
            if(e.getSource()==OperateButton5){
                s+="(";
                textfield.setText(s);
            }
            if(e.getSource()==OperateButton6){
                s+=")";
                textfield.setText(s);
            }
        }
    }

    class gameEvent implements ActionListener {                     //定义牌按钮的监视器类
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==GameButton1){
                //需要为各个图片初始话参数，并激活各个按钮的监听
                if(count==0){
                    count+=1;
                    life = 100;
                    //激活各个按钮监听-zwp
                    GameButton2.addActionListener(new gameEvent());
                    GameButton3.addActionListener(new gameEvent());
                    GameButton4.addActionListener(new gameEvent());

                    Button1.addActionListener(new ButtonEvent());
                    Button2.addActionListener(new ButtonEvent());
                    Button3.addActionListener(new ButtonEvent());
                    Button4.addActionListener(new ButtonEvent());

                    OperateButton1.addActionListener(new OperateEvent());
                    OperateButton2.addActionListener(new OperateEvent());
                    OperateButton3.addActionListener(new OperateEvent());
                    OperateButton4.addActionListener(new OperateEvent());
                    OperateButton5.addActionListener(new OperateEvent());
                    OperateButton6.addActionListener(new OperateEvent());

                    timer.start();
                }
                String textforButton = GameButton1.getText();
                if(textforButton=="开始"){
                    setThread();
                    GameButton1.setText("暂停");
                    thread.start();
                    GameButton2.setEnabled(false);
                }
                else if(textforButton=="暂停")
                {
                    isClicked = true;
                    GameButton1.setText("开始");
                    GameButton1.setEnabled(false);
                    GameButton2.setEnabled(true);
                }
            }
            if(e.getSource()==GameButton2){
                //-zwp 要判断是否为24
                GameButton1.setEnabled(true); //需要更改成自己的计算算法-zwp
                GameButton2.setEnabled(false);
                String str=textfield.getText();
                int len=str.length();
                StringBuffer sb= new StringBuffer(s);
                for(int i=0;i<sb.length();i++){
                    if(sb.charAt(i)=='1'){
                        int j=i+1;
                        if(j<sb.length()&&sb.charAt(j)=='0')
                        {
                            sb=sb.replace(i, j,"$");
                            sb.deleteCharAt(j);
                            i=0;
                        }
                    }
                }
                try{
                    Stack<Character> ct=new Stack<Character>();
                    Stack<Character> c1 = new Stack<Character>();
                    Stack<Character> s1 = new Stack<Character>();
                    ct.push(new Character('#'));
                    for(int i=sb.length()-1;i>=0;i--)
                        ct.push(new Character(sb.charAt(i)));
                    s1.push(new Character('#'));
                    char ch=ct.peek().charValue();
                    char y;
                    while(ch!='#'){
                        if(isDigit(ch))
                            c1.push(ct.peek().charValue());
                        else if(ch==')')
                            for(y=s1.peek().charValue(),s1.pop();y!='(';y=s1.peek().charValue(),s1.pop())
                                c1.push(new Character(y));
                        else{
                            for(y=s1.peek().charValue(),s1.pop();icp(ch)<=isp(y);y=s1.peek().charValue(),s1.pop())
                                c1.push(new Character(y));
                            s1.push(new Character(y));
                            s1.push(new Character(ch));
                        }
                        ct.pop();
                        ch=ct.peek().charValue();
                    }
                    while(!s1.isEmpty()){
                        y=s1.peek().charValue();
                        s1.pop();
                        if(y!='#')
                            c1.push(new Character(y));
                    }
                    s1.push(new Character('#'));
                    while(c1.isEmpty()==false){
                        char temp=c1.peek().charValue();
                        if(temp>='1'&&temp<='9')
                            temp=(char)(temp-48);
                        else if(temp=='$')
                            temp=(char)(temp-26);
                        s1.push(new Character(temp));
                        c1.pop();
                    }
                    Stack<Character> re=new Stack<Character>();

                    char cha[]=new char[20];
                    int i=0;
                    while(s1.peek().charValue()!='#')
                    {
                        cha[i++]=s1.peek().charValue();
                        s1.pop();
                    }
                    cha[i]='#';
                    for(i=0;cha[i]!='#';){
                        if(cha[i]=='+'){
                            cha[i-2]=(char)(cha[i-1]+cha[i-2]);
                            deletetwoElements(i-1,cha);
                            i=0;
                        }
                        else if(cha[i]=='-'){
                            cha[i-2]=(char)(cha[i-2]-cha[i-1]);
                            deletetwoElements(i-1,cha);
                            i=0;
                        }
                        else if(cha[i]=='*'){
                            cha[i-2]=(char)(cha[i-1]*cha[i-2]);
                            deletetwoElements(i-1,cha);
                            i=0;
                        }
                        else if(cha[i]=='/'){
                            cha[i-2]=(char)(cha[i-2]/cha[i-1]);
                            deletetwoElements(i-1,cha);
                            i=0;
                        }
                        else i++;
                    }
                    if((int)cha[0]==24){
                        textfield.setText("   Y^o^Y   ");
                        nowtime += 10 ;
                        score += 10;
                        labelscore.setText("目前得分："+score);
                    }
                    else {
                        life-= 5*count;
                        lifeBar.setValue(life);
                        label2.setText(Integer.toString(life));
                        if(life<0){
                            //进入最后界面
                            getEndWindows();
                        }
                        count += 1;
                        textfield.setText("  (＞﹏＜)");}
                }catch(Exception ee){}
                //提交之后自动进入下一个题目
                setThread();
                GameButton1.setText("暂停");
                thread.start();
            }
            if(e.getSource()==GameButton3){
                s="";
                textfield.setText(null);
                Button1.setEnabled(true);
                Button2.setEnabled(true);
                Button3.setEnabled(true);
                Button4.setEnabled(true);
            }
            if(e.getSource()==GameButton4){
                //-zwp
                int op1,op2,op3;
                for(op1=1;op1<=4;op1++)
                    for(op2=1;op2<=4;op2++)
                        for(op3=1;op3<=4;op3++){
                            if(calculate_model1(a,b,c,d,op1,op2,op3)==24)
                                System.out.println("(("+a+showFuHao(op1)+b+")"+showFuHao(op2)+c+")"+showFuHao(op3)+d); /*对应表达式类型：((A□B)□C)□D*/
                            if(calculate_model2(a,b,c,d,op1,op2,op3)==24)
                                System.out.println("("+a+showFuHao(op1)+"("+b+showFuHao(op2)+c+")"+")"+showFuHao(op3)+d);/*对应表达式类型：(A□(B□C))□D */
                            if(calculate_model3(a,b,c,d,op1,op2,op3)==24)
                                System.out.println(a+showFuHao(op1)+"("+b+showFuHao(op2)+"("+c+showFuHao(op3)+d+"))");/*对应表达式类型：A□(B□(C□D))*/
                            if(calculate_model4(a,b,c,d,op1,op2,op3)==24)
                                System.out.println(a+showFuHao(op1)+"(("+b+showFuHao(op2)+c+")"+showFuHao(op3)+d+")");/*对应表达式类型：A□((B□C)□D)*/
                            if(calculate_model5(a,b,c,d,op1,op2,op3)==24)
                                System.out.println("("+a+showFuHao(op1)+b+")"+showFuHao(op2)+"("+c+showFuHao(op3)+d+")");/*对应表达式类型：(A□B)□(C□D)*/
                        }
            }
        }
    }

    //需要建立一个utils类

    int icp(char ch){
        switch(ch){
            case '#':return 0;
            case '(':return 7;
            case '*':
            case '/':return 4;
            case '+':
            case '-':return 2;
            case ')':return 1;
        }
        return 0;
    }

    int isp(char ch){
        switch(ch){
            case '#':return 0;
            case '(':return 1;
            case '*':
            case '/':return 5;
            case '+':
            case '-':return 3;
            case ')':return 7;
        }
        return 0;
    }

    void deletetwoElements(int i,char cha[]){
        for(int j=i;j<cha.length-2;j++){
            cha[j]=cha[j+2];
        }
    }

    boolean isDigit(char ch){
        char c=ch;
        if(c>='0'&&c<='9'||c=='$')
            return true;
        else return false;
    }

    public float calculate_model1(float i,float j,float k,float t,int op1,int op2,int op3){
        float r1,r2,r3;                                           /*对应表达式类型：((A□B)□C)□D*/
        r1 = cal(i,j,op1);
        r2 = cal(r1,k,op2);
        r3 = cal(r2,t,op3);
        return r3;
    }

    public  float cal(float x,float y,int op){                               //计算两个操作数
        switch(op){
            case 1:return x+y;
            case 2:return x-y;
            case 3:return x*y;
            case 4:return x/y;
        }
        return 0;
    }

    public float calculate_model2(float i,float j,float k,float t,int op1,int op2,int op3){
        float r1,r2,r3;                                         /*对应表达式类型：(A□(B□C))□D */
        r1 = cal(j,k,op2);
        r2 = cal(i,r1,op1);
        r3 = cal(r2,t,op3);
        return r3;
    }

    public float calculate_model3(float i,float j,float k,float t,int op1,int op2,int op3){
        float r1,r2,r3;                                     /*对应表达式类型：A□(B□(C□D))*/
        r1 = cal(k,t,op3);
        r2 = cal(j,r1,op2);
        r3 = cal(i,r2,op1);
        return r3;
    }

    public float calculate_model4(float i,float j,float k,float t,int op1,int op2,int op3){
        float r1,r2,r3;                                         /*对应表达式类型：A□((B□C)□D)*/
        r1 = cal(j,k,op2);
        r2 = cal(r1,t,op3);
        r3 = cal(i,r2,op1);
        return r3;
    }

    public float calculate_model5(float i,float j,float k,float t,int op1,int op2,int op3){
        float r1,r2,r3;                                            /*对应表达式类型：(A□B)□(C□D)*/
        r1 = cal(i,j,op1);
        r2 = cal(k,t,op3);
        r3 = cal(r1,r2,op2);
        return r3;
    }

    String showFuHao(int op){
        switch(op){
            case 1:return "+";
            case 2:return "-";
            case 3:return "*";
            case 4:return "/";
        }
        return "";}

    public void setThread() {this.thread = new Thread(new Runnable(){
            public void run(){
                isClicked=false;
                while(isClicked==false){
                    try{
                        Thread.sleep(2);
                        int a1,b1,c1,d1;
                        a1=new Random().nextInt(11); //添加照片时需要修改的界限-zwp
                        b1=new Random().nextInt(11);
                        c1=new Random().nextInt(11);
                        d1=new Random().nextInt(11);
                        if(a1==0) a1++;
                        if(b1==0) b1++;
                        if(c1==0) c1++;
                        if(d1==0) d1++;
                        setButtonIcon(a1,b1,c1,d1);
                        if(isClicked==true){
                            a=a1; b=b1; c=c1; d=d1;
                            return ;
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getEndWindows(){
        this.removeAll();
        Graphics g = this.getGraphics();
        final ImageIcon bgImageIcon = new ImageIcon("./imgs/loss.PNG");
        final Image image = bgImageIcon.getImage();
        g.drawImage(image,0,0,this.getWidth(),this.getHeight(),null);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                firstgame.show_initial_windows();
                dispose();
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void setButtonIcon(int i,int j,int k,int t){
        String aPath="./imgs/number_img/"+i+".JPG";
        String bPath="./imgs/number_img/"+j+".JPG";
        String cPath="./imgs/number_img/"+k+".JPG";
        String dPath="./imgs/number_img/"+t+".JPG";
        Button1.setIcon(new ImageIcon(aPath));
        Button2.setIcon(new ImageIcon(bPath));
        Button3.setIcon(new ImageIcon(cPath));
        Button4.setIcon(new ImageIcon(dPath));
    }

    public static void main(String []args)
    {
        new CompetitionWindows().initWindows();
    }
}
