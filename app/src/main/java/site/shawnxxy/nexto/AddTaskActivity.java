package site.shawnxxy.nexto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

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

	// Add task to database
	public void onClickAddTask(View view) {

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
