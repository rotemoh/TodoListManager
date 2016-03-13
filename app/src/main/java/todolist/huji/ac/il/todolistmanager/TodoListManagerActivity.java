package todolist.huji.ac.il.todolistmanager;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import java.util.List;

public class TodoListManagerActivity extends AppCompatActivity {
    Context thisContext;
    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        public ArrayList<String> myItemArray;

        public MyAdapter(Context context, ArrayList<String> items) {
            super(context, R.layout.activity_todo_list_manager, items);
            this.context = context;
            this.myItemArray = items;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if(convertView == null){
                //We must create a View:
                convertView = inflater.inflate(R.layout.row, parent, false);
            }
            TextView textViewRow = (TextView)convertView.findViewById(R.id.textViewRow);
            textViewRow.setText(myItemArray.get(position));
            if (position % 2 == 0) {
                textViewRow.setTextColor(Color.BLUE);
            }
            else {
                textViewRow.setTextColor(Color.RED);
            }
            return convertView;
        }

    }
    public ArrayList<String> myItemArray = new ArrayList<>();
    public MyAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        adapter = new MyAdapter(getApplicationContext(), myItemArray);
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
                inviteBuilder.setTitle(myItemArray.get(position));
                Button deleteBtn = (Button) bodyView.findViewById(R.id.deleteBtn);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myItemArray.remove(position);
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
            EditText edtNewItem = (EditText) findViewById(R.id.edtNewItem);
            myItemArray.add(edtNewItem.getText().toString());
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}


