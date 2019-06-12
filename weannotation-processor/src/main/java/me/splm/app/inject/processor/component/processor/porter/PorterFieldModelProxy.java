package me.splm.app.inject.processor.component.processor.porter;


public class PorterFieldModelProxy {
    private PorterFieldModel fieldOfActivity;
    private PorterFieldModel fieldOfObject;
    private PorterFieldModel valueOfLayoutIdModel;
    private PorterFieldModel valueOfViewIDModel;
    private PorterFieldModel valueOfAbsNameModel;

    public PorterFieldModel getFieldOfActivity() {
        return fieldOfActivity;
    }

    public void setFieldOfActivity(PorterFieldModel fieldOfActivity) {
        this.fieldOfActivity = fieldOfActivity;
    }

    public PorterFieldModel getFieldOfObject() {
        return fieldOfObject;
    }

    public void setFieldOfObject(PorterFieldModel fieldOfObject) {
        this.fieldOfObject = fieldOfObject;
    }

    public PorterFieldModel getValueOfLayoutIdValueModel() {
        return valueOfLayoutIdModel;
    }

    public void setValueOfLayoutIdModel(PorterFieldModel valueOfLayoutIdModel) {
        this.valueOfLayoutIdModel = valueOfLayoutIdModel;
    }

    public void setValueOfViewIdModel(PorterFieldModel valueOfViewIDModel){
        this.valueOfViewIDModel=valueOfViewIDModel;
    }

    public PorterFieldModel getValueOfAbsNameModel() {
        return valueOfAbsNameModel;
    }

    public void setValueOfAbsNameModel(PorterFieldModel valueOfAbsNameModel) {
        this.valueOfAbsNameModel = valueOfAbsNameModel;
    }
}
