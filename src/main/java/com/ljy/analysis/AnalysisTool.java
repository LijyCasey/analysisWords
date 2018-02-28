package com.ljy.analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class AnalysisTool {

	public static Map<String, Integer> finalMap = new HashMap<>();

	private static Properties props;

	static {
		loadProps();
	}

	synchronized static private void loadProps() {
		props = new Properties();
		InputStream in = null;
		try {
			in = AnalysisTool.class.getClassLoader().getResourceAsStream("config.properties");
			 props.load(new InputStreamReader(in, "utf-8"));  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getProperty(String key) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key, defaultValue);
	}

	public static void add(Map<String, Integer> map) {
		synchronized (finalMap) {
			for (Entry<String, Integer> entry : map.entrySet()) {
				finalMap.put(entry.getKey(), finalMap.get(entry.getKey()) == null ? entry.getValue()
						: finalMap.get(entry.getKey()) + entry.getValue());
			}
		}
	}

	public static Map<String, Integer> getMap() {
		return finalMap;
	}

	public static List<String> traverseFolder(String path) {
		List<String> list = new ArrayList<>();
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return null;
			} else {
				for (File file2 : files) {
					String fileName = file2.getAbsolutePath();
					// int index = fileName.charAt(fileName.indexOf(".doc")-1)
					// -'0';
					list.add(fileName);
				}
			}
			return list;
		} else {
			System.out.println("文件不存在!路径是："+path);
			return null;
		}
	}

	public static void contentToTxt(String filePath, String content) {  
        String str = new String(); //原有txt内容  
        String s1 = new String();//内容更新  
        try {  
            File f = new File(filePath);  
            if (f.exists()) {  
            } else {  
                f.createNewFile();// 不存在则创建  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }  
            input.close();  
            s1 += content;  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    }  
}
