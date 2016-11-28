package boundary;
import control.Judge;
import java.util.*;

/**
 * Created by HecateEcho on 2016/11/28.
 */
public class Terminal {
    private static String str;
    private static Scanner inputtemp;

    public static void main(String[] args) {
        Judge con = new Judge();
        inputtemp=new Scanner(System.in);
        str=inputtemp.nextLine();
        while(!str.matches("end")){
            int op = con.judgestr(str);
            System.out.println(con.invoke(op,str));
            str=inputtemp.nextLine();
        }

    }
}
