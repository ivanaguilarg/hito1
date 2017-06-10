package es.udc.ipm.aleatorizador.modelo;

import android.content.ContentValues;


/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class Category implements Comparable<Category>{

    private int id = -1;
    private String name;

    public Category(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues getValues(){
        ContentValues cv = new ContentValues();
        if ( id!=-1)
            cv.put(DBS.TableCategory.ID, id);
        cv.put(DBS.TableCategory.CATEGORY_NAME, name);
        return cv;
    }

    @Override
    public int compareTo(Category category) {
        return name.compareToIgnoreCase(category.getName());
    }
}
