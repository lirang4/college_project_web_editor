package analyzer.graphes;
public class ParamterItem implements INameValue{

    private String Name;
    private Enums.Variables varType;
    private Object value;

    public ParamterItem(String name, Enums.Variables varType, Object value) {
        Name = name;
        this.varType = varType;
        this.value = value;
    }

    public String getName() {
        return Name;
    }

    public Enums.Variables getType() {
        return varType;
    }

    @Override
    public boolean IsValueNull() {
        return value == null;
    }

    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value){
        this.value = value;

    }
}
