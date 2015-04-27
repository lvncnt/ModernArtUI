package course.labs.modernartui.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

import course.labs.modernartui.MainActivity;
import course.labs.modernartui.R;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(),getActivity());
    }

    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    // Execute the SubmitTest
    public void testRun() {

        int delay = 2000;

        // ============= Section One ===============
        // Wait for activity: 'course.labs.modernartui.MainActivity'
        assertTrue("MainActivityTest failed:" +
                        "Section One:" +
                        "MainActivity did not load correctly.",
                solo.waitForActivity(
                        course.labs.modernartui.MainActivity.class, delay));

        // Click on action bar item to delete all items
        solo.clickOnActionBarItem(0x1);
        solo.sleep(delay);


        // ============= Section Two ===============
        // set the Seek Bar Status
        solo.setProgressBar(0, 10);
        solo.setProgressBar(0, 25);
        solo.setProgressBar(0, 50);
        solo.setProgressBar(0, 75);
        solo.setProgressBar(0, 100);
        solo.setProgressBar(0, 60);
        solo.setProgressBar(0, 40);
        solo.setProgressBar(0, 20);
        solo.setProgressBar(0, 0);

        // ============= Section Two ===============
        // blink view
        MainActivity activity = getActivity();

        solo.clickOnView(solo.getView(R.id.textview1));
        solo.clickOnView(solo.getView(R.id.textview3));
        solo.clickOnView(solo.getView(R.id.textview7));


        // ============= Section Three ===============
        // dialog
        Instrumentation.ActivityMonitor activityMonitor =
                getInstrumentation().addMonitor(MainActivity.class.getName(), null, true);
        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(activity, R.id.action_more_information, 0);
        solo.clickOnText(activity.getString(R.string.action_more_information));
        solo.clickOnButton(activity.getString(R.string.dialog_neg));

        assertTrue("MainActivityTest failed:" +
                        "Section One:" +
                        "MainActivity did not load correctly.",
                solo.waitForActivity(
                        course.labs.modernartui.MainActivity.class, delay));

        solo.clickOnActionBarItem(0x1);
        solo.sleep(delay);
        activity = getActivity();
        activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, true);
        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(activity, R.id.action_more_information, 0);

        solo.clickOnText(activity.getString(R.string.action_more_information));
        solo.clickOnButton(activity.getString(R.string.dialog_pos));

        solo.finishOpenedActivities();

    }


}