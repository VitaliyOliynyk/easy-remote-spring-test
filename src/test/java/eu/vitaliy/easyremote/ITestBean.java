package eu.vitaliy.easyremote;

import eu.vitaliy.easyremote.dto.param.Param;
import eu.vitaliy.easyremote.dto.result.Result;

public interface ITestBean {
    String TEST_STRING = "TEST";
    int TEST_INT = 1;

    Result test(Param param);

    Object[] testArray(Param param, int count);
}
