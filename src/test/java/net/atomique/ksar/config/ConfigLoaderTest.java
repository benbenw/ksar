package net.atomique.ksar.config;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;

public class ConfigLoaderTest {

    @Test
    public void loadOsConfig() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        InputStream is = this.getClass().getResourceAsStream("/Linux.xml");
        Assert.assertNotNull(is);
        ConfiG config = (ConfiG) unmarshaller.unmarshal(is);
        Assert.assertNotNull(config);
        OS os = config.getOS();
        Assert.assertNotNull(os);
        OSType osType = os.getOSType();
        Assert.assertNotNull(osType);
        Assert.assertEquals("Linux", osType.getName());
        
        List<Stat> stats = osType.getStat();
        Assert.assertNotNull(stats);
        Assert.assertFalse(stats.isEmpty());
    }
}
