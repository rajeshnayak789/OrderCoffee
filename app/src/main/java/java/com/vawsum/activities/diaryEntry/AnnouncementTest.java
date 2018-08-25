package java.com.vawsum.activities.diaryEntry;

import android.Manifest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.schibsted.spain.barista.rule.flaky.Repeat;
import com.vawsum.activities.credentials.CredentialInformation;
import com.schibsted.spain.barista.interaction.PermissionGranter;
import com.schibsted.spain.barista.rule.BaristaRule;
import com.vawsum.R;
import com.vawsum.activities.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.closeKeyboard;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by rajap on 10-Jan-18.
 * The testCase situations are :
 * 0 : Refers to no diary selected
 * 1 : Refers to some particular diaries selected
 * 2 : Refers to all diaries selected
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnouncementTest
{
    private CredentialInformation credentials = new CredentialInformation("Correct_Username_Correct_Password", "bob", "bob");
    @Rule
    public BaristaRule<LoginActivity> baristaRule = BaristaRule.create(LoginActivity.class);

    private TestName name = new TestName();

    @Before
    public void setUp()
    {
        Log.i("Info","[START] - Launch Test: " + name.getMethodName());
        baristaRule.launchActivity();
    }

    @After
    public void tearDown()
    {
        Log.i("Info", "[FINISH] - Test: " + name.getMethodName());
    }

    //Sign In
    private void login(String userName,String password)
    {
        //To grant permissions
        PermissionGranter.allowPermissionsIfNeeded(Manifest.permission.MEDIA_CONTENT_CONTROL);
        PermissionGranter.allowPermissionsIfNeeded(Manifest.permission.CAMERA);

        //Type Username
        writeTo(R.id.userNameET, "");
        clickOn(R.id.userNameET);
        writeTo(R.id.userNameET, userName);
        assertDisplayed(userName);
        closeKeyboard();

        //Type Password
        writeTo(R.id.passwordET, "");
        clickOn(R.id.passwordET);
        writeTo(R.id.passwordET, password);
        assertDisplayed(password);
        closeKeyboard();

        //Tap on SIGN IN
        assertDisplayed("SIGN IN");
        clickOn("SIGN IN");
    }
    public void selectAnnouncement()
    {
        clickOn(R.id.post);
        closeKeyboard();
        clickOn(R.id.announcementIV);
    }
    public void postAnnouncement (String announcementMessage)
    {
        clickOn(R.id.postMessageET);
        writeTo(R.id.postMessageET, announcementMessage);
        closeKeyboard();
        clickOn(R.id.actionPost);
    }
    public void announcementTest(String announcementMessage, int testCase)
    {
        if (announcementMessage.equals("") && testCase == 0)                              //Situation where none of the diaries are selected and no announcement is made to them.
        {
            selectAnnouncement();
            postAnnouncement(announcementMessage);
            onView(withText(startsWith("Say something"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }

        else if (announcementMessage.equals("") && testCase == 1)                         //Situation where some of the diaries are selected and no announcement is made to them.
        {
            selectAnnouncement();
            clickOn(R.id.groupSelectionTV);
            assertDisplayed("1 A Hindi");
            clickOn("1 A Hindi");
            assertDisplayed("1 A English Language");
            clickOn("1 A English Language");
            clickOn(R.id.ok);
            postAnnouncement(announcementMessage);
            onView(withText(startsWith("Say something"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }
        else if (announcementMessage.equals("") && testCase == 2)                         //Situation where all the diaries are selected and no announcement is made to them.
        {
            selectAnnouncement();
            clickOn(R.id.groupSelectionTV);
            clickOn(R.id.cbGroupSelectionAll);
            clickOn(R.id.ok);
            postAnnouncement(announcementMessage);
            onView(withText(startsWith("Say something"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }
        else if (testCase == 0 && !announcementMessage.equals(""))                        //Situation where no diaries are selected and an announcement is made.
        {
            selectAnnouncement();
            postAnnouncement(announcementMessage);

            onView(withText(startsWith("Select at least one diary"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }
        else if (testCase == 1 && !announcementMessage.equals(""))       //Situation where some diaries are selected and an announcement is made to all of them.
        {
            selectAnnouncement();
            clickOn(R.id.groupSelectionTV);
            assertDisplayed("1 A Hindi");
            clickOn("1 A Hindi");
            assertDisplayed("1 A English Language");
            clickOn("1 A English Language");
            clickOn(R.id.ok);
            postAnnouncement(announcementMessage);
        }
        else                                               //Situation where all the diaries are selected and an announcement is made to all of them.
        {
            selectAnnouncement();
            clickOn(R.id.groupSelectionTV);
            clickOn(R.id.cbGroupSelectionAll);
            clickOn(R.id.ok);
            postAnnouncement(announcementMessage);
        }
    }

    //No diaries have been selected and no announcement has been typed
    @Repeat(times = 1)
    @Test
    public void announcementBlankDiariesBlankAnnouncement() throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        announcementTest("",0);
        Thread.sleep(2000);
    }

    //Some diaries have been selected but no announcement has been typed
    @Repeat(times = 1)
    @Test
    public void announcementSelectedDiariesBlankAnnouncement() throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        announcementTest("", 1);
        Thread.sleep(2000);
    }

    //All diaries have been selected but no announcement has been typed
    @Repeat(times = 1)
    @Test
    public void announcementAllDiariesBlankAnnouncement() throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        announcementTest("",2);
        Thread.sleep(2000);
    }

    //No diaries have been selected and some announcement has been typed
    @Repeat(times = 1)
    @Test
    public void announcementBlankDiariesWithAnnouncement() throws Exception
    {
        String post = "This is a test announcement";
        login(credentials.getUserName(),credentials.getPassword());
        announcementTest(post,0);
        Thread.sleep(2000);
    }

    //Some diaries have been selected and some announcement has been typed
    @Repeat(times = 1)
    @Test
    public void announcementSelectedDiariesWithAnnouncement() throws Exception
    {
        String post = "This is a test announcement in selected diaries";
        login(credentials.getUserName(),credentials.getPassword());
        announcementTest(post,1);
        Thread.sleep(2000);
    }

    //All diaries have been selected and some announcement has been typed
    @Repeat(times = 1)
    @Test
    public void announcementAllDiariesWithAnnouncement() throws Exception
    {
        String post = "This is a test announcement in all diaries";
        login(credentials.getUserName(),credentials.getPassword());
        announcementTest(post,2);
        Thread.sleep(2000);
    }
}
