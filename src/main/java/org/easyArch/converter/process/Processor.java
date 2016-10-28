package org.easyArch.converter.process;/**
 * Description : 
 * Created by YangZH on 16-10-27
 *  下午9:16
 */

import org.easyArch.converter.config.Configuration;
import org.easyArch.converter.util.file.FileUtil;
import org.easyArch.converter.util.json.JSONUtil;

import java.io.File;
import java.util.*;

/**
 * Description :
 * Created by code4j on 16-10-27
 * 下午9:16
 */

public class Processor {

    private File srcFile;
    private File dstFile;
    private FileUtil util;
    private List<Map<String,String>> json;


    public Processor(String srcPath) {
        this.srcFile = new File(srcPath);
        this.dstFile = new File(Configuration.getCnf().getString("output"));
        json = new ArrayList<Map<String, String>>();
        util = new FileUtil();
    }


    public Processor process(boolean formatted) throws Exception {
        Configuration config = Configuration.getCnf();
        String mask = config.getString("mask");
        String separator = config.getString("separator");
        List<String> keys = config.getList("keys");
        String content = FileUtil.cat(srcFile.getAbsolutePath());
        String[] lines = content.split("\n");
        boolean hasSelf = keys!=null&&!keys.isEmpty();
        boolean firstLine = true;
        for (String line:lines){
            if (line.isEmpty()){
                break;
            }
            //有自定义且这是第一行，就跳过标准csv格式的meta部分
            if (firstLine&&hasSelf){
                continue;
            }
            String[] blocks = line.split(separator);
            //如果是标准的，或没有进行自定义,则重置keys为标准的keys,并跳过第一行meta的记录
            if (formatted&&!hasSelf&&firstLine){
                keys = Arrays.asList(blocks);
                firstLine = false;
                continue;
            }
            Map<String,String> map = new TreeMap<String, String>();
            int index = 0;
            boolean notEnough = blocks.length>keys.size();
            for (String block:blocks){
                block = block.replace(mask,"");
                if (index >= keys.size()&&notEnough){
                    map.put("$item"+index,block);
                }else{
                    map.put(keys.get(index),block);
                }
                index++;
            }
            json.add(map);
        }
        System.out.println("数据共："+json.size()+"条");
        return this;
    }

    public File disk() throws Exception {
        return  FileUtil.vim(dstFile.getAbsolutePath(), JSONUtil.listToJson(json));
    }

    public String string(){
        return JSONUtil.listToJson(json);
    }

    public static void main(String[] args) throws Exception {
        Configuration.getCnf().setString("separator",",");
        Configuration.getCnf().setString("mask","`");
        Configuration.getCnf().setString("output","/home/code4j/58daojia/test/data.json");
        Processor process = new Processor("/home/code4j/58daojia/test/data.csv");
        process.process(true).disk();
        System.out.println(process.string());
//        Configuration.getCnf().init(System.getProperty("user.dir")+"/src/main/resources/setup.xml");
    }
}
