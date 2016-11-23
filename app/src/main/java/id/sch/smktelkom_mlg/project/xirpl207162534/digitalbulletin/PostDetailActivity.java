package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetailActivity extends AppCompatActivity {
    TextView tvcontent, datepost;
    String content, title, channel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Intent i = getIntent();
        channel = i.getStringExtra("channel");
        title = i.getStringExtra("title");
        setTitle(title);
        content = i.getStringExtra("id");
        tvcontent = (TextView) findViewById(R.id.tvContent);
        datepost = (TextView) findViewById(R.id.datePost);
        tvcontent.setText(content);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference("channels").child(channel).child("posts").child(content);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvcontent.setText(dataSnapshot.child("content").getValue().toString());
                datepost.setText("Posted at " + dataSnapshot.child("date").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
