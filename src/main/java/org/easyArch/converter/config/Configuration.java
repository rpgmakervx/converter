package org.easyArch.converter.config;/**
 * Description : 
 * Created by YangZH on 16-10-27
 *  下午11:43
 */

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Description :
 * Created by code4j on 16-10-27
 * 下午11:43
 */

public class Configuration {
    private SAXReader reader = new SAXReader();
    private Document document = null;
    private Element root = null;
    private static Configuration configuration = new Configuration();

    private String mask;
    private String separator;
    private List<String> keys;

    private Configuration(){
    }

    public static Configuration getCnf(){
        return configuration;
    }

    private static Map<String,Object> properties = new HashMap<String, Object>();
    public void init(String path){
        try {
            InputStream is = new FileInputStream(path);
            document = reader.read(is);
            root = document.getRootElement();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(InputStream is){
        try {
            document = reader.read(is);
            root = document.getRootElement();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(){
        Iterator<Element> elementIterator = root.elementIterator();
        while (elementIterator.hasNext()) {
            Element property = elementIterator.next();
            Element key = property.element("key");
            Element value = property.element("value");
            Element props = property.element("props");
            if (props == null||props.elements()==null){
                properties.put(key.getTextTrim(),value.getTextTrim());
            }else{
                Iterator<Element> eleitr = props.elementIterator();
                List<String> keys = new ArrayList<String>();
                while (eleitr.hasNext()){
                    keys.add(eleitr.next().getTextTrim());
                }
                properties.put(key.getTextTrim(),keys);
            }
        }
    }

    public <T> T get(String key,Class<T> clazz){
        return (T) properties.get(key);
    }

    public <T> void set(String key,Object value,Class<T> clazz){
        properties.put(key,value);
    }

    public String getString(String key){
        return get(key,String.class);
    }

    public void setString(String key,String value){
        set(key,value,String.class);
    }
    public List<String> getList(String key){
        return get(key,List.class);
    }

    public void setList(String key,List<String> value){
        set(key,value,List.class);
    }

    @Override
    public String toString(){
        return properties.toString();
    }
}
