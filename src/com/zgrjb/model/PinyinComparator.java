package com.zgrjb.model;

import java.util.Comparator;

/**
 * 排序用的比较器类
 * @author tk
 *
 */


//
public class PinyinComparator implements Comparator<SortModel>{
	public int compare(SortModel o1, SortModel o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
