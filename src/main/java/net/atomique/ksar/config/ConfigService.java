package net.atomique.ksar.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.atomique.ksar.OsParsers;

public class ConfigService {

    private static Map<String, ConfiG> configs = new HashMap<>();
    private static Map<String, Itemcolor> columns = new HashMap<>();
    
    public void load() throws Exception {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        
        for (OsParsers parser : OsParsers.values()) {
            InputStream is = this.getClass().getResourceAsStream("/" + parser.name() + ".xml");
            if (is != null) {
                ConfiG config = (ConfiG) unmarshaller.unmarshal(is);
                configs.put(parser.name(), config);
            }
        }
        
        InputStream is = this.getClass().getResourceAsStream("/Config.xml");
        if (is != null) {
            ConfiG config = (ConfiG) unmarshaller.unmarshal(is);
            List<Itemcolor> colors = config.getColors().getItemcolor();
            for (Itemcolor itemcolor : colors) {
                columns.put(itemcolor.getName(), itemcolor);
            }
        }
    }
}
