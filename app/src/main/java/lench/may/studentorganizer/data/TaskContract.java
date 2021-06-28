package lench.may.studentorganizer.data;

import android.provider.BaseColumns;

public final class TaskContract {
    private TaskContract(){
    };

    public static final class AddTask implements BaseColumns{
        public final static String TABLE_NAME = "tasks";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "taskName";
        public final static String COLUMN_DESCRIPTION = "taskDescription";
    }
}
