package girod.anthony.acerestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//Creates the Menu screen where users can view Ace Restaurant food offerings in a scrollview style

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}
