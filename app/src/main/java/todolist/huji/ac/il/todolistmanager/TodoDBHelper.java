package todolist.huji.ac.il.todolistmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class TodoDBHelper extends SQLiteOpenHelper {
    public static final String ID_COL = "_id";
    public static final String TITLE_COL = "title";
    public static final String DUE_COL = "due";
    public static final String DB_ITEM_TBL_NAME = "todo";

    public TodoDBHelper(Context context) {
        super(context, "todo_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table todo (" +
                        ID_COL + " integer primary key autoincrement, " +
                        TITLE_COL + " text, " +
                        DUE_COL + " long);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  DB_ITEM_TBL_NAME);
        this.onCreate(db);
    }

    public void addItemToDB(String newItemData, Date newItemDueDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesToAdd = new ContentValues();
        valuesToAdd.put(TITLE_COL, newItemData);
        valuesToAdd.put(DUE_COL, newItemDueDate.getTime());
        db.insert("todo", null, valuesToAdd);
        db.close();
    }

    public void deleteItemFromDB(String item, Date dueDate){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_ITEM_TBL_NAME, TITLE_COL + "=? and " + DUE_COL + "=?",
                new String[]{item, String.valueOf(dueDate.getTime())});
        db.close();
    }

    public ArrayList<String> getStringListFromDB(String table){
        ArrayList<String> listResult = new ArrayList<>();
        String tableItems = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(tableItems, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return listResult;
        }
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            listResult.add(cursor.getString(1));
            cursor.moveToNext();
        }
        db.close();
        return listResult;
    }

    public ArrayList<Date> getDateListFromDB(String table) {
        ArrayList<Date> listResult = new ArrayList<>();
        String tableItems = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(tableItems, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return listResult;
        }
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String dateStr = cursor.getString(2);
            Date d = new Date(Long.parseLong(dateStr));
            listResult.add(d);
            cursor.moveToNext();
        }
        db.close();
        return listResult;
    }
}
