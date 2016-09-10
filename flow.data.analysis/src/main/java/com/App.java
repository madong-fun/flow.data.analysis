package com;

import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.nlpcn.commons.lang.standardization.WordUtil;
import org.nlpcn.commons.lang.util.WordAlert;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String text = "山东省济宁市任城区廿里铺镇";

        System.out.println(DicAnalysis.parse(text, UserDefineLibrary.FOREST));


        String str = "ａｚ ＡＺ AZ az ０９•" ;
        char[] result = WordAlert.alertStr(str) ;
        System.out.println(new String(result));//az az az az 09·


        WordUtil wordUtil = new WordUtil('1', 'A');
        System.out.println(wordUtil.str2Elements("123中国CHINA456你好!"));
        System.out.println(Arrays.toString(wordUtil.str2Chars("123中国CHINA456你好!")));
        System.out.println(wordUtil.str2Str("123中国CHINA456你好!"));
    }
}
