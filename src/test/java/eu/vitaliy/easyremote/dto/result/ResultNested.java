package eu.vitaliy.easyremote.dto.result;

public class ResultNested {
    private ResultNestedNested resultNestedNested;

    public ResultNested(ResultNestedNested resultNestedNested) {
        this.resultNestedNested = resultNestedNested;
    }

    public ResultNestedNested getParamNestedNested() {
        return resultNestedNested;
    }
}
