package com.example.coolweather.util;

import com.example.coolweather.model.City;
import com.example.coolweather.model.Province;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Administrator on 2016/2/21.
 */
class XMLHelper {
    private Document document;
    //private XPath xPath;

    XMLHelper(InputStream is) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            document = builder.parse(is);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    XMLHelper(String path) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            FileInputStream in = new FileInputStream(path);
            document = builder.parse(in);

//                XPathFactory factory = XPathFactory.newInstance();
//                xPath = factoryactory.newXPath();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }


    List<Province> obtainProvinceList() {
        ArrayList<Province> list = new ArrayList<Province>();

        NodeList nodeList = document.getElementsByTagName("State");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            Province province = new Province();
            province.setProvinceName(element.getAttribute("Name"));
            province.setProvinceCode(element.getAttribute("Code"));
            list.add(province);
        }

        return list;
    }

    List<City> obtainCityList(String code) {
        List<City> list = new ArrayList<City>();

        NodeList nodeList = document.getElementsByTagName("State");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (element.getAttribute("Code").equals(code)) {
                list = getCityListFromNodeList(element.getChildNodes());
                break;
            }
        }

        return list;
    }

    private List<City> getCityListFromNodeList(NodeList nodeList) {
        ArrayList<City> list = new ArrayList<City>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node  = nodeList.item(i);
            final String nodeName = node.getNodeName();
            if(nodeName == null || nodeName.isEmpty() || !nodeName.equals("City")) continue;

            Element element = (Element)node;
            City city = new City();
            city.setCityName(element.getAttribute("Name"));
            city.setCityCode(element.getAttribute("Code"));
            list.add(city);
        }
        return list;
    }
}
