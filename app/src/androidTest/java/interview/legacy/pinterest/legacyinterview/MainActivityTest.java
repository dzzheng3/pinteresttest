package interview.legacy.pinterest.legacyinterview;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import interview.legacy.pinterest.legacyinterview.Utils.MainTestHelperUtil;
import interview.legacy.pinterest.legacyinterview.Utils.RecyclerViewMatcher;
import interview.legacy.pinterest.legacyinterview.Utils.ToolbarUtils;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

// Run all the tests in this file by press the double green arrows
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private Context instrumentationCtx;
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    public static RecyclerViewMatcher withRecyclerView( int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void setup() {

        instrumentationCtx = InstrumentationRegistry.getTargetContext();
    }

    // Run this test by pressing the green arrow next to it in Android Studio
    @Test
    public void ToolbarExistSampleTest() {
        ToolbarUtils.toolbarIsDisplayed();
    }



    @Test
    public void HeaderOnFeedPageTest() {
        String header = mActivityTestRule.getActivity().getString(R.string.app_name);
        Locale current = mActivityTestRule.getActivity().getResources().getConfiguration().locale;
        if (current.getLanguage().equals("en")) {
            Assert.assertEquals("Pinterest Espresso", header);
        } else if (current.getLanguage().equals("es")){
            Assert. assertEquals("Spanish Version", header);
        }

        ToolbarUtils.matchToolbarTitle(header);}
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
    String board;
    @Test
    public void clickFeedPageConfirmIndividulePinPageLoaded() {
        //test feed display loaded
        onView(isRoot()).perform(MainTestHelperUtil.timeDelay(5000));
        RecyclerViewMatcher recyclerViewMatcher = withRecyclerView(R.id.main_recycler_view);
        onView(recyclerViewMatcher.atPosition(0)).check(matches(isDisplayed()));
        RecyclerView mainRecyclerView = (RecyclerView) MainTestHelperUtil.getActivityInstance().findViewById(R.id.main_recycler_view);
        View viewByPosition = mainRecyclerView.getLayoutManager().findViewByPosition(0);
        TextView tvPinBoard = (TextView) viewByPosition.findViewById(R.id.pin_board);
        String pinBoard = tvPinBoard.getText().toString();

        //click item
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //test individul pin detail loaded
        onView(isRoot()).perform(MainTestHelperUtil.timeDelay(5000));
        Activity activityInstance = MainTestHelperUtil.getActivityInstance();
        Assert.assertEquals(activityInstance.getClass().getSimpleName(),"PinDetailActivity");
        RecyclerViewMatcher pin_detail_recycler_view = withRecyclerView(R.id.pin_detail_recycler_view);
        onView(pin_detail_recycler_view.atPosition(0)).check(matches(isDisplayed()));
        board = pinBoard.substring("Board: ".length());
        Assert.assertEquals(board,MainTestHelperUtil.getActivityInstance().getTitle().toString());
    }
    @Test
    public void flowAToBPressBackAPressBackFeedDisplay() {
        //feed display to a
        clickFeedPageConfirmIndividulePinPageLoaded();

        //click item of pin A
        onView(withId(R.id.pin_detail_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Pin   B   detail   page   loads
        Activity activityInstanceB = MainTestHelperUtil.getActivityInstance();
        Assert.assertEquals(activityInstanceB.getClass().getSimpleName(),"PinDetailActivity");
        Assert.assertEquals(board,MainTestHelperUtil.getActivityInstance().getTitle().toString());

        //press back to show A
        pressBack();
        Activity activityInstanceAA = MainTestHelperUtil.getActivityInstance();
        Assert.assertEquals(activityInstanceAA.getClass().getSimpleName(),"PinDetailActivity");

        //press back to show feed display
        pressBack();
        Activity activityInstanceFeed = MainTestHelperUtil.getActivityInstance();
        Assert.assertEquals(activityInstanceFeed.getClass().getSimpleName(),"MainActivity");
    }


}