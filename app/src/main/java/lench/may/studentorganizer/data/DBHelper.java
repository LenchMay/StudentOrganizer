package lench.may.studentorganizer.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import lench.may.studentorganizer.data.TaskContract.AddTask;
import lench.may.studentorganizer.data.SubjContract.AddSubj;
import lench.may.studentorganizer.data.ScheduleContract.AddLesson;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{
    public static final String LOG_TAG = DBHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "StudentOrganizer.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //создание таблицы "tasks"
        String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " + AddTask.TABLE_NAME + " ("
                + AddTask._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AddTask.COLUMN_NAME + " TEXT NOT NULL, " + AddTask.COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(SQL_CREATE_TASKS_TABLE);
        //создание таблицы "subjects"
        String SQL_CREATE_SUBJECTS_TABLE = "CREATE TABLE " + AddSubj.TABLE_NAME + " ("
                + AddSubj._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AddSubj.COLUMN_NAME + " TEXT NOT NULL, " + AddSubj.COLUMN_PED + " TEXT, " + AddSubj.COLUMN_ROOM + " TEXT)";
        db.execSQL(SQL_CREATE_SUBJECTS_TABLE);
        //создание таблицы "lessons"
        String SQL_CREATE_LESSONS_TABLE = "CREATE TABLE " + AddLesson.TABLE_NAME + " ("
                + AddLesson._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AddLesson.COLUMN_DAY + " TEXT NOT NULL, " + AddLesson.COLUMN_SUBJ + " TEXT, "
                + AddLesson.COLUMN_START_H + " INTEGER, " + AddLesson.COLUMN_START_M + " INTEGER, "
                + AddLesson.COLUMN_END_H + " INTEGER, " + AddLesson.COLUMN_END_M + " INTEGER)";
        db.execSQL(SQL_CREATE_LESSONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + AddTask.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + AddSubj.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + AddLesson.TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<String> getAllNames(){
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + AddSubj.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String subjName = cursor.getString(cursor.getColumnIndex("subjName"));
                    list.add(subjName);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            //db.close();
        }
        return list;
    }
}
