import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public test(){}
    public static void main(String[] args){
        String str = "(12-2)*3*4";
        String regEx = "[^0-9]";
        String regsign = "(\\d+)";
        String [] s;
        int[] a = new int[4];
        Pattern p = Pattern.compile("\\d+");
        Matcher m =  p.matcher(str);
        String x = m.replaceAll("").trim();
        String [] split;
        int num = 0;
        int replace = 0;
        String start;
//        System.out.println(str.split("\\*")[0]);
        for(int i=0;i<x.length();i++)
        {
            start =  String.valueOf(x.charAt(i));
            if(str.startsWith(start)){
                str = str.replaceFirst(".","");
            }
            else{
                if(start.equals(new String(")")))
                {
                    split = str.split("\\)");
                }
                else if(start.equals(new String("(")))
                {
                    split = str.split("\\(");
                }
                else if(start.equals(new String("*")))
                {
                    split = str.split("\\*");
                }
                else if(start.equals(new String("/")))
                {
                    split = str.split("\\/");
                }
                else{
                    split = str.split(start);
                }
                s = split;
                replace = 0;
                if(s[0].charAt(0)>=48 && s[0].charAt(0)<=57)
                {
                    for(int j=0;j<s[0].length();j++)
                    {
                        replace = replace*10 + s[0].charAt(j)-48;
                    }
                    a[num] =replace;
                    num++;
                }
                str = s[1];
            }

        }
        for(int i=0;i<4;i++)
        {
            System.out.println(a[i]);
        }
    }
}
