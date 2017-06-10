package es.udc.ipm.aleatorizador.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class DBS extends SQLiteOpenHelper {

    private static final int    DB_VERSION  = 1;
    private static final String DB_NAME     = "categories.db";

    public DBS(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public static final String TABLE_CATEGORY  = "category";

    public static final class TableCategory{
        public static final String ID = "categoryId";
        public static final String CATEGORY_NAME = "categoryName";
    }

    private static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + TABLE_CATEGORY + " ( " +
                    TableCategory.ID + " INTEGER NOT NULL PRIMARY KEY, " +
                    TableCategory.CATEGORY_NAME + " TEXT NOT NULL " +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATE_TABLE_CATEGORY);
    }
}

