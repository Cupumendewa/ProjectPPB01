package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewPostActivity extends AppCompatActivity {
    EditText title, content;
    Button submit;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        title = (EditText) findViewById(R.id.newpostTitle);
        content = (EditText) findViewById(R.id.newpostContent);
        submit = (Button) findViewById(R.id.submitNewPost);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  post();
            }
        });
    }

    private boolean valid(){
        if(title.getText().toString().equals(""))
        {
            title.setError("Title is Empty");
            return false;
        }
        else if (content.getText().toString().equals("")) {
            content.setError("Content Is Empty");
            return false;
        }
        else {
            return true;
        }
    }

    private void post(){
        if(valid()){
            int postcount;
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dbref = db.getReference("channels").child(id).child("posts");
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("APP",String.valueOf(dataSnapshot.getChildrenCount()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
