package java.com.vawsum.activities.diaryEntry;

import android.Manifest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.schibsted.spain.barista.interaction.PermissionGranter;
import com.schibsted.spain.barista.rule.BaristaRule;
import com.schibsted.spain.barista.rule.flaky.Repeat;
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
import com.vawsum.activities.credentials.CredentialInformation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.closeKeyboard;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Krishnendu Mukherjee on 07-FEB-18.
 * The testCase situations are :
 * 0 : Refers to no com.vawsum.test.diary selected
 * 1 : Refers to some particular diaries selected
 * 2 : Refers to all diaries selected
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimplePostTest
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

    public void postMessage(String post)
    {
        clickOn(R.id.postMessageET);
        writeTo(R.id.postMessageET, post);
        closeKeyboard();
        clickOn(R.id.actionPost);
    }
    public void postTest(String post, int testCase)
    {
        if (post.equals("") && testCase == 0)                              //Situation where no post message is selected but all diaries are selected
        {
            clickOn(R.id.post);
            closeKeyboard();
            postMessage(post);
            onView(withText(startsWith("Say something"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }

        else if (post.equals("") && testCase == 1)                         //Situation where no post message is selected but all diaries are selected
        {
            clickOn(R.id.post);
            closeKeyboard();
            clickOn(R.id.groupSelectionTV);
            assertDisplayed("1 A Hindi");
            clickOn("1 A Hindi");
            assertDisplayed("1 A English Language");
            clickOn("1 A English Language");
            clickOn(R.id.ok);
            postMessage(post);
            onView(withText(startsWith("Say something"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }
        else if (post.equals("") && testCase == 2)                        //Situation where no post message is selected but all diaries are selected
        {
            clickOn(R.id.post);
            closeKeyboard();
            clickOn(R.id.groupSelectionTV);
            clickOn(R.id.cbGroupSelectionAll);
            clickOn(R.id.ok);

            postMessage(post);

            onView(withText(startsWith("Say something"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }
        else if (testCase == 0 && !post.equals(""))                       //Choice for selecting no diaries and a valid post message
        {
            clickOn(R.id.post);
            closeKeyboard();
            postMessage(post);
            onView(withText(startsWith("Select at least one com.vawsum.test.diary"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }
        else if (testCase == 1 && !post.equals(""))                      //Choice for selecting some selected diaries and a valid post message
        {
            clickOn(R.id.post);
            closeKeyboard();
            clickOn(R.id.groupSelectionTV);
            assertDisplayed("1 A Hindi");
            clickOn("1 A Hindi");
            assertDisplayed("1 A English Language");
            clickOn("1 A English Language");
            clickOn(R.id.ok);
            postMessage(post);
        }
        else                                          //Situation where all the diaries are selected and a post is made to all of them.
        {
            clickOn(R.id.post);
            closeKeyboard();
            clickOn(R.id.groupSelectionTV);
            clickOn(R.id.cbGroupSelectionAll);
            clickOn(R.id.ok);
            postMessage(post);
        }
    }
    //No diaries have been selected and no message has been typed
    @Repeat(times = 1)
    @Test
    public void simplePostBlankDiariesBlankPost() throws Exception
    {
        //No diaries have been selected and no message has been typed
        login(credentials.getUserName(),credentials.getPassword());
        postTest("",0);
        sleep(2000);
    }

    //Some diaries have been selected but no message has been typed
    @Repeat(times = 1)
    @Test
    public void simplePostSelectedDiariesBlankPost() throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        postTest("",1);
        sleep(2000);
    }

    //All diaries have been selected but no message has been typed
    @Repeat(times = 1)
    @Test
    public void simplePostAllDiariesBlankPost() throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        postTest("",2);
        sleep(2000);
    }

    //No diaries have been selected and some message has been typed for the post
    @Repeat(times = 1)
    @Test
    public void simplePostBlankDiariesInformationPost() throws Exception
    {
        String post = "This is a test announcement";
        login(credentials.getUserName(),credentials.getPassword());
        postTest(post,0);
        sleep(2000);
    }

    //Some diaries have been selected and some message has been typed for the post
    @Repeat(times = 1)
    @Test
    public void simplePostSelectedDiariesInformationPost() throws Exception
    {
        String post = "This is a test post in some selected diaries";
        login(credentials.getUserName(),credentials.getPassword());
        postTest(post,1);
        sleep(2000);
    }

    //All diaries have been selected and some message has been typed for the post
    @Repeat(times = 1)
    @Test
    public void simplePostAllDiariesInformationPost() throws Exception
    {
        String post = "This is a test post in all diaries";
        login(credentials.getUserName(),credentials.getPassword());
        postTest(post,2);
        sleep(2000);
    }
}
