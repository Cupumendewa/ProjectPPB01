package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email,pass;
    Button submit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = (Button) findViewById(R.id.submitBtn);
        email = (EditText) findViewById(R.id.emailBox);
        pass = (EditText) findViewById(R.id.passBox);
        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setEnabled(false);
                submit.setText("Logging In...");
                login();
            }
        });
    }
    private boolean valid(){
        if(email.getText().toString().equals(""))
        {
            email.setError("Email is Empty");
            return false;
        }
        else if (pass.getText().toString().equals("")){
            pass.setError("Password Is Empty");
            return false;
        }
        else if (pass.getText().toString().length() < 6)
        {
            pass.setError("Password Must Consist More than 6 Characters");
            return false;
        }
        else{
            return true;
        }
    }
    public void login(){
        if (valid()){
            mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Welcome, "+mAuth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"Login Failed :(",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


}
