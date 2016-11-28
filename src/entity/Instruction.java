package entity;

/**
 * Created by HecateEcho on 2016/11/28.
 */
public class Instruction {
    private String Ins;//the instruction
    public String[] insvar;
    public int[] insvaul;
    public String insd;
    public int num;// the number of the variable

    public void init(String str){
        Ins = str;
        insvar = new String[5];
        insvaul = new int [5];
        num = 0;
    }
    public String Insjudge(){
        if(Ins.matches("!simplify.*")){
            int i = 11;//the start position
            while(i < Ins.length()){
                if("=".equals(Ins.substring(i,i+1))){
                    insvar[num] = Ins.substring(i-1,i);
                    int k = 1;
                    while (i + k < Ins.length() && Ins.substring(i + k, i + k + 1).matches("[0-9]")) {
                        // 查找赋值表达式中的数字，数字可为多位，k为数字长度
                        k++;
                    }
                    insvaul[num] = Integer.parseInt(Ins.substring(i + 1, i + k));
                    num+=1;
                    i+=k;
                    i+=2;
                }
                else{
                    return "Wrong command";
                }
            }
            return "1";
        }
        else if(Ins.matches("!d/d[a-zA-z]")){
            insd = Ins.substring(Ins.length()-1);
            return "2";
        }
        return "Wrong command";
    }
}
