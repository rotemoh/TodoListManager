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
//                Intent data = new Intent();
                data.putExtra("newItemData", edtNewItem.getText().toString());
//                Date dueDate;
//                int day  = datePicker.getDayOfMonth();
//                int month= datePicker.getMonth();
//                int year = datePicker.getYear();

                long dateTime = datePicker.getCalendarView().getDate();
                Date dueDate = new Date(dateTime);

//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                String formatedDate = sdf.format(Calendar.getTime());
//                String formatDate = sdf.format(year, month, day);
//                dueDate = sdf.parse(formatDate);
                //Date to = new Date(year, month, day);
//                data.putExtra("newItemDueDate", datePicker.toString());
                data.putExtra("newItemDueDate", dueDate);
                // Activity finished ok, return the data
                setResult(RESULT_OK, data);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent data = new Intent(); //?
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "AddNewTodoItem Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://todolist.huji.ac.il.todolistmanager/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "AddNewTodoItem Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://todolist.huji.ac.il.todolistmanager/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }
}
