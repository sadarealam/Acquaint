package io.github.yesalam.acquaint.Pojo;

/**
 * Created by yesalam on 14-06-2017.
 */

public class SpinnerItem {
    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String name;
    String value;

    @Override
    public boolean equals(Object obj) {
        return this.value.equalsIgnoreCase(((SpinnerItem)obj).value);
    }

    public SpinnerItem(String name, String value){
        this.name = name ;
        this.value = value;
    }

    public SpinnerItem(String value){this.value=value;}

    public String getName(){
        return name;
    }

    public String getValue(){
        if(value==null) return name;
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
