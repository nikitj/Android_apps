package ru.surf.nikita_makarov.jotter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {
    public String TAG = "AddNoteActivity";
    public EditText data1;
    public EditText data2;
    public ImageView colorShow;
    public String themeAdd;
    public String textAdd;
    public int colorAdd;
    private Button button;
    static final int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        data1 = (EditText) findViewById(R.id.editText);
        data2 = (EditText) findViewById(R.id.editText2);
        colorShow = (ImageView) findViewById(R.id.color_show);
        button = (Button) findViewById(R.id.button);
        colorAdd = ContextCompat.getColor(this, R.color.blue);
        colorShow.setBackgroundColor(colorAdd);
        initListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "on ActivityCreate");
        if (data == null) {
            return;
        }
        data1 = (EditText) findViewById(R.id.editText);
        data2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        initListeners();
    }

    private void initListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (data1.getText().length() != 0 & data2.getText().length() != 0 & colorAdd != 0) {
                    themeAdd = data1.getText().toString();
                    textAdd = data2.getText().toString();
                    Log.v(TAG, themeAdd);
                    Log.v(TAG, textAdd);
                    Log.v(TAG, Integer.toString(colorAdd));
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd. MM. yyyy h:mm a", Locale.getDefault());
                    String dateAdd = sdf.format(date);
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("date", dateAdd);
                    intent.putExtra("theme", themeAdd);
                    intent.putExtra("text", textAdd);
                    intent.putExtra("color", colorAdd);
                    setResult(RESULT_CODE, intent);
                    finish();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Fill all fields!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.BOTTOM, 0, 45);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.yellow:
                colorAdd = ContextCompat.getColor(this, R.color.yellow);
                break;
            case R.id.red:
                colorAdd = ContextCompat.getColor(this, R.color.red);
                break;
            case R.id.blue:
                colorAdd = ContextCompat.getColor(this, R.color.blue);
                break;
            case R.id.green:
                colorAdd = ContextCompat.getColor(this, R.color.green);
                break;
            case R.id.violet:
                colorAdd = ContextCompat.getColor(this, R.color.violet);
                break;
            case R.id.pink:
                colorAdd = ContextCompat.getColor(this, R.color.pink);
                break;
            default:
                colorAdd = ContextCompat.getColor(this, R.color.blue);
                break;
        }
        colorShow.setBackgroundColor(colorAdd);
        return true;
    }


}