package eu.vitaliy.easyremote.dto.param;

public class ParamNested {
    private ParamNestedNested paramNestedNested;

    public ParamNested(ParamNestedNested paramNestedNested) {
        this.paramNestedNested = paramNestedNested;
    }

    public ParamNestedNested getParamNestedNested() {
        return paramNestedNested;
    }
}
