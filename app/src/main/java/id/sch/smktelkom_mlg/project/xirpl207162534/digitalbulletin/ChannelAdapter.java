package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nerdywoffy on 11/22/16.
 */

public class ChannelAdapter extends ArrayAdapter<Channel> {
    public ChannelAdapter(Context context, ArrayList<Channel> channel) {
        super(context, 0, channel);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Channel channel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_channel, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // Populate the data into the template view using the data object
        tvName.setText(channel.Name);
        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.layoutN);
        ll.setTag(position);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Channel ch = getItem(position);
                Log.d("Press",ch.id.toString());
            }
        });
        ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = (Integer) v.getTag();
                Channel ch = getItem(position);

                DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Log.d("APP","Removed");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder prompt = new AlertDialog.Builder(getContext());
                prompt.setMessage("Are you Sure want to delete " + ch.Name + " from your Subscribed List?").setPositiveButton("Yes",dialogClick).setNegativeButton("No",dialogClick).show();
                return true;
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

}
