package analyzer.graphes;

public interface INameValue{

    void setValue(Object value);
    Object getValue();
    String getName();
    Enums.Variables getType();

    boolean IsValueNull();
}