package com.ljy.analysis;

import java.util.Map;

public class HashMapTokenizer {

	public static void tokenizer(Map<String,String> map,String text,int maxSize,Map<String, Integer> result){
		int length = text.length();
		for (int i = 0; i < length; i++) {
			int endIdx = i + maxSize;
			if (endIdx > length) {
				endIdx = length;
			}
			// 最大逆序匹配
			for (int j = 0; j < maxSize; j++) {
				String s = text.substring(i, endIdx);
				if (map.get(s) != null) {
					// 跳过匹配过的词
					i = endIdx - 1;
//					System.out.println(s);
					result.put(s, result.get(s)==null?1:result.get(s)+1);
					break;
				} else {
					endIdx -= 1;
					if (endIdx == i) {
						break;
					}
				}
			}

		}
	}
}
