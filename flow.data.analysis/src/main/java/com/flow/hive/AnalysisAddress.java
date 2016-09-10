package com.flow.hive;

import com.flow.analysis.Analysis;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

/**
 * Created by madong on 16-9-10.
 */
public class AnalysisAddress extends GenericUDF {

    Logger logger = LoggerFactory.getLogger(AnalysisAddress.class);

    //用于将输入类型转换为目标类型
    private ObjectInspectorConverters.Converter[] converters;

    private Analysis analysis;

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 2) {
            throw new UDFArgumentLengthException(
                    "The function loc(address, dicfile) takes exactly 2 arguments.");
        }

        converters = new ObjectInspectorConverters.Converter[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            //创建一个Converter，可以在evaluate方法中使用它 将参数的原生数据类型转存未Java Strings。
            converters[i] = ObjectInspectorConverters.getConverter(arguments[i],
                    PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }
        // 指定UDF （evaluate函数）返回类型为String
        return PrimitiveObjectInspectorFactory
                .getPrimitiveJavaObjectInspector(PrimitiveObjectInspector.PrimitiveCategory.STRING);
    }

    public Object evaluate(DeferredObject[] arguments) throws HiveException {

        if (arguments[0].get() == null || arguments[1].get() == null) {
            return null;
        }

        String address = (String) converters[0].convert(arguments[0].get());
        String dicPath = (String) converters[1].convert(arguments[1].get());

        return location(address,dicPath);
    }

    public String getDisplayString(String[] children) {
        return "loc(" + children[0] + ", " + children[1] + ")";
    }

    public String location(String address,String dicPath) throws HiveException {

        URL url = this.getClass().getClassLoader().getResource(dicPath);

        if (url == null){
            throw new HiveException("Couldn't find dic file '" + dicPath + "'");
        }

        analysis = new Analysis(url.getFile());

        List<String> list = analysis.lookupDefineWord(analysis.analysis(address));

        if ( list == null || list.isEmpty()){
            return null;
        }

        return list.get(0);
    }

    public static void main(String[] args) throws HiveException {
        String dicpath = "library/default.dic";
        String address = "北京大兴区亦庄经济开发区米拉小镇";
        AnalysisAddress analysisAddress = new AnalysisAddress();
        System.out.println(analysisAddress.location(address,dicpath));
    }
}
