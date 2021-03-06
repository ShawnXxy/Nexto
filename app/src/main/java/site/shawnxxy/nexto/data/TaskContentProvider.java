package site.shawnxxy.nexto.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static site.shawnxxy.nexto.data.TaskContract.TaskEntry.TABLE_NAME;

/**
 * Created by shawn on 2/12/2018.
 */

public class TaskContentProvider extends ContentProvider {

	public static final int TASKS = 100;
	public static final int TASK_WITH_ID = 101;
	private static final UriMatcher sUriMatcher = buildUriMatcher();

	public static UriMatcher buildUriMatcher() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS);
		uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID);
		return uriMatcher;
	}

	private TaskDbHelper mTaskDbHelper;

	@Override
	public boolean onCreate() {
		Context context = getContext();
		mTaskDbHelper = new TaskDbHelper(context);
		return true;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		// get access to database
		final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();
		int match = sUriMatcher.match(uri);
		Cursor returnCursor;

		switch (match) {
			case TASKS:
				returnCursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

		returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return returnCursor;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		// get access to database
		final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
		// identify the match for the tasks directory
		int match = sUriMatcher.match(uri);
		Uri returnUri; // URI to be returned

		switch (match) {
			case TASKS:
				// insert value
				long id = db.insert(TABLE_NAME, null, values);
				if (id > 0) {
					returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
				} else {
					throw new android.database.SQLException("Failed to insert row into " + uri);
				}
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

		return returnUri;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		// get access to database
		final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
		int match = sUriMatcher.match(uri);
		int taskDeleted;

		switch (match) {
			case TASK_WITH_ID:
				String id = uri.getPathSegments().get(1);
				taskDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

		if (taskDeleted != 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return taskDeleted;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}
}
