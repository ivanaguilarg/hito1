package es.udc.ipm.aleatorizador.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class DAO {

    private DBS mHelper;
    private Context mContext;

    public DAO(Context context) {
        this.mContext = context;
        mHelper = new DBS(mContext);
    }

    private SQLiteDatabase getWriteDB() {
        return mHelper.getWritableDatabase();
    }
    private SQLiteDatabase getReadDB() {
        return mHelper.getReadableDatabase();
    }


    public Category insertCategory(Category category) {
        SQLiteDatabase db = getWriteDB();
        long id = db.insert(
                DBS.TABLE_CATEGORY,
                null,
                category.getValues()
        );
        db.close();
        category.setId((int) id);
        return category;
    }

    public List<Category> getAllCategories() {
        SQLiteDatabase db = getReadDB();
        Cursor c = db.query(
                DBS.TABLE_CATEGORY,
                null,
                null,
                null, null, null,
                "UPPER(" + DBS.TableCategory.CATEGORY_NAME + ") ASC"
        );
        if ( c!= null) {
            c.moveToFirst();
            ArrayList<Category> categories = new ArrayList<>();
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndexOrThrow(DBS.TableCategory.CATEGORY_NAME));
                Category note = new Category(name);
                note.setId( c.getInt( c.getColumnIndexOrThrow( DBS.TableCategory.ID )));
                categories.add(note);
                c.moveToNext();
            }
            c.close();
            db.close();
            return categories;
        } else {
            return null;
        }
    }

    public void deleteCategory(Category category) {
        SQLiteDatabase db = getWriteDB();
        db.delete(
                DBS.TABLE_CATEGORY,
                DBS.TableCategory.ID + " = ? ",
                new String[]{Integer.toString(category.getId())}

        );
        db.close();
    }

    public void updateCategory(Category category) {
        SQLiteDatabase db = getWriteDB();
        db.update(
                DBS.TABLE_CATEGORY,
                category.getValues(),
                DBS.TableCategory.ID + " = ? ",
                new String[]{Integer.toString(category.getId())}
        );
        db.close();
    }
}
