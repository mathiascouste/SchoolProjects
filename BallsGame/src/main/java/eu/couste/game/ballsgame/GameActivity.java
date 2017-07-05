package eu.couste.game.ballsgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class GameActivity extends Activity {
    public static GameActivity currentGameActivity;
    private boolean returnPressed = false;

    int popupWidth = 400;
    int popupHeight = 600;

    public PopupHandler mHandler = new PopupHandler();

    public class PopupHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case GroupsActivity.CREATE_EXIT_POPUP:
                    ((GameView) findViewById(R.id.game_game_view)).pause();
                    showExitPopup(GameActivity.this);
                    break;
                case GroupsActivity.CREATE_GAMEOVER_POPUP:
                    ((GameView) findViewById(R.id.game_game_view)).pause();
                    showGameOverPopup(GameActivity.this);
                    break;
                case GroupsActivity.CREATE_NEXTLEVEL_POPUP:
                    ((GameView) findViewById(R.id.game_game_view)).pause();
                    showNextLevelPopup(GameActivity.this);
                    break;
            }
        }

        public class GroupsActivity {
            public static final int CREATE_EXIT_POPUP = 1;
            public static final int DISMISS_EXIT_POPUP = 2;
            public static final int CREATE_GAMEOVER_POPUP = 3;
            public static final int DISMISS_GAMEOVER_POPUP = 4;
            public static final int CREATE_NEXTLEVEL_POPUP = 5;
            public static final int DISMISS_NEXTLEVEL_POPUP = 6;
        }
    }

    public GameActivity() {
        currentGameActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public void onBackPressed(){
        mHandler.sendMessage(Message.obtain(mHandler, PopupHandler.GroupsActivity.CREATE_EXIT_POPUP));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    public void showExitPopup(GameActivity context) {
        int w,h;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_exit_game, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        w = context.getWindow().getDecorView().getWidth();
        h = context.getWindow().getDecorView().getHeight();

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, (w - popupWidth) / 2, (h - popupHeight) / 2);

        // Getting a reference to Close button, and close the popup when clicked.
        ((Button) layout.findViewById(R.id.popup_exit_game_resume)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        ((Button) layout.findViewById(R.id.popup_exit_game_restart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                ((GameView) findViewById(R.id.game_game_view)).getGame().createLevel();
            }
        });
        ((Button) layout.findViewById(R.id.popup_exit_game_exit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                exitGame();
            }
        });
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ((GameView) findViewById(R.id.game_game_view)).stopPause();
            }
        });
    }


    public void showGameOverPopup(GameActivity context) {
        int w,h;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_game_over, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        w = context.getWindow().getDecorView().getWidth();
        h = context.getWindow().getDecorView().getHeight();

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, (w - popupWidth) / 2, (h - popupHeight) / 2);

        // Getting a reference to Close button, and close the popup when clicked.
        ((Button) layout.findViewById(R.id.popup_game_over_restart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                ((GameView) findViewById(R.id.game_game_view)).getGame().setLevel(1,1);
                ((GameView) findViewById(R.id.game_game_view)).getGame().createLevel();
            }
        });
        ((Button) layout.findViewById(R.id.popup_game_over_exit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                exitGame();
            }
        });
    }

    public void showNextLevelPopup(GameActivity context) {
        int w,h;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_next_level, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        w = context.getWindow().getDecorView().getWidth();
        h = context.getWindow().getDecorView().getHeight();

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, (w - popupWidth) / 2, (h - popupHeight) / 2);

        // Getting a reference to Close button, and close the popup when clicked.
        ((Button) layout.findViewById(R.id.popup_next_level_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameView) findViewById(R.id.game_game_view)).getGame().nextLevel();
                ((GameView) findViewById(R.id.game_game_view)).getGame().createLevel();
                popup.dismiss();
            }
        });
        ((Button) layout.findViewById(R.id.popup_next_level_replay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameView) findViewById(R.id.game_game_view)).getGame().createLevel();
                popup.dismiss();
            }
        });
    }

    public void exitGame() {
        finish();
    }
}
