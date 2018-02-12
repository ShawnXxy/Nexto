package site.shawnxxy.nexto;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import site.shawnxxy.nexto.data.TaskContract;

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
		int idIndex = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
		int descriptionIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
		int priorityIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY);

		cursor.moveToPosition(position);

		final int id = cursor.getInt(idIndex);
		String description = cursor.getString(descriptionIndex);
		int priority = cursor.getInt(priorityIndex);

		holder.itemView.setTag(id);
		holder.taskDescriptionView.setText(description);

		String priorityString = "" + priority;
		holder.priorityView.setText(priorityString);

		GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
		int priorityColor = getPriorityColor(priority);
		priorityCircle.setColor(priorityColor);
	}

	private int getPriorityColor(int priority) {
		int priorityColor = 0;
		switch (priority) {
			case 1:
				priorityColor = ContextCompat.getColor(context, R.color.materialRed);
				break;
			case 2:
				priorityColor = ContextCompat.getColor(context, R.color.materialOrange);
				break;
			case 3:
				priorityColor = ContextCompat.getColor(context, R.color.materialYellow);
				break;
			default:
				break;
		}
		return priorityColor;
	}

	@Override
	public int getItemCount() {
		if (cursor == null) {
			return 0;
		}
		return cursor.getCount();
	}

	/**
	 *  swaps the old cursor with a newly udpated one
	 * @param c
	 * @return
	 */
	public Cursor swapCursor(Cursor c) {
		if (cursor == c) {
			return null; // nothing updated
		}
		Cursor temp = cursor;
		this.cursor = c;

		if (c != null) {
			this.notifyDataSetChanged();
		}
		return temp;
	}
}
