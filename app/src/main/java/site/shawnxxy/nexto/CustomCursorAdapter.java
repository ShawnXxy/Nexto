package site.shawnxxy.nexto;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by shawn on 2/11/2018.
 */

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder> {

	// Class variables for the Cursor that holds task data and the Context
	private Cursor cursor;
	private Context context;

	public CustomCursorAdapter(Context context) {
		this.context = context;
	}

	// Creating ViewHolders
	class TaskViewHolder extends RecyclerView.ViewHolder {

		TextView taskDescriptionView;
		TextView priorityView;
		public TaskViewHolder(View view) {
			super(view);
			taskDescriptionView = view.findViewById(R.id.taskDescription);
			priorityView = view.findViewById(R.id.priorityTextView);
		}
	}

	@Override
	public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Inflate teh task_layout to a view
		View view = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
		return new TaskViewHolder(view);
	}

	@Override
	public void onBindViewHolder(TaskViewHolder holder, int position) {
		int idIndex = cursor.getColumnIndex();
	}

	@Override
	public int getItemCount() {
		return 0;
	}
}
