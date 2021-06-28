package lench.may.studentorganizer.data;

import android.provider.BaseColumns;

public final class SubjContract {
    private SubjContract(){
    };

    public static final class AddSubj implements BaseColumns {
        public final static String TABLE_NAME = "subjects";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "subjName";
        public final static String COLUMN_PED = "subjPed";
        public final static String COLUMN_ROOM = "subjRoom";
    }
}
