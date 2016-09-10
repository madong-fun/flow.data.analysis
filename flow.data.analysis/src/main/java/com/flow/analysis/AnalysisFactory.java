package com.flow.analysis;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * Created by madong on 16-9-10.
 */
public class AnalysisFactory {

    public final static AnalysisFactory FACTORY = new AnalysisFactory();

    private final static ConcurrentSkipListSet<String> SET = new ConcurrentSkipListSet<String>();

    private AnalysisFactory() {
    }

    private static void initDictory(File file){

        //判断是否以及完成初始化词典
        if (SET.add(file.getName().toUpperCase())){
            UserDefineLibrary.loadFile(UserDefineLibrary.FOREST,file);
        }

    }

    /**
     * 分词
     * @param word
     * @return
     */
    private static List<Term> analysis(String word){

        Result result = DicAnalysis.parse(word, UserDefineLibrary.FOREST);

        return result.getTerms();
    }

    /**
     * 获取自定义分词
     * @param word
     * @param file
     * @return
     */
    public List<String> lookupDefineWord(String word,File file){
        initDictory(file);
        List<Term> termList = analysis(word);
        List<String> list = termList.parallelStream().filter(term -> term.getNatureStr().equalsIgnoreCase("userDefine")).map(t -> t.getRealName()).collect(Collectors.toList());

        return list;
    }
}
