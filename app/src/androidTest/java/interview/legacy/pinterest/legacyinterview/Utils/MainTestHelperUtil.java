package interview.legacy.pinterest.legacyinterview.Utils;

import android.app.Activity;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.Collection;
import java.util.Iterator;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by dzzheng3 on 10/26/17.
 *
 * TO help main test
 */

public class MainTestHelperUtil {

    /**
     *  waiting for a network time.
     */
    public static ViewAction timeDelay(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    /**
     * Get current activity
     * @return current activity
     */
    public static Activity getActivityInstance(){
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable(){
            public void run(){
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }
}
