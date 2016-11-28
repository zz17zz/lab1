package entity;
import java.util.*;

/**
 * Created by HecateEcho on 2016/11/28.
 */
public class MulItem {
    public static boolean compare(final Map<String, Integer> map1,final Map<String, Integer> map2) {
        if ((map1.size() < 1) && (map2.size() < 1)) {
            return true;
        }
        else if (map1.size() > 0 && map2.size() > 0) {
            for (String key : map1.keySet()) {
                if (map2.get(key) != map1.get(key)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public static int simplifyitem(String var,int valu,Map<String, Integer> map,int pra){
        if(map.containsKey(var)){
            int temp = map.get(var);
            for(int i = 0;i<temp;i++){
                pra *= valu;
            }
            map.remove(var);
        }
        else
            return -1;
        return pra;
    }
    public static int derivativeitem(String var,Map<String, Integer> map,int pra){
        if(map.containsKey(var)){
            int temp = map.get(var);
            if(temp ==1){
                map.remove(var);
            }
            else{
                pra *= temp;
                map.put(var,temp-1);
            }
        }
        else
            return -1;
        return pra;
    }
}
