package org.jivesoftware.util;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class ZZZZ {
	@Test
	public void tt() throws DocumentException{
		String stanza = "<iq id='0yEwW-28' type='set'><deliverconfirm xmlns='androidpn:iq:deliverconfirm'><uuid>a846f3a0</uuid></deliverconfirm> </iq>";
		
		SAXReader reader = new SAXReader(); 
		 Element root = reader.read(new StringReader(stanza)).getRootElement();
	       
       
        StringBuffer sb = new StringBuffer(); 
        sb.append("通过Dom4j解析XML,并输出数据:\n"); 
       
        sb.append("----------------遍历start----------------\n"); 
        int count = 0;
        //遍历当前元素(在此是根元素)的子元素 
         
        
        for (Iterator i_pe = root.elementIterator(); i_pe.hasNext();) { 
            Element e_pe = (Element) i_pe.next(); 
            for (Iterator i_pe2 = e_pe.elementIterator(); i_pe2.hasNext();){
            	Element e_pe2 = (Element) i_pe2.next(); 
            	
            	System.out.println(e_pe2.getData());
            }
            }
        System.out.println(sb.toString()+count); 
	}

}
