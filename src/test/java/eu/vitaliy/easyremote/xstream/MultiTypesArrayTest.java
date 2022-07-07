package eu.vitaliy.easyremote.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import eu.vitaliy.easyremote.dto.param.Param;
import eu.vitaliy.easyremote.dto.param.ParamNested;
import eu.vitaliy.easyremote.dto.param.ParamNestedNested;
import org.testng.annotations.Test;

public class MultiTypesArrayTest {

    @Test
    public void xstreamTest() {
        //given
        Object[] testData = new Object[] {
                new Param(new ParamNested(new ParamNestedNested("TEST"))),
                1,
                "TEST-STRING"
        };

        XStream xStream = createXStream(testData);

        //when
        String result = xStream.toXML(testData);

        //then
        System.out.println(result);

    }

    private XStream createXStream(Object[] testData) {
        XStream xStream = new XStream(new DomDriver());
        for (Object item: testData) {
            xStream.alias(item.getClass().getName(), item.getClass());
        }


        return xStream;
    }
}
