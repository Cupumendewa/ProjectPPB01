package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChannelDetailActivity extends AppCompatActivity {
    TextView title, desc;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);
        title = (TextView) findViewById(R.id.chanName);
        desc = (TextView) findViewById(R.id.chanDesc);
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
                title.setText(dataSnapshot.child("name").getValue().toString());
                desc.setText(dataSnapshot.child("description").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
