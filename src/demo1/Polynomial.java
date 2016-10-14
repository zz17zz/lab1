package demo1;
import java.util.*;

public class Polynomial {
	static String str;
	static String[] ori;
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		str = s.nextLine();
		String reg1 = ".*simplify.*";//命令1运算
		String reg2 = ".*d/d.*";//命令2求导
		String reg3 = ".*end.*";//结束命令
		while(! str.matches(reg3))
		{
			if(str != null && str.length() > 0){
				if( "!".equals(str.substring(0,1))){
					if (str.matches(reg1)){
						int n = 0;
						int[] p = new int[10];
						List<String> ch = new ArrayList<String>();
						int i2 =0;
						int be = 11;//起始
						while(be < str.length() &&"=".equals(str.substring(be,be+1))){
							n+=1;
							ch.add(str.substring(be-1,be));
							int k = 1;
							while(be+k <str.length() && str.substring(be+k,be+k+1).matches("[0-9]")){
								k++;
							}
							p[i2++] =  Integer.parseInt(str.substring(be+1,be+k));
							be += k;
							be += 2;
						}
						Polynomial tou = new Polynomial(); 
						tou.simplify(n,p,ch);
					}
					else if(str.matches(reg2)){
						String ss = str.substring(str.length()-1);
						Polynomial tot = new Polynomial(); 
						tot.derivative(ss);
					}
					else{
						System.out.println("wrong command");
					}
				}
				else{
					Polynomial tr = new Polynomial();
					tr.expression();
				}
			}
			str = s.nextLine();
		}
	}
	private void  expression(){
			String reg = "[a-zA-Z0-9+*]+";//only the specail charactor
			String reg4 = ".*[a-zA-Z][0-9]+.*";//wrong pattern 1
			String reg5 = ".*[0-9]+[a-zA-Z].*";//wrong pattern 2
			String reg6 = ".*[a-zA-Z]{2,}.*";//wrong pattern 3
			if(str.matches(reg) && ! str.matches(reg4) && !str.matches(reg5) && !str.matches(reg6))
			{
				ori = str.split("\\+");
				System.out.println(str);
			}
			else{
				System.out.println("wrong expression");
			}
	}

	private int  simplify(int n,int[] p,List<String> ch){
		int len = ori.length;//记录存在多少个乘积项
		String[] stst;//每一项元素的记录
		int[] pa = new int[len];//每一项的参数，初始化为1
		Map<String, Integer>[] map = new Map[len];
		String reg7 = "[0-9]*";
		for(int k = 0; k < len; k++){
			pa[k] = 1;
		}
		for(int i = 0;i < len;i++){
			stst = ori[i].split("\\*"); 
			int s_len = stst.length;
			map[i] = new HashMap<String, Integer>();
			for(int j = 0;j < s_len;j++){
				if(stst[j].matches(reg7)){//当前的因数是数字
					pa[i] *= Integer.parseInt(stst[j]);
				}
				else {
					if(map[i].get(stst[j]) == null){
						map[i].put(stst[j], 1);
					}
					else{
						int val = map[i].get(stst[j]);
						map[i].put(stst[j], val+1);
					}
				}
			}
		}
		for(int op = 0;op < n;op++){
			int right = 0;//判断是否存在
			String ttt = ch.get(op);
			for(int op1 = 0;op1 < len;op1++){
				if(map[op1].get(ttt) != null){
					for(int pp = 0; pp < map[op1].get(ttt);pp++){
						pa[op1] *= p[op];
					}
					map[op1].remove(ttt);
					right = 1;
				}
			}
			if(right == 0){
				System.out.println("Error, no variable");
				return -1;
			}
		}
		pri(pa,map);
		return 0;
	}
	private  void derivative(String ss){
		
		int len = ori.length;
		String[] s;//每一项元素的unit
		int[] pa = new int[len];//每一项的参数，初始化为1
		Map<String, Integer>[] map = new Map[len]; 
		String reg7 = "[0-9]*";
		
		for(int k = 0; k < len; k++){
			pa[k] = 1;
		}
		int right = 0;
		for(int i = 0;i < len;i++){
			s = ori[i].split("\\*");
			int s_len = s.length;
			int cc = 0;//求导过程中该项的变元的幂
			map[i] = new HashMap<String, Integer>();
				for(int j = 0;j < s_len;j++){
					if(s[j].matches(reg7)){//当前的因数是数字
						pa[i] *= Integer.parseInt(s[j]);
					}
					else{
						if(map[i].get(s[j]) == null){
							map[i].put(s[j], 1);
						}
						else{
							int val = map[i].get(s[j]);
							map[i].put(s[j], val+1);
						}
						if(ss.equals(s[j])){
							cc+=1;
							map[i].put(s[j], cc);
							right = 1;
						}
					}
				}
				if(! map[i].containsKey(ss)){
					pa[i] = 0;
				}
			if(cc != 0){
				pa[i] *= cc;
				int val = map[i].get(ss);
				if(val == 1){//若幂是1则直接删除这个元素
					map[i].remove(ss);
				}
				else{
				map[i].put(ss, val-1);
				}
			}	
		}
		pri(pa,map);
		if(right == 0){
			System.out.println("Error, no variable");
		}
	} 
	private boolean compare(Map<String,Integer> map1,Map<String,Integer> map2){
		if(( map1.size() <1) && ( map2.size() <1)){
			return true;
		}
		else if(map1.size()>0 && map2.size()>0){
			for(String key : map1.keySet()){
				if(map2.get(key) != map1.get(key)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	private void pri(int [] p,Map<String,Integer>[] map){
		for(int ti=0;ti<p.length;ti++){//判断是否存在相同的项以便于合并
			for(int tr = ti+1;tr<p.length;tr++){
				if(compare(map[ti],map[tr])){
					p[ti] += p[tr];
					p[tr] = 0;
				}
			}
		}
		boolean plus = false;
		for(int i = 0;i<p.length;i++){//打印
			boolean mul = false;
			if(p[i] != 0){
				if(plus){
					System.out.print("+");
				}
				if(p[i] != 1 || map[i].isEmpty()){
				System.out.print(p[i]);
				}
				if(map[i].size() >0){
					for(String key : map[i].keySet()){
						for(int j = 0;j< map[i].get(key);j++){
							if( mul ||p[i] != 1){
								System.out.print("*");
							}
							System.out.print(key);
							mul = true;
						}
					}
				}
				plus = true;
			}
		}
	}
}