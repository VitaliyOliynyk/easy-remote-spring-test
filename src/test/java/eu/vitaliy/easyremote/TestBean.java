package eu.vitaliy.easyremote;

import eu.vitaliy.easyremote.dto.param.Param;
import eu.vitaliy.easyremote.dto.result.Result;
import eu.vitaliy.easyremote.dto.result.ResultNested;
import eu.vitaliy.easyremote.dto.result.ResultNestedNested;

public class TestBean implements ITestBean {

    @Override
    public Result test(Param param) {
        return createResult(param);
    }

    private Result createResult(Param param) {
        return new Result(
                new ResultNested(
                        new ResultNestedNested(
                                param.getParamNested().getParamNestedNested().getValue()
                        )
                )
        );
    }

    @Override
    public Object[] testArray(Param param, int count) {
        return new Object[]{
                createResult(param),
                TEST_STRING,
                TEST_INT
        };
    }
}
