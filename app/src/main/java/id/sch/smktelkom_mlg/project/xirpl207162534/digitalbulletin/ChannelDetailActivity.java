package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChannelDetailActivity extends AppCompatActivity {
    TextView title, desc;
    String id;
    ArrayList<Post> arrayPost = new ArrayList<Post>();
    PostAdapter adapter;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);
        title = (TextView) findViewById(R.id.chanName);
        desc = (TextView) findViewById(R.id.chanDesc);
        adapter = new PostAdapter(this,arrayPost);
        lv = (ListView) findViewById(R.id.lvPost);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id",null);
        if(id == null){
            finish();
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbChan = db.getReference("channels").child(id);
        dbChan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    title.setText(dataSnapshot.child("name").getValue().toString());
                    desc.setText(dataSnapshot.child("description").getValue().toString());

                }catch(Exception ex){
                    finish();
                    Toast.makeText(getApplicationContext(),"Channel has been removed from Server.",Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    adapter.clear();
                    for(DataSnapshot postchild : dataSnapshot.child("posts").getChildren()){
                        Log.d("DATA OUTSIDE",String.valueOf(postchild.getChildrenCount()));
                            adapter.add(new Post(postchild.getKey().toString(),postchild.child("name").getValue().toString(),postchild.child("date").getValue().toString()));
                    }
                }catch(Exception ex){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
