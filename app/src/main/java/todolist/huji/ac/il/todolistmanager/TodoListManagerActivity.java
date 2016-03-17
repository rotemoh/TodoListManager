package todolist.huji.ac.il.todolistmanager;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListManagerActivity extends AppCompatActivity {
    Context thisContext;
    final static int REQUEST_CODE_ADD_MENU = 80;
    final static String CALL_ITEM_PREF = "Call ";

    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        public ArrayList<String> myItemArray;
        public ArrayList<Date> myDueDateArray;

        public MyAdapter(Context context, ArrayList<String> items, ArrayList<Date> dueDates) {
            super(context, R.layout.activity_todo_list_manager, items);
            this.context = context;
            this.myItemArray = items;
            this.myDueDateArray = dueDates;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if(convertView == null){
                //We must create a View:
                convertView = inflater.inflate(R.layout.row, parent, false);
            }
            TextView txtTodoTitle = (TextView)convertView.findViewById(R.id.txtTodoTitle);
            txtTodoTitle.setText(myItemArray.get(position));
            TextView txtTodoDueDate = (TextView)convertView.findViewById(R.id.txtTodoDueDate);
            Date dueDate = myDueDateArray.get(position);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(dueDate);
            txtTodoDueDate.setText(formattedDate);
            Date today = new Date();
            if (today.after(dueDate)) {
                txtTodoTitle.setTextColor(Color.RED);
            }
            else {
                txtTodoTitle.setTextColor(Color.BLUE);
            }
            return convertView;
        }

    }
    public ArrayList<String> myItemArray = new ArrayList<>();
    public ArrayList<Date> myDueDateArray = new ArrayList<>();

    public MyAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        adapter = new MyAdapter(getApplicationContext(), myItemArray, myDueDateArray);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog inviteBuilder = new Dialog(thisContext);
                LayoutInflater inflater = (LayoutInflater)
                        thisContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View bodyView = inflater.inflate(R.layout.dialog_body, null);
                inviteBuilder.setContentView(bodyView);
                String itemData = adapter.myItemArray.get(position);
                inviteBuilder.setTitle(itemData);

                Button deleteBtn = (Button) bodyView.findViewById(R.id.menuItemDelete);
                Button callBtn = (Button) bodyView.findViewById(R.id.menuItemCall);

                if (itemData.startsWith(CALL_ITEM_PREF)) {
                    final String itemCallNumber = itemData.substring(itemData.indexOf(":") + 1);
                    callBtn.setText(itemData);
                    callBtn.setVisibility(View.VISIBLE);
                    callBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent dial = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse("tel:" + itemCallNumber));
                            startActivity(dial);
                            //inviteBuilder.dismiss(); //?
                        }
                    });
                }
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.myItemArray.remove(position);
                        adapter.myDueDateArray.remove(position);
                        adapter.notifyDataSetChanged();
                        inviteBuilder.dismiss();
                    }
                });
                inviteBuilder.show();

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_action) {
            Intent addMenuIntent = new Intent(TodoListManagerActivity.this, AddNewTodoItemActivity.class);
            startActivityForResult(addMenuIntent, REQUEST_CODE_ADD_MENU);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_MENU) {
            if (data.hasExtra("newItemData")) {
                String newItemData = data.getExtras().getString("newItemData");
                Date newItemDueDate = (Date)data.getSerializableExtra("newItemDueDate");
                adapter.myItemArray.add(newItemData);
                adapter.myDueDateArray.add(newItemDueDate);
                adapter.notifyDataSetChanged();
            }
        }
    }
}


