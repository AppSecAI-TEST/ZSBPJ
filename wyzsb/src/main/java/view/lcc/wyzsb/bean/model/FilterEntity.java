package view.lcc.wyzsb.bean.model;

import java.io.Serializable;

public class FilterEntity implements Serializable {

    private String key;
    private String value;
    private boolean isSelected;

    public FilterEntity() {
    }

    public FilterEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
