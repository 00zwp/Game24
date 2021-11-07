import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Algorithm {
    char[] chars;
    int[] numbers = new int[4];
    int[] operatesign = new int [3];
    int[] signpriority;
    public Vector<String> expressions;


    public Algorithm(){
        expressions = new Vector<String>();
    }

    public void calcualte(ArrayList<Integer>array,int target,String expression) {
        String s;
        if(array.size()==1){
            if(target==array.get(0)){
                expressions.add(expression);
            }
        }

        else {
            for(int i=0;i<array.size();i++) {
                for(int j=i+1;j<array.size();j++){
                    int x = array.remove(i);
                    int y = array.remove(j-1);
                    array.add(0,x+y);
                    s = "...."+x+"+"+y+"="+(x+y);
                    calcualte(array,target,expression+s);
                    array.remove(0);

                    array.add(0,x-y);
                    s = "...."+x+"-"+y+"="+(x-y);
                    calcualte(array,target,expression+s);
                    array.remove(0);

                    array.add(0,y-x);
                    s = "...."+y+"-"+x+"="+(y-x);
                    calcualte(array,target,expression+s);
                    array.remove(0);

                    array.add(0,x*y);
                    s = "...."+x+"*"+y+"="+(x*y);
                    calcualte(array,target,expression+s);
                    array.remove(0);

                    if(y!=0 && x%y==0)
                    {
                        array.add(0, x / y);
                        s = "...." + x + "/" + y + "=" + (x / y);
                        calcualte(array, target, expression + s);
                        array.remove(0);
                    }
                    if(x!=0&&y%x==0)
                    {
                        array.add(0, y / x);
                        s = "...." + y + "/" + x + "=" + (y / x);
                        calcualte(array, target, expression + s);
                        array.remove(0);
                    }
                    array.add(j-1,y);
                    array.add(i, x);
                }
            }
        }
    }

    public boolean check(int[] initNums,int[] temp,double num){

        for (int i = 0; i < initNums.length; i++) {
            if (temp[i]==0) {//之所以加上temp的限制，是避免元素本身和自己做运算
                temp[i] = 1;
                System.out.println(num);
                if (check(initNums, temp,num + initNums[i])
                        || check(initNums,temp ,num - initNums[i])
                        || check(initNums,temp ,num * initNums[i])
                        || check(initNums, temp,num / initNums[i])) {
                    return true;}
                temp[i] = 0;
            }
        }
        if(num==24)
            return true;
        else
            return false;
    }

    public String getelementsfromString(String str,int[] num) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m =  p.matcher(str);
        String sign = m.replaceAll("").trim();

        String[] split = str.split("\\+|-|\\*|\\/|\\(|\\)");
        int numcount = 0;
        int replace = 0;
        for(int i=0;i<split.length;i++)
        {
            if(split[i]!="")
            {
                replace = 0;

                for(int j=0;j<split[i].length();j++)
                {
                    replace = replace*10 + split[i].charAt(j)-48;
                }
                num[numcount] =replace;
                numcount++;
            }
        }
        return sign;
    }//将符号从字符串中提取

    public int[] getpriority(String sign, int[] signpriority, int[] opratesign) {
        int bracketlevel = 0 ;
        int signlevel = 0;
        int labelnum = 0;
        for(int i=0;i<this.chars.length;i++) {
            switch (this.chars[i]){
                case '(':
                    bracketlevel ++ ;
                    signpriority[i] = bracketlevel*10 + signlevel;
                    break;
                case ')':
                    bracketlevel --;
                    signpriority[i] = bracketlevel*10 + signlevel;
                    break;
                case '*':
                case '/':
                    signpriority[i] = bracketlevel*10 + signlevel +1;
                    opratesign[labelnum++] = i ;
                    break;
                default:
                    signpriority[i] = bracketlevel*10 + signlevel ;
                    opratesign[labelnum++] = i ;
                    break;
            }
        }

        int[] indexofoperate ;
        if(signpriority[opratesign[0]]>signpriority[opratesign[1]])
        {
            if (signpriority[opratesign[0]]>signpriority[opratesign[2]]){
                if(signpriority[opratesign[2]]>signpriority[opratesign[1]]){
                    indexofoperate=new int[]{0,2,1};
                }
                else {
                    indexofoperate=new int[]{0,1,2};
                }
            }
            else {
                    indexofoperate=new int[]{2,0,1};
            }
        }
        else
        {
            if (signpriority[opratesign[0]]>signpriority[opratesign[2]]){
                indexofoperate=new int[]{1,0,2};
            }
            else {
                if(signpriority[opratesign[2]]>signpriority[opratesign[1]]){
                    indexofoperate=new int[]{2,1,0};
                }
                else {
                    indexofoperate=new int[]{1,2,0};
                }
            }
        }
        return indexofoperate;
    }

    public boolean calculate_String(int[] number,int[] opratesign,int[] indexofoperate,String sign){
        char[] chars = sign.toCharArray();
        double calnumber = 0.0;
        double backcalnumber = 0.0;
        int index = indexofoperate[0];
        int[] used = new int[4];
        calnumber = cal(number[index],number[index+1],chars[opratesign[index]]);
        used[index]=0;
        used[index+1] =0;
        index = indexofoperate[1];
        if(index==indexofoperate[0]+1){
            calnumber = cal(calnumber,number[index+1],chars[opratesign[index]]);
            if(indexofoperate[2]==0){
                calnumber = cal(number[0],calnumber,chars[opratesign[0]]);
            }
            else {
                calnumber = cal(calnumber,number[2],chars[opratesign[2]]);
            }
        }
        else if(index==indexofoperate[0]-1){
            calnumber = cal(number[index],calnumber,chars[opratesign[index]]);
            if(indexofoperate[2]==0){
                calnumber = cal(number[0],calnumber,chars[opratesign[0]]);
            }
            else {
                calnumber = cal(calnumber,number[2],chars[opratesign[2]]);
            }
        }
        else
        {
            backcalnumber = cal(number[index],number[index+1],chars[opratesign[index]]);
            if(index>indexofoperate[0])
            {
                calnumber=cal(calnumber,backcalnumber,chars[opratesign[1]]);
            }
            else {
                calnumber=cal(backcalnumber,calnumber,chars[opratesign[1]]);
            }
        }
        return calnumber==24.0;

    }

    public  double cal(double x,double y,char op){                               //计算两个操作数
        switch(op){
            case '+':return x+y;
            case '-':return x-y;
            case '*':return x*y;
            case '/':return x/y;
        }
        return 0;
    }

    public boolean checkString(String str){
        String sign = this.getelementsfromString(str,this.numbers);
        this.chars = str.toCharArray();
        this.signpriority = new int[chars.length];
        int[] indexofoperate = this.getpriority(sign, this.signpriority,this.operatesign);
        return this.calculate_String(this.numbers,this.operatesign,indexofoperate,sign);
    }
    public static void main(String[] args) {
//        Algorithm a = new Algorithm();
//        String str = "3*2*1*4";
//        int[] numbers = new int[4];
//        String sign = a.getelementsfromString(str,numbers);
//        a.chars = str.toCharArray();
//        int[] signpriority =new int [str.toCharArray().length];
//        int[] operatesign = new int [3];
//        int[] indexofoperate = a.getpriority(sign, signpriority, operatesign);
//        System.out.println(a.calculate_String(numbers,operatesign,indexofoperate,sign));
    }
}

