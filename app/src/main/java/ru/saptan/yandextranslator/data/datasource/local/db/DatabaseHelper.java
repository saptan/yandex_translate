package ru.saptan.yandextranslator.data.datasource.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.saptan.yandextranslator.data.datasource.local.db.schema.Migration_v1;

public class DatabaseHelper extends SQLiteOpenHelper {

    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    private static final String DATABASE_NAME = "translator.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Migration_v1.CREATE_DIRECTIONS);
        db.execSQL(Migration_v1.CREATE_LANGUAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
