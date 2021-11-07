import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class test1 {
    public String getelementsfromString(String str,int[] num,String sign)
    {
        Pattern p = Pattern.compile("\\d+");
        Matcher m =  p.matcher(str);
        sign = m.replaceAll("").trim();

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
    }
    public test1(){}
    public static void main(String[] args){
        String str = "3+2*(1+3)";
        String regEx = "[^0-9]";
        String regsign = "(\\d+)";
        int[] a = new int[4];
        regEx = new test1().getelementsfromString(str, a,regEx);
        System.out.println(Arrays.toString(a));
        System.out.println(regEx);

        char[] chars = regEx.toCharArray();
        int[] ints = new int[chars.length];
        int[] numints = new int[3];
        int bracketlevel= 0;
        int signlevel = 0;
        int labelnum = 0;
        for(int i=0;i<chars.length;i++)
        {
            switch (chars[i]){
                case '(':
                    bracketlevel ++ ;
                    ints[i] = bracketlevel*10 + signlevel;
                    break;
                case ')':
                    bracketlevel --;
                    ints[i] = bracketlevel*10 + signlevel;
                    break;
                case '*':
                case '/':
                    ints[i] = bracketlevel*10 + signlevel +1;
                    numints[labelnum++] = i ;
                    break;
                default:
                    ints[i] = bracketlevel*10 + signlevel ;
                    numints[labelnum++] = i ;
                    break;
            }
        }
        System.out.println(Arrays.toString(ints));
        System.out.println(Arrays.toString(numints));



    }
}
