package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Polynomial.
 *
 * @author ShadowErii&zz17zz
 *
 */
public class Polynomial {
	/**str.*/
	private static String str;
	private static String strold;
	/**ori.*/
	private static String[] ori;
	private static Scanner s;
	/**
	 *main.
	 * @param args string
	 */
	public static void main(final String[] args) { // 主函数
		s = new Scanner(System.in);
		str = s.nextLine();
		String reg1 = ".*simplify.*"; // 命令1运算
		String reg2 = ".*d/d.*"; // 命令2求导
		String reg3 = ".*end.*"; // 结束命令
		while (!str.matches(reg3)) {
			if (str != null && str.length() > 0) {
				if ("!".equals(str.substring(0, 1))) { // 给变量赋值
					if (str.matches(reg1)) {
						Polynomial tou = new Polynomial();
						tou.simplify(str,strold);
					} else if (str.matches(reg2)) {
						String ss = str.substring(str.length() - 1);
						Polynomial tot = new Polynomial();
						tot.derivative(ss);
					} else {
						System.out.println("wrong command");
					}
				} else { // 输入表达式，按'+'分割
					Polynomial tr = new Polynomial();
					tr.expression(str);
				}
			}
			str = s.nextLine();
		}
	}

/**
 * expression.
 */
	public void expression(String expre) {
		String reg = "[a-zA-Z0-9+*]+"; // only the specail charactor
		String reg4 = ".*[a-zA-Z][0-9]+.*"; // wrong pattern 1
		String reg5 = ".*[0-9]+[a-zA-Z].*"; // wrong pattern 2
		String reg6 = ".*[a-zA-Z]{2,}.*"; // wrong pattern 3
		if (expre.matches(reg) && !expre.matches(reg4)
				&& !expre.matches(reg5) && !expre.matches(reg6)) {
			strold=str;
			ori = expre.split("\\+");
			System.out.println(expre);
		} else {
			System.out.println("wrong expression");
		}
	}

/**
 * simplify.
 * @param n int
 * @param p int
 * @param ch list
 * @return int
 */
	public static int simplify(String command,String expre) {
		String [] ori=expre.split("\\+");
		int n = 0;
		int righte = 0;
		int[] p = new int[10];
		List<String> ch = new ArrayList<String>();
		int i2 = 0;
		int be = 11; // 起始，去除 " !simplify "
		while (be < command.length()) {
			if("=".equals(command.substring(be, be + 1))){
				n += 1; // 等号个数，即要赋值变量的个数
				ch.add(command.substring(be - 1, be));
				// 记录要赋值的变量
				int k = 1;
				while (be + k < command.length()
						&& command.substring(be + k, be + k + 1).matches("[0-9]")) {
					// 查找赋值表达式中的数字，数字可为多位，k为数字长度
					k++;
				}
				p[i2++] = Integer.parseInt(command.substring(be + 1, be + k));
				// 记录赋给变量的值
				be += k; // 下一轮跳过找到的数字
				be += 2; // 下一轮跳过空格和变量
			}
			else{
				System.out.println("wrong commond");
				righte = 1;
				break;
			}
		}
		if (righte == 1){
			return -1;
		}
		else if(n==0){
			System.out.println(expre);
		}
		else{
			int len = ori.length; // 记录存在多少个用'+'分割后的加和项
			String[] stst; // 每一项元素的记录
			int[] pa = new int[len]; // 每一项的参数，初始化为1
			Map<String, Integer>[] map = new Map[len];
			String reg7 = "[0-9]*";
			for (int k = 0; k < len; k++) {
				pa[k] = 1;
			}
			for (int i = 0; i < len; i++) {
				stst = ori[i].split("\\*");
				int slen = stst.length;
				map[i] = new HashMap<String, Integer>();
				for (int j = 0; j < slen; j++) {
					if (stst[j].matches(reg7)) { // 当前的因数是数字，则进行化简
						pa[i] *= Integer.parseInt(stst[j]);
					} else {
						if (map[i].get(stst[j]) == null) { // 把新的变量添加到哈希表中
							map[i].put(stst[j], 1);
						} else {
							int val = map[i].get(stst[j]); // 把变量出现的次数加一
							map[i].put(stst[j], val + 1);
						}
					}
				}
			}
			for (int op = 0; op < n; op++) { // n为要赋值变量的个数
				int right = 0; // 判断是否存在
				String ttt = ch.get(op); // ch为要赋值的变量
				for (int op1 = 0; op1 < len; op1++) {
					if (map[op1].get(ttt) != null) { // 如果赋值的变量在表达式中出现
						for (int pp = 0; pp < map[op1].get(ttt); pp++) {
							pa[op1] *= p[op];
						}
						map[op1].remove(ttt); // 把已经赋值的变量去除
						right = 1;
					}
				}
				if (right == 0) { // 没有对表达式中的变量赋值
					System.out.println("Error, no variable");
					return -1;
				}
			}
			pri(pa, map);
		}
		return 0;
	}

/**
 * derivative.
 * @param ss string[]
 */
	public void derivative(final String ss) {

		int len = ori.length;
		String[] s; // 每一项元素的unit
		int[] pa = new int[len]; // 每一项的参数，初始化为1
		Map<String, Integer>[] map = new Map[len];
		String reg7 = "[0-9]*";

		for (int k = 0; k < len; k++) {
			pa[k] = 1;
		}
		int right = 0;
		for (int i = 0; i < len; i++) {
			s = ori[i].split("\\*");
			int slen = s.length;
			int cc = 0; // 求导过程中该项的变元的幂
			map[i] = new HashMap<String, Integer>();
			for (int j = 0; j < slen; j++) {
				if (s[j].matches(reg7)) { // 当前的因数是数字
					pa[i] *= Integer.parseInt(s[j]);
				} else {
					if (map[i].get(s[j]) == null) {
						map[i].put(s[j], 1);
					} else {
						int val = map[i].get(s[j]);
						map[i].put(s[j], val + 1);
					}
					if (ss.equals(s[j])) {
						cc += 1;
						map[i].put(s[j], cc);
						right = 1;
					}
				}
			}
			if (!map[i].containsKey(ss)) {
				pa[i] = 0;
			}
			if (cc != 0) {
				pa[i] *= cc;
				int val = map[i].get(ss);
				if (val == 1) { // 若幂是1则直接删除这个元素
					map[i].remove(ss);
				} else {
					map[i].put(ss, val - 1);
				}
			}
		}
		pri(pa, map);
		if (right == 0) {
			System.out.println("Error, no variable");
		}
	}

/**
 * compare.
 * @param map1 map
 * @param map2 map
 * @return boolean
 */
	private static boolean compare(final Map<String, Integer> map1,
			final Map<String, Integer> map2) {
		if ((map1.size() < 1) && (map2.size() < 1)) {
			return true;
		} else if (map1.size() > 0 && map2.size() > 0) {
			for (String key : map1.keySet()) {
				if (map2.get(key) != map1.get(key)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

/**
 * pri.
 * @param p int
 * @param map map
 */
	private static void pri(final int[] p, final Map<String, Integer>[] map) { // 输出结果
		for (int ti = 0; ti < p.length; ti++) { // 判断是否存在相同的项以便于合并，ti小于加和项的长度
			for (int tr = ti + 1; tr < p.length; tr++) {
				if (compare(map[ti], map[tr])) {
					p[ti] += p[tr];
					p[tr] = 0;
				}
			}
		}
		boolean plus = false;
		for (int i = 0; i < p.length; i++) { // 打印
			boolean mul = false;
			if (p[i] != 0) {
				if (plus) {
					System.out.print("+");
				}
				if (p[i] != 1 || map[i].isEmpty()) { // 乘数不为1，或者
					System.out.print(p[i]);
				}
				if (map[i].size() > 0) { // 加和项中的变量个数大于0
					for (String key : map[i].keySet()) {
						for (int j = 0; j < map[i].get(key); j++) {
							if (mul || p[i] != 1) {
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