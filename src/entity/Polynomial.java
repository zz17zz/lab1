package entity;
import java.util.*;
import entity.MulItem;
import entity.AddItem;

/**
 * Created by HecateEcho on 2016/11/28.
 */
public class Polynomial {
    private AddItem polyadd;
    private String polyori="";
    private String polyresu="";
    public Map<String, Integer>[] maps;

    public String isright(String str){
        polyori = str;
        if(polyori.matches(".*[a-zA-Z][0-9]+.*")||polyori.matches(".*[0-9]+[a-zA-Z].*")||polyori.matches(".*[a-zA-Z]{2,}.*")||!str.matches("[a-zA-Z0-9+*]+")){
            return "Wrong Expresion";
        }
        else {
            polyadd = new AddItem();
            return polyori;
        }

    }


    public String simplify(String[] var,int[] valu,int n){
        polyadd.startarith(polyori);
        int inslen = var.length;
        int explen = polyadd.map.length;
        int opop = 1;
        for(int i = 0;i < n;i++){
            for (int j = 0;j < explen;j++){
                int temp = MulItem.simplifyitem(var[i],valu[i],polyadd.map[j],polyadd.pra[j]);
                if( temp != -1){
                    polyadd.pra[j] = temp;
                    opop = 0;
                }
            }
        }
        if(opop == 1) {
            return "Error, no variable";
        }
        polyadd.merge();
        polyresu = polyadd.recover();
        return polyresu;
    }


    public String derivative(String var){
        polyadd.startarith(polyori);
        int explen = polyadd.map.length;
        int opop = 1;
            for (int i = 0;i < explen;i++){
                int temp = MulItem.derivativeitem(var,polyadd.map[i],polyadd.pra[i]);
                if( temp != -1){
                    polyadd.pra[i] = temp;
                    opop = 0;
                }
                else{
                    polyadd.pra[i] = 0;
                }
            }
            if(opop == 1){
                return "Error, no variable";
            }
        polyadd.merge();
        polyresu = polyadd.recover();
        return polyresu;
    }
}
