package com.flow.hive;

import com.flow.analysis.AnalysisFactory;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by madong on 16-9-10.
 */
public class Location extends UDF {
    Logger logger = LoggerFactory.getLogger(Location.class);
    private AnalysisFactory factory = AnalysisFactory.FACTORY;

    public String evaluate(String address , String dicPath) throws UDFArgumentLengthException {

        if (address == null || dicPath == null){
            throw new UDFArgumentLengthException(
                    "The function Location(address, dicfile) takes exactly 2 arguments.");
        }

        URL url = this.getClass().getClassLoader().getResource(dicPath);
        logger.info("input dic file url is {}.",url);
        if (url == null){
            if (!new File(dicPath).exists()){
                throw new UDFArgumentLengthException(
                        "The function Location dicfile is not exists.");
            }
        }

        List<String> list = factory.lookupDefineWord(address,new File(dicPath));

        if (list == null || list.isEmpty() ) return null;

        return list.get(0);
    }

    public static void main(String[] args) throws HiveException {
        String dicpath = "library/default.dic";
        String address = "北京大兴区亦庄经济开发区米拉小镇";
        Location analysisAddress = new Location();
        System.out.println(analysisAddress.evaluate(address,dicpath));
    }
}
