package com.mybatis.example.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.sf.shiva.oms.csm.utils.ValidityUtils;



/**
 * 描述：校验工具类
 */
public class MailnoUtils {

	public static void main(String[] args) {
		List<String> sfNumber = getSFWabillNumber(30);
		System.out.println(sfNumber);
//		wabillNumbers(1);
	}
	
	public static List<String> wabillNumbers(int n){
		
		String[] strrtStr = { "111", "121", "122", "124", "126", "127", "128", "129", "130", "222", "223", "224",
				"226", "333", "334", "336", "337", "980", "981", "982", "983", "984", "985", "986", "987", "988",
				"989", "967", "935", "936", "937", "938", "939", "940", "941", "942", "943", "944", "945", "946",
				"947", "948", "949", "140", "141", "142", "143", "144", "145", "146", "147", "148", "149", "338",
				"339", "340", "341", "342", "343", "344", "345", "346", "347" };
		String nof3 = "";
		for (int i = 0;i< strrtStr.length;i++) {
			nof3 = strrtStr[i];
			System.out.println("前3位包含则返回:"+nof3);
		}
		nof3 = "SF"+nof3;
		System.out.println("前3位包含则返回:"+nof3);
		
		Set<String> set = new HashSet<String>();
		List<String> list = new ArrayList<String>();
		
		n = 10;
		for (int i = 0; i < n; i++) {
			// 随机一个小数
			Double random = Math.random();
//			 System.out.println(random);
			if (random.toString().length() >= 17) {
				String str = random.toString().substring(3, 13);
				System.err.println(str);
				set.add(str);
			}
		}
		System.err.println(set);
		
		list.add(nof3);
		
		Iterator<String> it = set.iterator();
		int count = 0;
		while (it.hasNext()) {
			String str = String.valueOf(it.next());

			char s = genCheckCode(str.substring(1, 9));

			if (s == str.charAt(9)) {
				//System.out.println("校验通过的运单号:" + str);
				count = count + 1;
				list.add(str);
			}
		}
		
		return list;
	}
	public static List<String> getSFWabillNumber(int n) {
		//不重复字符串
		Set<String> set = new HashSet<String>();
		//运单号
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < n; i++) {
			// 随机一个小数
			Double random = Math.random();
//			 System.out.println(random);
			if (random.toString().length() >= 16) {
				String str = random.toString().substring(2, 14);
//				System.err.println(str);
				set.add(str);
			}
		}
		 System.out.println("12位数字集合：" + set);
		// 遍历数字集合
		Iterator<String> it = set.iterator();
		int count = 0;
		while (it.hasNext()) {
			String str = String.valueOf(it.next());
			/*
			 * System.out.println(str); boolean bagNo = checkIsBagNo(str); boolean childNo =
			 * checkIfChild(str); boolean blockCode = checkIsBlockCode(str); boolean cageNo
			 * = checkIsCageNo(str); boolean packageNo = checkIsPackageNo(str); boolean
			 * vehicleNo = checkIsVehicleNo(str); boolean waybillNoOrBagNo =
			 * checkIsWaybillNoOrBagNo(str);
			 */
			String[] strrtStr = { "111", "121", "122", "124", "126", "127", "128", "129", "130", "222", "223", "224",
					"226", "333", "334", "336", "337", "980", "981", "982", "983", "984", "985", "986", "987", "988",
					"989", "967", "935", "936", "937", "938", "939", "940", "941", "942", "943", "944", "945", "946",
					"947", "948", "949", "140", "141", "142", "143", "144", "145", "146", "147", "148", "149", "338",
					"339", "340", "341", "342", "343", "344", "345", "346", "347" };
			
			//
//			for (String str1 : strrtStr) {
//				if (str1.contains(str)) {
//					System.err.println("运单号中存在数组中数字，则返回："+str);
//					continue;
//				}
//				System.out.print("运单号中不包含数组中数字："+str);
//			}

			if (str.length() == 12 && str != null) {
				System.err.println(str.substring(3, 11));
				//校验3-11位长度是否符合
				char s = genCheckCode(str.substring(3, 11));
				//
				System.err.println(""+str.charAt(11));
				if (s == str.charAt(11)) {
					System.out.println("校验通过的运单号:" + str);
					count = count + 1;
					list.add(str);
				}
			}
		}
		System.out.println(count + " 个运单");
		// System.out.println("集合：" + set.size());
		return list;

	}

	public static String getAwb(String head, String middle) {
		StringBuffer sb = new StringBuffer();
		char c = genCheckCode(middle);
		sb.append(head).append(middle).append(c);
		return sb.toString();
	}

	/**
	 * 预留单号位数合法规则校验 判断传入的运单号(12位、15位)是否合法
	 * 
	 * @param bno (预留单号)
	 * @return
	 */
	public static boolean validate(String bno) {
		
//		  if ((bno == null) || (bno.length() != 12)) { return false; }
//		  
//		  char c = genCheckCode(bno.substring(3, 11)); return c == bno.charAt(11);
		 
//		 return WaybillNoUtils.isWaybillNo(bno, false);
		try {
			return ValidityUtils.validity(bno);
		} catch (Exception e) {
			return true;
		}

	}

	/**
	 * 校验是否是运单号或者是否是袋号
	 * 
	 * @param bno
	 * @return
	 */
	public static boolean checkIsWaybillNoOrBagNo(String bno) {
		return validate(bno) || checkIsBagNo(bno);
	}

	/*
	 * public static void main(String[] args) { boolean no =
	 * checkIsWaybillNoOrBagNo("232752908874"); System.out.println(no); }
	 */
	/**
	 * 校验是否是子单
	 * 
	 * @param waybillNo
	 * @return
	 */
	public static boolean checkIfChild(String waybillNo) {
		if (validate(waybillNo)) {
			if (waybillNo.startsWith("00") || (waybillNo.startsWith("841")) || (waybillNo.startsWith("842"))
					|| (waybillNo.startsWith("843")) || (waybillNo.startsWith("844")) || (waybillNo.startsWith("845"))
					|| (waybillNo.startsWith("846")) || (waybillNo.startsWith("847")) || (waybillNo.startsWith("848"))
					|| (waybillNo.startsWith("849")) || (waybillNo.startsWith("850")) || (waybillNo.startsWith("860"))
					|| (waybillNo.startsWith("861")) || (waybillNo.startsWith("862")) || (waybillNo.startsWith("863"))
					|| (waybillNo.startsWith("864")) || (waybillNo.startsWith("865"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验是否是包号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIsPackageNo(String str) {
		if ((str == null) || (str.length() == 0)) {
			return false;
		}
		if ((str.startsWith("111")) || (str.startsWith("126")) || (str.startsWith("128")) || (str.startsWith("129"))
				|| (str.startsWith("130")) || (str.startsWith("980")) || (str.startsWith("981"))
				|| (str.startsWith("982")) || (str.startsWith("983")) || (str.startsWith("984"))
				|| (str.startsWith("985")) || (str.startsWith("986")) || (str.startsWith("987"))
				|| (str.startsWith("988")) || (str.startsWith("989")) || (str.startsWith("935"))
				|| (str.startsWith("936")) || (str.startsWith("937")) || (str.startsWith("938"))
				|| (str.startsWith("939")) || (str.startsWith("940")) || (str.startsWith("941"))
				|| (str.startsWith("942")) || (str.startsWith("943")) || (str.startsWith("944"))
				|| (str.startsWith("945")) || (str.startsWith("946")) || (str.startsWith("947"))
				|| (str.startsWith("948")) || (str.startsWith("949")) || (str.startsWith("967"))) {
			return true;
		}
		return false;
	}

	/**
	 * 校验是否是笼号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIsCageNo(String str) {
		if ((str == null) || (str.length() == 0)) {
			return false;
		}
		if ((str.startsWith("222")) || (str.startsWith("223")) || (str.startsWith("224")) || (str.startsWith("226"))) {
			return true;
		}
		return false;
	}

	/**
	 * 校验是否是封车码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIsBlockCode(String str) {
		if ((str == null) || (str.length() == 0)) {
			return false;
		}
		if ((str.startsWith("121")) || (str.startsWith("122")) || (str.startsWith("124")) || (str.startsWith("127"))
				|| (str.startsWith("140")) || (str.startsWith("141")) || (str.startsWith("142"))
				|| (str.startsWith("143")) || (str.startsWith("144")) || (str.startsWith("145"))
				|| (str.startsWith("146")) || (str.startsWith("147")) || (str.startsWith("148"))
				|| (str.startsWith("149")) || (str.startsWith("135")) || (str.startsWith("136"))
				|| (str.startsWith("137")) || (str.startsWith("138"))) {
			return true;
		}
		return false;
	}

	/**
	 * 校验是否是车标号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIsVehicleNo(String str) {
		if ((str == null) || (str.length() == 0)) {
			return false;
		}
		if ((str.startsWith("333")) || (str.startsWith("334")) || (str.startsWith("336")) || (str.startsWith("337"))
				|| (str.startsWith("338")) || (str.startsWith("339")) || (str.startsWith("340"))
				|| (str.startsWith("341")) || (str.startsWith("342")) || (str.startsWith("343"))
				|| (str.startsWith("344")) || (str.startsWith("345")) || (str.startsWith("346"))
				|| (str.startsWith("347"))) {
			return true;
		}
		return false;
	}

	/**
	 * 校验是否是袋号 第4位到第8位不能包含A、B、C、D、I、O、L字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIsBagNo(String str) {
		if ((str == null) || (str.length() != 8)) {
			return false;
		}
		if (Pattern.compile("[A,B,C,D,I,O,L]+").matcher(str.substring(3, 8)).find()) {
			return false;
		}
		if ((str.startsWith("696")) || (str.startsWith("709")) || (str.startsWith("SFE"))) {
			return true;
		}
		return false;
	}

	/**
	 * 计算检验位
	 * 
	 * @param js
	 * @return
	 */
	private static char genCheckCode(String js) {
		if ((js == null) || (js.length() != 8)) {
			return ' ';
		}

		char s0 = js.charAt(7);
		char s1 = js.charAt(6);
		char s2 = js.charAt(5);
		char s3 = js.charAt(4);
		char s4 = js.charAt(3);
		char s5 = js.charAt(2);
		char s6 = js.charAt(1);
		char s7 = js.charAt(0);

		int A0 = s0 - '0';
		int A1 = s1 - '0';
		int A2 = s2 - '0';
		int A3 = s3 - '0';
		int A4 = s4 - '0';
		int A5 = s5 - '0';
		int A6 = s6 - '0';
		int A7 = s7 - '0';

		int P0 = A0 * 1;
		int P1 = A1 * 3;
		int P2 = A2 * 5;
		int P3 = A3 * 7;
		int P4 = A4 * 9;
		int P5 = A5 * 11;
		int P6 = A6 * 13;
		int P7 = A7 * 15;

		int Q0 = P0 / 10 + (P0 - 10 * (P0 / 10));
		int Q1 = P1 / 10 + (P1 - 10 * (P1 / 10));
		int Q2 = P2 / 10 + (P2 - 10 * (P2 / 10));
		int Q3 = P3 / 10 + (P3 - 10 * (P3 / 10));
		int Q4 = P4 / 10 + (P4 - 10 * (P4 / 10));
		int Q5 = P5 / 10 + (P5 - 10 * (P5 / 10));
		int Q6 = P6 / 10 + (P6 - 10 * (P6 / 10));
		int Q7 = P7 / 10 + (P7 - 10 * (P7 / 10));

		// 变量Q对应数学公式中的SQ+R
		int Q = Q0 + Q1 + Q2 + Q3 + Q4 + Q5 + Q6 + Q7;
		int X = (Q / 10 + 1) * 10;
		int M = X - Q;
		int CB = M - (M / 10) * 10;

		char CB_ch = String.valueOf(CB).charAt(0);

		return CB_ch;
	}

}
