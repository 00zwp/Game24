import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Algorithm {

    public Vector<String> expressions;

    public Algorithm(){
        expressions = new Vector<String>();
    }

    public void show_all_Expression(){
        System.out.println(expressions.size());
        for(String exp:expressions){
            System.out.println(exp);
        }
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

    public static void main(String[] args) {

    }
}

