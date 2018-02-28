package com.ljy.analysis.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import com.ljy.analysis.HashMapTokenizer;

public class Test {
	private static Map<String, String> map = new HashMap<String, String>();

	// 词典中最长词的长度，map中的key的最长长度
	private static final int maxSize = 6;

	public static void main(String[] args) {
		OPCPackage opcPackage;
		POIXMLTextExtractor extractor = null;
		String input = "公共场所/无烟环境/宣传/教育/禁止烟草广告/促销/赞助/包装标识/危害警示/税收/价格/收益/成分管制/信息披露/非法贸易/打假/打私/戒烟/监测/监控";
		String[] inputs = input.split("/");
		Map<String, Integer> result = new HashMap<>();
		try {
			
			opcPackage = POIXMLDocument.openPackage("E:\\政策图\\政策文本\\控烟\\3.docx");
			extractor = new XWPFWordExtractor(opcPackage);
			String text2007 = extractor.getText();
			text2007 = text2007.replaceAll("\\s*", "");
			for(String str:inputs){
				map.put(str, "");
			}
			HashMapTokenizer.tokenizer(map, text2007, maxSize, result);
//			for(String str:inputs){
//				map.put(str, "");
//			}
//			for (int i = 0; i < length; i++) {
//				int endIdx = i + maxSize;
//				if (endIdx > length) {
//					endIdx = length;
//				}
//				// 最大逆序匹配
//				for (int j = 0; j < maxSize; j++) {
//					String s = text2007.substring(i, endIdx);
//					if (map.get(s) != null) {
//						// 跳过匹配过的词（后面会说明跳过匹配词的原因）
//						i = endIdx - 1;
////						System.out.println(s);
//						result.put(s, result.get(s)==null?1:result.get(s)+1);
//						break;
//					} else {
//						endIdx -= 1;
//						if (endIdx == i) {
//							break;
//						}
//					}
//				}
//
//			}
			for (Entry<String, Integer> entry : result.entrySet()) {
	            System.out.println(entry.getKey()+":"+entry.getValue());
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

	}
}
