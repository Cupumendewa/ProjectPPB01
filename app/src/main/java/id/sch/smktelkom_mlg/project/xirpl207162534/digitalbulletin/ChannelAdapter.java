package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.content.Context;
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
        Channel channel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_channel, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // Populate the data into the template view using the data object
        tvName.setText(channel.Name);
        // Return the completed view to render on screen
        return convertView;
    }

}
