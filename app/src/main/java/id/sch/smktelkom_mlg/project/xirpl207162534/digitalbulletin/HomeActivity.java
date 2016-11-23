package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class HomeActivity extends AppCompatActivity {
    TextView em;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ListView lv;
    ArrayList<Channel> arrayChannel = new ArrayList<Channel>();
    ChannelAdapter adapter;
    String name;
    FloatingActionButton fabHome;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lv = (ListView) findViewById(R.id.lview);
        em = (TextView) findViewById(R.id.viewEmail);
        mAuth = FirebaseAuth.getInstance();
        adapter = new ChannelAdapter(this,arrayChannel);
        lv.setAdapter(adapter);
        fabHome = (FloatingActionButton) findViewById(R.id.fabHome);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CreateOrInviteActivity.class);
                startActivity(i);
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference refname = db.getReference("users").child(mAuth.getCurrentUser().getUid()).child("name");
                    refname.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String val = dataSnapshot.getValue(String.class);
                            em.setText("Welcome, "+val);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Snackbar sb = Snackbar.make(getWindow().getDecorView().getRootView(),"Unable To Fetch Data. Check your Connection",Snackbar.LENGTH_SHORT);
                            sb.setAction("Got It", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    return;
                                }
                            });
                            sb.show();
                        }
                    }
                    );
                    DatabaseReference refsub = db.getReference("users").child(mAuth.getCurrentUser().getUid()).child("subscribed");
                    refsub.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            adapter.clear();
                            //Toast.makeText(getApplicationContext(),"Currently Subscribed to "+ String.valueOf(dataSnapshot.getChildrenCount())+" Channels",Toast.LENGTH_SHORT).show();
                            for(DataSnapshot child : dataSnapshot.getChildren()){
                                adapter.add(new Channel(child.getValue().toString(),child.getKey()));
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
        Button lo = (Button) findViewById(R.id.logoutBtn);
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }else{connected = false;}
        if(!connected){
            mAuth.removeAuthStateListener(mAuthListener);
            em.setText("Cannot Fetch data From Server");
            View v = findViewById(R.id.activity_home);
            Snackbar sb = Snackbar.make(v,"Unable To Fetch Data. Check your Connection",Snackbar.LENGTH_INDEFINITE);
            sb.setAction("Got It", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    return;
                }
            });
            sb.show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
