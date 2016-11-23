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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by nerdywoffy on 11/22/16.
 */

public class PostAdapter extends ArrayAdapter<Post> {
    public Post ps;
    public boolean admin = false;
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
        ll.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                int position = (Integer) view.getTag();
                final Post pos = getItem(position);
                DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                FirebaseUser usr = FirebaseAuth.getInstance().getCurrentUser();
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference dbf = db.getReference("channels").child(pos.channel).child("posts").child(pos.id);
                                dbf.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Toast.makeText(getContext(),"Removed!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder prompt = new AlertDialog.Builder(getContext());
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser mUser = mAuth.getCurrentUser();
                final String userUID = mUser.getUid().toString();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference df = db.getReference("channels").child(pos.channel).child("owner");
                df.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue().equals(userUID)){
                            admin = true;

                        }else{
                            admin = false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(admin){
                    prompt.setMessage("Are you Sure want to delete this Post?").setPositiveButton("Yes",dialogClick).setNegativeButton("No",dialogClick).show();
                    return true;
                }else{
                    return false;
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
