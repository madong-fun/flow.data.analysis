package com.flow.flume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madong on 16-9-10.
 */
public class FlumeApp {

    static Logger logger = LoggerFactory.getLogger("FLUME");
    public static void main(String[] args) throws InterruptedException {
        int i =0;
        while (true){
            logger.error("这是第 {} . 次 发送flume events .",++i);
         //   Thread.sleep(2000);
        }
    }
}
