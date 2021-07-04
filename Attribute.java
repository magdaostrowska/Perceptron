import java.util.List;

public class Attribute {

    List<Double> dataLines;
    String irisClass;


    public Attribute(List<Double> dataLines, String irisClassName) {
        this.dataLines = dataLines;
        this.irisClass = irisClassName;
    }

    public void setDataLines(List<Double> dataLines){
        this.dataLines = dataLines;
    }

    public void setIrisClass(String irisClassName){
        this.irisClass = irisClassName;
    }

    public List<Double> getDataLines() {
        return dataLines;
    }

    public String getIrisClass() {
        return irisClass;
    }
}
