package eu.vitaliy.easyremote;

import eu.vitaliy.easyremote.dto.param.Param;
import eu.vitaliy.easyremote.dto.result.Result;

public interface ITestBean {
    String TEST = "TEST";

    Result test(Param param);
}
