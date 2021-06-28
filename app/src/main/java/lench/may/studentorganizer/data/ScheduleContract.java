package lench.may.studentorganizer.data;

import android.provider.BaseColumns;

public final class ScheduleContract {
    private ScheduleContract(){
    };

    public static final class AddLesson implements BaseColumns {
        public final static String TABLE_NAME = "lessons";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DAY = "lessonDay";
        public final static String COLUMN_SUBJ = "subjName";
        public final static String COLUMN_START_H = "lessonStartH";
        public final static String COLUMN_START_M = "lessonStartM";
        public final static String COLUMN_END_H = "lessonEndH";
        public final static String COLUMN_END_M = "lessonEndM";
    }
}
