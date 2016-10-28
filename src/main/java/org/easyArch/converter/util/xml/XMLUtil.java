package org.easyArch.converter.util.xml;/**
 * Description : 
 * Created by YangZH on 16-10-27
 *  下午11:29
 */

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Description :
 * Created by code4j on 16-10-27
 * 下午11:29
 */

public class XMLUtil {
    private static SAXReader reader = new SAXReader();
    private Document document = null;
    private Element root = null;
    private JSONObject object = new JSONObject();

    public XMLUtil(InputStream is) {
        try {
            document = reader.read(is);
            root = document.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XMLUtil(String xml_path) {
        try {
            InputStream is = new FileInputStream(xml_path);
            document = reader.read(is);
            root = document.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(){
        Iterator<Element> elementIterator = root.elementIterator();
        while (elementIterator.hasNext()) {
            Element element = elementIterator.next();
            element.element("property");

        }
    }
}
