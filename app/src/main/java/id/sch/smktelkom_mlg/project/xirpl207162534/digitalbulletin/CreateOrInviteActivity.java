package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                
            }
        });

        joinChannelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
