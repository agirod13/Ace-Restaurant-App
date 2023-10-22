package girod.anthony.acerestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Creates the Welcome screen and prompts users to press a button to open the Home screen

public class Welcome extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
    }

    // Opens home screen after Order Up! button is pressed
    public void openHome() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
