package eu.couste.game.ballsgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;

import eu.couste.game.ballsgame.game.Settings;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ((Button) findViewById(R.id.settings_button_apply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSettings();
                finish();
            }
        });
        ((Button) findViewById(R.id.settings_button_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SeekBar ballSpeed = ((SeekBar) findViewById(R.id.settings_seekbar_ballsVelocity));
        SeekBar sensitivity = ((SeekBar) findViewById(R.id.settings_seekbar_sensitivity));
        ballSpeed.setProgress(ballSpeed.getMax()/2);
        sensitivity.setProgress(sensitivity.getMax()/2);
    }

    public void updateSettings() {
        SeekBar ballSpeed = ((SeekBar) findViewById(R.id.settings_seekbar_ballsVelocity));
        SeekBar sensitivity = ((SeekBar) findViewById(R.id.settings_seekbar_sensitivity));
        Settings.getInstance().setBallSpeed(2*(double) ballSpeed.getProgress() / (double) ballSpeed.getMax());
        Settings.getInstance().setSensitivity((double)sensitivity.getProgress()/(double)sensitivity.getMax());

        int moveType = 0;
        int bonusType = 0;
        if(((RadioButton)findViewById(R.id.settings_radio_click)).isChecked()) {
            moveType = Settings.MOVE_CLICK;
        } else if(((RadioButton)findViewById(R.id.settings_radio_touch)).isChecked()) {
            moveType = Settings.MOVE_TOUCH;
        } else if(((RadioButton)findViewById(R.id.settings_radio_incline)).isChecked()) {
            moveType = Settings.MOVE_INCLINE;
        }
        if(((RadioButton)findViewById(R.id.settings_radio_bonus_shake)).isChecked()) {
            bonusType = Settings.BONUS_SHAKE;
        } else if(((RadioButton)findViewById(R.id.settings_radio_bonus_longpress)).isChecked()) {
            bonusType = Settings.BONUS_LONGPRESS;
        } else if(((RadioButton)findViewById(R.id.settings_radio_bonus_scroll)).isChecked()) {
            bonusType = Settings.BONUS_SCROLL;
        }
        Settings.getInstance().setMoveType(moveType);
        Settings.getInstance().setBonusType(bonusType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
