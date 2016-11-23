package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateOrInviteActivity extends AppCompatActivity {
    EditText newChannelName,newChannelId,newChannelDesc, joinChannelId;
    Button createChannelbtn, joinChannelbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_invite);

        newChannelName = (EditText) findViewById(R.id.newChannelName);
        newChannelId = (EditText) findViewById(R.id.newChannelId);
        newChannelDesc = (EditText) findViewById(R.id.newChannelDesc);
        joinChannelId = (EditText) findViewById(R.id.joinChannelId);

        createChannelbtn = (Button) findViewById(R.id.createChannelbtn);
        joinChannelbtn = (Button) findViewById(R.id.joinChannelbtn);

        createChannelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        joinChannelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
            }
        });

    }

    private boolean createValid(){
       if(newChannelId.getText().toString().equals("")){
           newChannelId.setError("The Channel ID is Empty");
           return false;
       }
        else if(newChannelName.getText().toString().equals("")){
           newChannelName.setError("The Channel Name is Empty");
           return false;
       }
        else if(newChannelDesc.getText().toString().equals("")){
           newChannelDesc.setError("The Channel Description is Empty");
           return false;
       }
        else{
           return true;
       }
    }

    private boolean joinValid(){
        if(joinChannelId.getText().toString().equals("")){
            joinChannelId.setError("The ID is Empty");
            return false;
        }
        else{
            return true;
        }
    }

    private void signup(){
        if(createValid()){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser fUser = mAuth.getCurrentUser();

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ddref = db.getReference("channels").child(newChannelId.getText().toString());
            ddref.child("owner").setValue(fUser.getUid().toString());
            ddref.child("name").setValue(newChannelName.getText().toString());
            ddref.child("description").setValue(newChannelDesc.getText().toString());
            DatabaseReference uref = db.getReference("users").child(fUser.getUid().toString()).child("subscribed").child(newChannelId.getText().toString());
            uref.setValue(newChannelName.getText().toString());
            Toast.makeText(getApplicationContext(),newChannelName.getText().toString()+ " has Been Created!",Toast.LENGTH_SHORT);
            finish();
        }
    }

    private void join(){
        if(joinValid()){
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dbref = db.getReference("channels").child(joinChannelId.getText().toString());
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser fUser = mAuth.getCurrentUser();

                    String name = dataSnapshot.child("name").getValue().toString();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference("users").child(fUser.getUid().toString()).child("subscribed");
                    dbref.child(joinChannelId.getText().toString()).setValue(name);
                        Toast.makeText(getApplicationContext(),"Join Success!", Toast.LENGTH_SHORT).show();
                    finish();}
                    catch (Exception ex){
                        Toast.makeText(getApplicationContext(),"Failed to Join!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
