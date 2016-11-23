package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPostActivity extends AppCompatActivity {
    EditText title, content;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        title = (EditText) findViewById(R.id.newpostTitle);
        content = (EditText) findViewById(R.id.newpostContent);
        submit = (Button) findViewById(R.id.submitNewPost);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
