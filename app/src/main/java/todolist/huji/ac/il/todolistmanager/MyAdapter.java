package todolist.huji.ac.il.todolistmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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