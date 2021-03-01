package top.anets.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
//需要gson相关的包
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;



public class CommonUtils {
	public static String getHostIP(){

        Enumeration<NetworkInterface> allNetInterfaces = null;
        String resultIP=null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements())
        {
        NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
//        System.out.println(netInterface.getName());
        Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
        while (addresses.hasMoreElements())
        {
        ip = (InetAddress) addresses.nextElement();
        if (ip != null && ip instanceof Inet4Address)
        { 
           if(resultIP==null)
            resultIP= ip.getHostAddress();  
//           System.out.println("本机地址是："+ip.getHostAddress());
        
        } 
        }
        }
          return resultIP;
         
    }
    
	
	
	 public static String map2Xml(Map<String , String> paras) {
	        SortedMap<String, String> paraMap = new TreeMap<String, String>();
	        paraMap.putAll(paras);
	        String xmlStr = "";
	        Set es = paraMap.entrySet();
	        Iterator it = es.iterator();
	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();
	            String k = (String) entry.getKey();
	            String v = (String) entry.getValue();
	            xmlStr += "<" + k + ">" + v + "</" + k + ">";
	        }
//	        xmlStr += "</xml>";
	        return xmlStr;
	    }
	
	 public static Map xml2map(String xmlStr, boolean needRootKey) throws DocumentException {  
	        Document doc = DocumentHelper.parseText(xmlStr);  
	        Element root = doc.getRootElement();  
	        Map<String, Object> map = (Map<String, Object>) xml2map(root);  
	        if(root.elements().size()==0 && root.attributes().size()==0){  
	            return map;  
	        }  
	        if(needRootKey){  
	            //在返回的map里加根节点键（如果需要）  
	            Map<String, Object> rootMap = new HashMap<String, Object>();  
	            rootMap.put(root.getName(), map);  
	            return rootMap;  
	        }  
	        return map;  
	    }  
	 
	 private static Map xml2map(Element e) {  
	        Map map = new LinkedHashMap();  
	        List list = e.elements();  
	        if (list.size() > 0) {  
	            for (int i = 0; i < list.size(); i++) {  
	                Element iter = (Element) list.get(i);  
	                List mapList = new ArrayList();  
	  
	                if (iter.elements().size() > 0) {  
	                    Map m = xml2map(iter);  
	                    if (map.get(iter.getName()) != null) {  
	                        Object obj = map.get(iter.getName());  
	                        if (!(obj instanceof List)) {  
	                            mapList = new ArrayList();  
	                            mapList.add(obj);  
	                            mapList.add(m);  
	                        }  
	                        if (obj instanceof List) {  
	                            mapList = (List) obj;  
	                            mapList.add(m);  
	                        }  
	                        map.put(iter.getName(), mapList);  
	                    } else  
	                        map.put(iter.getName(), m);  
	                } else {  
	                    if (map.get(iter.getName()) != null) {  
	                        Object obj = map.get(iter.getName());  
	                        if (!(obj instanceof List)) {  
	                            mapList = new ArrayList();  
	                            mapList.add(obj);  
	                            mapList.add(iter.getText());  
	                        }  
	                        if (obj instanceof List) {  
	                            mapList = (List) obj;  
	                            mapList.add(iter.getText());  
	                        }  
	                        map.put(iter.getName(), mapList);  
	                    } else  
	                        map.put(iter.getName(), iter.getText());  
	                }  
	            }  
	        } else  
	            map.put(e.getName(), e.getText());  
	        return map;  
	    }





	
	
}
