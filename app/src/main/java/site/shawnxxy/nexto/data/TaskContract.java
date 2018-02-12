package site.shawnxxy.nexto.data;

import android.provider.BaseColumns;

/**
 * Created by shawn on 2/12/2018.
 */

public class TaskContract {

	public static final class TaskEntry implements BaseColumns {
		public static final String TABLE_NAME = "tasks";
		public static final String COLUMN_DESCRIPTION = "description";
		public static final String COLUMN_PRIORITY = "priority";
	}
}
