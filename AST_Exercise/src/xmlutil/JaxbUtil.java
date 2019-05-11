package xmlutil;

import java.io.StringReader;
import java.io.StringWriter;
 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
 
import org.apache.log4j.Logger;

public class JaxbUtil {
	private static Logger logger = Logger.getLogger(JaxbUtil.class);
	private static JAXBContext jaxbContext;
	
	//xml转java对象
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(String xml,Class<T> c){
		T t = null;
		try {
			jaxbContext = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			logger.info(e.getMessage());
		}
		return t;
	}
	
	//java对象转xml
	public static String beanToXml(Object obj){
		StringWriter writer = null;
		try {
			jaxbContext=JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			//Marshaller.JAXB_FRAGMENT:是否省略xml头信息,true省略，false不省略
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			//Marshaller.JAXB_FORMATTED_OUTPUT:决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//Marshaller.JAXB_ENCODING:xml的编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			writer = new StringWriter();
			marshaller.marshal(obj, writer);
		} catch (JAXBException e) {
			logger.info(e.getMessage());
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + writer.toString();
	}

}
