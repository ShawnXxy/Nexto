package site.shawnxxy.nexto;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import site.shawnxxy.nexto.data.TaskContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = MainActivity.class.getSimpleName();
	private static final int TASK_LOADER_ID = 0;

	private CustomCursorAdapter mAdapter;
	RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set the RecyclerView to its corresponding view
		mRecyclerView = findViewById(R.id.taskRecyclerView);
		// Set the layout for the RecyclerView to be a linear layout, which measures and positions items within a RecyclerView into a linear list
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		// Initialize the adapter and attach it to the RecyclerView
		mAdapter = new CustomCursorAdapter(this);
		mRecyclerView.setAdapter(mAdapter);

		// Enable touch behavior
		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
				return false;
			}

			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

			}
		}).attachToRecyclerView(mRecyclerView);

		// Set FAB
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Create a new intent
				Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
				startActivity(addTaskIntent);
			}
		});

		// Ensure a loader is initialized and active
		getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
	}

	/**
	 *      Called when activity paused or restarted
	 */
	@Override
	protected void onResume() {
		super.onResume();
		getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
	}

	/**
	 *  Instantiates and returns a new AsyncTaskLoader with the given ID
	 *
	 *  This loader will return task data as a Cursor or null if an error occurs
	 *
	 * @param id
	 * @param args
	 * @return
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
		return new AsyncTaskLoader<Cursor>(this) {
			// Initialize a Cursor, this will hold all the task data
			Cursor cursor = null;

			@Override
			protected void onStartLoading() {
				if (cursor != null) {
					// Delivers any previously loaded data immediately
					deliverResult(cursor);
				} else {
					// Force a new load
					forceLoad();
				}
			}

			public void deliverResult(Cursor cursor) {
				cursor = cursor;
				super.deliverResult(cursor);
			}

			@Override
			public Cursor loadInBackground() {
				// query and load data
				try {
					return getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI, null, null, null, TaskContract.TaskEntry.COLUMN_PRIORITY);
				} catch (Exception e) {
					Log.e(TAG, "Failed to asynchronously load data.");
					e.printStackTrace();
					return null;
				}
			}

		};
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// update data that the adapter uses to create ViewHolders
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
