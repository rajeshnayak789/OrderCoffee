package java.com.vawsum.activities.loginLogout;
import android.Manifest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.schibsted.spain.barista.interaction.PermissionGranter;
import com.schibsted.spain.barista.rule.BaristaRule;
import com.schibsted.spain.barista.rule.flaky.Repeat;
import com.vawsum.R;
import com.vawsum.activities.credentials.CredentialInformation;
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
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.closeKeyboard;
import static com.schibsted.spain.barista.interaction.BaristaMenuClickInteractions.clickMenu;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by rajap on 03-Jan-18.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginLogoutTest
{
    //@Rule
    //public final SpoonRule spoon = new SpoonRule();

    @Rule
    public BaristaRule<LoginActivity> baristaRule = BaristaRule.create(LoginActivity.class);
    TestName name = new TestName();

    @Before
    public void setUp()
    {
        Log.i("Info","[START] - Launch Test: " + name.getMethodName());
        baristaRule.launchActivity();
    }

    @After
    public void tearDown()
    {
        Log.i("Info", "[FINISH] - Test: " + LoginLogoutTest.class.getName());
        //baristaRule.getActivityTestRule().finishActivity();
    }

    @Test
    @Repeat(times = 1)
    public void checkLoginBlankUsernameBlankPassword() throws Exception
    {
        CredentialInformation case1 = new CredentialInformation("Correct_Username_Correct_Password", "", "");
        logintest(case1.getUserName(), case1.getPassword());
        onView(withText(startsWith("Enter username"))).
                inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Log.i("Info","Login :- Test Status : " + true);
    }

    @Test
    @Repeat(times = 1)
    public void checkLoginBlankUsernameSomePassword() throws Exception
    {
        CredentialInformation case1 = new CredentialInformation("Blank_Username_Some_Password", "", "");
        logintest(case1.getUserName(), case1.getPassword());
        onView(withText(startsWith("Enter username"))).
                inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Log.i("Info","Login :- Test Status : " + true);
    }

    @Test
    @Repeat(times = 1)
    public void checkLoginSomeUsernameBlankPassword() throws Exception
    {
        CredentialInformation case1 = new CredentialInformation("Blank_Username_Some_Password", "err3335dd", "");
        logintest(case1.getUserName(), case1.getPassword());
        onView(withText(startsWith("Enter password"))).
                inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Log.i("Info","Login :- Test Status : " + true);
    }

    @Test
    @Repeat(times = 1)
    public void checkLoginIncorrectUsernameIncorrectPassword() throws Exception
    {
        CredentialInformation case1 = new CredentialInformation("Correct_Username_Correct_Passwordd", "hqqsqsv2232", "kwiewen333");
        logintest(case1.getUserName(), case1.getPassword());
        onView(withText(startsWith("Incorrect username/password"))).
                inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Log.i("Info","Login :- Test Status : " + true);
    }

    @Test
    @Repeat(times = 1)
    public void checkLoginCorrectUsernameCorrectPassword() throws Exception
    {
        CredentialInformation case1 = new CredentialInformation("Correct_Username_Correct_Passwordd", "rj", "rj");
        logintest(case1.getUserName(), case1.getPassword());
        Log.i("Info","Login :- Test Status : " + true);
        logout_test();
        Log.i("Info","Logout :- Test Status : " + true);
    }

    //Login
    private void logintest(String userName, String password)
    {
        //To grant permissions
        PermissionGranter.allowPermissionsIfNeeded(Manifest.permission.MEDIA_CONTENT_CONTROL);
        PermissionGranter.allowPermissionsIfNeeded(Manifest.permission.CAMERA);

        //Type Username
        clickOn(R.id.userNameET);
        writeTo(R.id.userNameET, userName);
        assertDisplayed(userName);
        closeKeyboard();

        //Type Password
        clickOn(R.id.passwordET);
        writeTo(R.id.passwordET, password);
        assertDisplayed(password);
        closeKeyboard();

        //Tap on SIGN IN
        assertDisplayed("SIGN IN");
        clickOn("SIGN IN");

    }

    //Log Out
    public void logout_test()
    {
        clickOn(R.id.notificationBell);
        clickOn(R.id.home_page);
        clickOn(R.id.post);
        closeKeyboard();
        clickBack();
        clickOn(R.id.writeEmail);
        closeKeyboard();
        clickBack();
        clickMenu(R.id.actionNavigationDrawer);
        clickOn("Help & Setting");
        clickOn("Logout");
    }
}
