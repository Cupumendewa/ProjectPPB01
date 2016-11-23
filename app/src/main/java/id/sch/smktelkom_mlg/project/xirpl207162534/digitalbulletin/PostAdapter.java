package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class PostAdapter extends ArrayAdapter<Post> {
    public Post ps;
    public PostAdapter(Context context, ArrayList<Post> post) {
        super(context, 0, post);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Post post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.postName);
        TextView tvDate = (TextView) convertView.findViewById(R.id.postDate);
        // Populate the data into the template view using the data object
        tvTitle.setText(post.Title);
        tvDate.setText(post.Date);
        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.layoutP);
        ll.setTag(position);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                ps = getItem(position);
                Intent i = new Intent(getContext(),PostDetailActivity.class);
                i.putExtra("channel",ps.channel);
                i.putExtra("id",ps.id);
                i.putExtra("title",ps.Title);
                getContext().startActivity(i);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}