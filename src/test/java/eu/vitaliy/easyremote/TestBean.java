package eu.vitaliy.easyremote;

import eu.vitaliy.easyremote.dto.param.Param;
import eu.vitaliy.easyremote.dto.result.Result;
import eu.vitaliy.easyremote.dto.result.ResultNested;
import eu.vitaliy.easyremote.dto.result.ResultNestedNested;

public class TestBean implements ITestBean {
    @Override
    public Result test(Param param) {
        return new Result(new ResultNested(new ResultNestedNested(ITestBean.TEST)));
    }
}
