package org.easyArch.converter.startup;/**
 * Description : 
 * Created by YangZH on 16-10-28
 *  下午1:49
 */

import org.easyArch.converter.config.Configuration;
import org.easyArch.converter.process.Processor;

/**
 * Description :
 * Created by code4j on 16-10-28
 * 下午1:49
 */

public class Converter {

    public static void main(String[] args) throws Exception {
        if (args.length == 1){
            Configuration.getCnf().init(System
                    .getProperty("user.dir") + "/src/main/resources/setup.xml");
        }else if (args.length == 2){
            Configuration.getCnf().init(args[1]);

        }
        Processor processor = new Processor(args[0]);
        processor.process(true).disk();

    }
}
