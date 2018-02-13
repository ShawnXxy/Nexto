package site.shawnxxy.nexto;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import site.shawnxxy.nexto.data.TaskContract;

public class AddTaskActivity extends AppCompatActivity {

	// keep track of a tasks selected priority
	private int mPriority;

	RadioButton radio1 = findViewById(R.id.Radio1);
	RadioButton radio2 = findViewById(R.id.Radio2);
	RadioButton radio3 = findViewById(R.id.Radio3);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);

		radio1.setChecked(true);
		mPriority = 1;
	}

	// Add task to database when Add button is clicked
	public void onClickAddTask(View view) {
		//  check if input is empty
		String input = ((EditText) findViewById(R.id.TaskDescriptionEditText)).getText().toString();
		if (input.length() == 0) {
			return;
		}

		// insert to database
		ContentValues contentValues = new ContentValues();
		contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
		contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority);
		Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);
		if (uri != null) {
			Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
		}

		finish();
	}

	public void onPrioritySeleted(View view) {
		if (radio1.isChecked()) {
			mPriority = 1;
		} else if (radio2.isChecked()) {
			mPriority = 2;
		} else if (radio3.isChecked()) {
			mPriority = 3;
		}
	}
}
