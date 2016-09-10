package com.flow.analysis;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by madong on 16-9-10.
 */
public class Analysis {

    private String dicPath;

    public Analysis(String dicPath) {
        this.dicPath = dicPath;
        //加载词典
        UserDefineLibrary.loadFile(UserDefineLibrary.FOREST,new File(dicPath));
    }

    /**
     * 分词
     * @param word
     * @return
     */
    public List<Term> analysis(String word){

        Result result = DicAnalysis.parse(word,UserDefineLibrary.FOREST);

        return result.getTerms();
    }

    /**
     * 获取自定义分词
     * @param termList
     * @return
     */

    public List<String> lookupDefineWord(List<Term> termList){


        List<String> list = termList.parallelStream().filter(term -> term.getNatureStr().equalsIgnoreCase("userDefine")).map(t -> t.getRealName()).collect(Collectors.toList());

        return list;
    }

    /**
     * 获取所有地区分词
     * @param termList
     * @return
     */
    public List<String> lookupAddressWord(List<Term> termList){
        List<String> list = termList.parallelStream().filter(term -> term.getNatureStr().equalsIgnoreCase("ns")).map(t -> t.getRealName()).collect(Collectors.toList());
        return list;
    }
}
