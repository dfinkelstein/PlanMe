package com.planme.planme;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * List adapter for Task items
 *
 * Created by Danny Finkelstein on 4/6/2016.
 */
public class TasksAdapter extends ArrayAdapter<TasksDB> {

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        CheckBox checkComplete;
        TextView date;
        TextView description;
        TextView location;
    }

    public TasksAdapter(Context context, List<TasksDB> objects) {
        super(context, R.layout.item_task, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TasksDB task = getItem(position);

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
            viewHolder.checkComplete = (CheckBox) convertView.findViewById(R.id.checkBoxComplete);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textName);
            viewHolder.date = (TextView) convertView.findViewById(R.id.textDate);
            viewHolder.description = (TextView) convertView.findViewById(R.id.textDescription);
            viewHolder.location = (TextView) convertView.findViewById(R.id.textLocation);
            convertView.setTag(viewHolder);

            viewHolder.checkComplete.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    TasksDB task = (TasksDB) cb.getTag();
                    if (cb.isChecked()) {
                        task.setStatus(TasksDB.completionStatus.Completed);
                        viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        task.setStatus(TasksDB.completionStatus.New);
                        viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(task.getName());

        SimpleDateFormat df = new SimpleDateFormat("ccc MMM d hh:mm a", Locale.US);
        viewHolder.date.setText(df.format(task.getEndDate()));

        viewHolder.description.setText(task.getDescription());

        if (task.getLocation() != null) {
            viewHolder.location.setText(task.getLocation().getProvider());
        } else {
            viewHolder.location.setText(getContext().getString(R.string.no_location));
        }

        viewHolder.checkComplete.setTag(task);

        if (task.getStatus() == TasksDB.completionStatus.Completed ||
                task.getStatus() == TasksDB.completionStatus.Canceled) {
            viewHolder.checkComplete.setChecked(true);
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.checkComplete.setChecked(false);
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return convertView;
    }
}
