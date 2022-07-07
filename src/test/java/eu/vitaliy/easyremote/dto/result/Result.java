package eu.vitaliy.easyremote.dto.result;

public class Result {
    private ResultNested resultNested;

    public Result(ResultNested resultNested) {
        this.resultNested = resultNested;
    }

    public ResultNested getParamNested() {
        return resultNested;
    }
}
