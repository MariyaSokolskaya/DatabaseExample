package packagecom.databaseexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 555 on 14.03.2016.
 */
public class OpenHelper extends SQLiteOpenHelper {
   public static final String DATADASE_NAME = "debt.db";
   public static final int DATABASE_VERSION = 1;
    public static final String TABEL_NAME = "dept";
    public static final String ID_COLUMN = "_id";
    public static final String  FIO_COLUMN= "fio";
   public static final String MONEY_COLUMN = "money";
   public static final String TELEPHONE_COLUMN = "teiephone";
    public static final String DATE_COLUMN = "date_in";
    public static final String DEBT_CREDIT_COLUMN = "dept_or_create";


    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public OpenHelper (Context context){
        super(context,  DATADASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = " CREATE TABLE "+ TABEL_NAME+ " ("+
                ID_COLUMN+ " integer primary key autoincrement, "+
                FIO_COLUMN+" text, "+MONEY_COLUMN+" real, "+
                TELEPHONE_COLUMN+" text not null, "+DATE_COLUMN+ " text, "+DEBT_CREDIT_COLUMN + " integer);";
                db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABEL_NAME);
        onCreate(db);

    }
}
