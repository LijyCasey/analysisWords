package com.ljy.analysis.test;

import java.util.HashMap;
import java.util.Map;

public class TokenizerDemo {

    private static Map<String, String> map = new HashMap<String, String>();

    //词典中最长词的长度，map中的key的最长长度
    private static final int maxSize = 3;

    public static void main(String[] args) {
        String text = "中国人民共和国首都是北京，中关村在海淀区。";
        int length = text.length();
        for(int i=0; i<length; i++){
            int endIdx = i+maxSize;
            if(endIdx>length){
                endIdx = length;
            }
            //最大逆序匹配
            for(int j=0; j<maxSize; j++){
                String s = text.substring(i, endIdx);
                if(map.get(s)!=null){
                    //跳过匹配过的词（后面会说明跳过匹配词的原因）
                    i=endIdx-1;
                    System.out.println(s);
                    break;
                }else{
                    endIdx-=1;
                    if(endIdx==i){
                        break;
                    }
                }
            }
            
        }
    }
}
