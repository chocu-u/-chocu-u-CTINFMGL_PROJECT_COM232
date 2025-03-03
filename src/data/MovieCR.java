package data;

import javafx.beans.property.SimpleStringProperty;

public class MovieCR {
    private final SimpleStringProperty contentratingid;
    private final SimpleStringProperty classification;

    public MovieCR(String crid, String cf){
        this.contentratingid = new SimpleStringProperty(crid);
        this.classification = new SimpleStringProperty(cf);
    }

    public String getContentratingid(){
        return contentratingid.get();
    }
    public String getClassification() {
        return classification.get();
    }
}
