package entity;

import java.util.*;
import entity.MulItem;

/**
 * Created by HecateEcho on 2016/11/28.
 */
public class AddItem {
    public Map<String, Integer>[] map;
    public int[] pra;


    public void startarith(String str){
        String[] tempstr = str.split("\\+");
        map = new Map[tempstr.length];
        pra = new int[tempstr.length];
        for(int i = 0; i< tempstr.length;i++){
            map[i] = new HashMap<String, Integer>();
            String[] strtemp = tempstr[i].split("\\*");
            pra[i] = 1;//store the constant
            for(int j = 0;j < strtemp.length;j++){
                if(strtemp[j].matches("[0-9]*")){
                    pra[i] *=  Integer.parseInt(strtemp[j]);
                }
                else{
                    if (map[i].containsKey(strtemp[j])) {
                        int val = map[i].get(strtemp[j]);
                        map[i].put(strtemp[j], val + 1);
                    }
                    else {
                        map[i].put(strtemp[j], 1);
                    }
                }
            }
        }
    }


    public String recover(){
        String strn = "";
        int len=map.length;
        boolean plus = false;
        for (int i = 0; i < len; i++) {
            boolean mul = false;
            if (pra[i] != 0) {
                if (plus) {
                    strn = strn + "+";
                }
                if (pra[i] != 1 || map[i].isEmpty()) {
                    String s = String.valueOf(pra[i]);
                    strn = strn + s;
                }
                if (map[i].size() > 0) { // not empty
                    for (String key : map[i].keySet()) {
                        for (int j = 0; j < map[i].get(key); j++) {
                            if (mul || pra[i] != 1) {
                                strn = strn + "*";
                            }
                            strn = strn + key;
                            mul = true;
                        }
                    }
                }
                plus = true;
            }
        }
        return strn;
    }

    public void merge(){
        int len = map.length;
        for(int i = 0;i < len-1;i++){
            for(int j = i+1;j<len;j++){
                if(MulItem.compare(map[i],map[j])){
                    pra[i] += pra[j];
                    pra[j] = 0;
                }
            }
        }
    }
}
