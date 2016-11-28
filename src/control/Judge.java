package control;

import entity.Instruction;
import entity.Polynomial;

/**
 * Created by HecateEcho on 2016/11/28.
 */
public class Judge {
    Instruction insob;
    Polynomial polyob;


    public int judgestr(String str){
            if("!".equals(str.substring(0, 1))){
                insob = new Instruction();
                return 1;
            }
            else{
                polyob = new Polynomial();
                return 2;
            }
    }

    public String invoke(int a,String str){
        String strr = "";
        if(a == 1){
            insob.init(str);
            strr = insob.Insjudge();
            if(strr.equals("Wrong command")){
                return strr;
            }
            else if(strr.equals("1")){
                strr = polyob.simplify(insob.insvar,insob.insvaul,insob.num);
            }
            else if(strr.equals("2")){
                strr = polyob.derivative(insob.insd);
            }
            return strr;
        }
        else if(a == 2){
            strr = polyob.isright(str);
            return strr;
        }
        return strr;
    }
}
