package ru.surf.nikita_makarov.jotter.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

import java.util.LinkedList;
import java.util.List;

public final class FeedReaderContract {

    public FeedReaderContract() {
    }

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME_THEME = "theme";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_DATE = "date";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    FeedEntry.COLUMN_NAME_THEME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_COLOR + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static class FeedReaderDBHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Database.db";

        public FeedReaderDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        public long pushNote(String themeForDB, String textForDB,
                                String dateForDB, int colorForDB) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_THEME, themeForDB);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT, textForDB);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, dateForDB);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_COLOR, colorForDB);
            long id = db.insert(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    null,
                    values);
            db.close();
            return id;
        }

        public List<Note> getAllNotes() {
            List<Note> notes = new LinkedList<>();
            String query = "SELECT * FROM " + FeedEntry.TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            Note note;
            if (cursor.moveToFirst()) {
                do {
                    note = new Note();
                    note.id = Integer.parseInt(cursor.getString(0));
                    note.theme = cursor.getString(1);
                    note.text = cursor.getString(2);
                    note.date = cursor.getString(3);
                    note.color = Integer.parseInt(cursor.getString(4));
                    notes.add(note);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return notes;
        }

        public void deleteNote(long id) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(FeedReaderContract.FeedEntry.TABLE_NAME,
                    FeedEntry.COLUMN_ID +" = ?",
                    new String[] { String.valueOf(id) });
            db.close();
        }
    }
}