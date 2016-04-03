package todolist.huji.ac.il.todolistmanager;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rotemoh on 16/03/2016.
 */
public class AddNewTodoItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item_list);

        final Intent data = new Intent();
        final EditText edtNewItem = (EditText) findViewById(R.id.edtNewItem);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        Button btnOK = (Button)findViewById(R.id.btnOK);
        Button btnCancel = (Button)findViewById(R.id.btnCancel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.putExtra("newItemData", edtNewItem.getText().toString());

                long dateTime = datePicker.getCalendarView().getDate();
                Date dueDate = new Date(dateTime);

                data.putExtra("newItemDueDate", dueDate);
                // Activity finished ok, return the data
                setResult(RESULT_OK, data);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });

    }
}
