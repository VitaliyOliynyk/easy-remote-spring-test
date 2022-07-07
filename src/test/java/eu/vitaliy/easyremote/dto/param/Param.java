package eu.vitaliy.easyremote.dto.param;

public class Param {
    private ParamNested paramNested;

    public Param(ParamNested paramNested) {
        this.paramNested = paramNested;
    }

    public ParamNested getParamNested() {
        return paramNested;
    }
}
