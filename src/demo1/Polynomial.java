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
	public static void main(final String[] args) { // ������
		s = new Scanner(System.in);
		str = s.nextLine();
		String reg1 = ".*simplify.*"; // ����1����
		String reg2 = ".*d/d.*"; // ����2��
		String reg3 = ".*end.*"; // ��������
		while (!str.matches(reg3)) {
			if (str != null && str.length() > 0) {
				if ("!".equals(str.substring(0, 1))) { // ��������ֵ
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
				} else { // ������ʽ����'+'�ָ�
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
		int be = 11; // ��ʼ��ȥ�� " !simplify "
		while (be < command.length()) {
			if("=".equals(command.substring(be, be + 1))){
				n += 1; // �ȺŸ�������Ҫ��ֵ�����ĸ���
				ch.add(command.substring(be - 1, be));
				// ��¼Ҫ��ֵ�ı���
				int k = 1;
				while (be + k < command.length()
						&& command.substring(be + k, be + k + 1).matches("[0-9]")) {
					// ���Ҹ�ֵ���ʽ�е����֣����ֿ�Ϊ��λ��kΪ���ֳ���
					k++;
				}
				p[i2++] = Integer.parseInt(command.substring(be + 1, be + k));
				// ��¼����������ֵ
				be += k; // ��һ�������ҵ�������
				be += 2; // ��һ�������ո�ͱ���
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
			int len = ori.length; // ��¼���ڶ��ٸ���'+'�ָ��ļӺ���
			String[] stst; // ÿһ��Ԫ�صļ�¼
			int[] pa = new int[len]; // ÿһ��Ĳ�������ʼ��Ϊ1
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
					if (stst[j].matches(reg7)) { // ��ǰ�����������֣�����л���
						pa[i] *= Integer.parseInt(stst[j]);
					} else {
						if (map[i].get(stst[j]) == null) { // ���µı�����ӵ���ϣ����
							map[i].put(stst[j], 1);
						} else {
							int val = map[i].get(stst[j]); // �ѱ������ֵĴ�����һ
							map[i].put(stst[j], val + 1);
						}
					}
				}
			}
			for (int op = 0; op < n; op++) { // nΪҪ��ֵ�����ĸ���
				int right = 0; // �ж��Ƿ����
				String ttt = ch.get(op); // chΪҪ��ֵ�ı���
				for (int op1 = 0; op1 < len; op1++) {
					if (map[op1].get(ttt) != null) { // �����ֵ�ı����ڱ��ʽ�г���
						for (int pp = 0; pp < map[op1].get(ttt); pp++) {
							pa[op1] *= p[op];
						}
						map[op1].remove(ttt); // ���Ѿ���ֵ�ı���ȥ��
						right = 1;
					}
				}
				if (right == 0) { // û�жԱ��ʽ�еı�����ֵ
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
		String[] s; // ÿһ��Ԫ�ص�unit
		int[] pa = new int[len]; // ÿһ��Ĳ�������ʼ��Ϊ1
		Map<String, Integer>[] map = new Map[len];
		String reg7 = "[0-9]*";

		for (int k = 0; k < len; k++) {
			pa[k] = 1;
		}
		int right = 0;
		for (int i = 0; i < len; i++) {
			s = ori[i].split("\\*");
			int slen = s.length;
			int cc = 0; // �󵼹����и���ı�Ԫ����
			map[i] = new HashMap<String, Integer>();
			for (int j = 0; j < slen; j++) {
				if (s[j].matches(reg7)) { // ��ǰ������������
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
				if (val == 1) { // ������1��ֱ��ɾ�����Ԫ��
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
	private static void pri(final int[] p, final Map<String, Integer>[] map) { // ������
		for (int ti = 0; ti < p.length; ti++) { // �ж��Ƿ������ͬ�����Ա��ںϲ���tiС�ڼӺ���ĳ���
			for (int tr = ti + 1; tr < p.length; tr++) {
				if (compare(map[ti], map[tr])) {
					p[ti] += p[tr];
					p[tr] = 0;
				}
			}
		}
		boolean plus = false;
		for (int i = 0; i < p.length; i++) { // ��ӡ
			boolean mul = false;
			if (p[i] != 0) {
				if (plus) {
					System.out.print("+");
				}
				if (p[i] != 1 || map[i].isEmpty()) { // ������Ϊ1������
					System.out.print(p[i]);
				}
				if (map[i].size() > 0) { // �Ӻ����еı�����������0
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