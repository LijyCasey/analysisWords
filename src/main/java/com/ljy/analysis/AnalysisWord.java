package com.ljy.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class AnalysisWord extends Thread {
	private static String[] keys = AnalysisTool.getProperty("tokenizer.dictionary").split("/");
	// 词典中最长词的长度，map中的key的最长长度
	private static final int maxSize = Integer.parseInt(AnalysisTool.getProperty("tokenizer.maxSize"));
	private static Map<String, String> map = new HashMap<String, String>();
	private int index;
	private List<String> files;

	public AnalysisWord(int index, List<String> files) {
		this.index = index;
		this.files = files;
	}

	@Override
	public void run() {
		Map<String, Integer> result = new HashMap<>();
		OPCPackage opcPackage;
		POIXMLTextExtractor extractor = null;
		// 此线程只遍历以index结尾的doc文档
		if (files.size() > 0) {
			for (String tmp : files) {
				if (tmp.endsWith(index + ".docx")) {
					// System.out.println(tmp);
					//
					try {
						opcPackage = POIXMLDocument.openPackage(tmp);
						extractor = new XWPFWordExtractor(opcPackage);
						String text2007 = extractor.getText();
						text2007 = text2007.replaceAll("\\s*", "");
						for (String str : keys) {
							map.put(str, "");
						}
						HashMapTokenizer.tokenizer(map, text2007, maxSize, result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			AnalysisTool.add(result);
			// for (Entry<String, Integer> entry : result.entrySet()) {
			// System.out.println(entry.getKey()+":"+entry.getValue());
			// }
		}

		super.run();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			AnalysisWord a = new AnalysisWord(i, AnalysisTool.traverseFolder(AnalysisTool.getProperty("tokenizer.directory")));
			a.run();
		}
		
		for (Entry<String, Integer> entry : AnalysisTool.getMap().entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
		AnalysisTool.contentToTxt(AnalysisTool.getProperty("output.path"), AnalysisTool.getMap().toString());
	}
}
