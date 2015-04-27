package course.labs.modernartui;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Main activity of the application
 */
public class MainActivity extends ActionBarActivity {

    private DialogFragment dialogFragment;
    private final static int TAG_ALERT = 0;
    private final static String URL = "http://www.moma.org";
    private final static String CHOOSER_TEXT = "Load " + URL + " with:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<TextView> textViewList = new ArrayList<>(Arrays.asList(
                (TextView) findViewById(R.id.textview1),
                (TextView) findViewById(R.id.textview2),
                (TextView) findViewById(R.id.textview3),
                (TextView) findViewById(R.id.textview4),
                (TextView) findViewById(R.id.textview5),
                (TextView) findViewById(R.id.textview6),
                (TextView) findViewById(R.id.textview7),
                (TextView) findViewById(R.id.textview8),
                (TextView) findViewById(R.id.textview9)));

        final int[] originalColors = new int[textViewList.size()];

        for(int i = 0; i < textViewList.size(); i ++){
            final TextView textView = textViewList.get(i);
            textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Animation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(5);
                    animation.setStartOffset(1);
                    animation.setRepeatMode(Animation.REVERSE);
                    textView.startAnimation(animation);
                }
            });

            originalColors[i] = ((ColorDrawable) textViewList.get(i).getBackground()).getColor();
        }

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progressChange = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressChange = progress;
                for(int i = 0; i < textViewList.size(); i ++){
                    setProgressBasedBackgroundColor(textViewList.get(i), originalColors[i]);
                 }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            private void setProgressBasedBackgroundColor(TextView textView, int originalColor) {
                if(originalColor == getResources().getColor(R.color.lightgrey) ) return;
                float[] hsvColor = new float[3];
                Color.colorToHSV(originalColor, hsvColor);
                hsvColor[0] = hsvColor[0] + progressChange;
                hsvColor[0] = hsvColor[0] % 360;
                textView.setBackgroundColor(Color.HSVToColor(Color.alpha(originalColor), hsvColor));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_more_information) {
            showDialogFragment(TAG_ALERT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogFragment(int dialogID){
        switch (dialogID){
            case TAG_ALERT:
                dialogFragment = new AlertDialogFragment();
                dialogFragment.show(getFragmentManager(), "Alert");
                break;
        }
    }

    /* Set up an intent to open web page */
    public void doPositiveClick(){
        Uri webpage = Uri.parse(URL);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        Intent chooseIntent = Intent.createChooser(intent, CHOOSER_TEXT);

        if(intent.resolveActivity(getPackageManager()) != null ){
            startActivity(chooseIntent);
        }
    }
}
