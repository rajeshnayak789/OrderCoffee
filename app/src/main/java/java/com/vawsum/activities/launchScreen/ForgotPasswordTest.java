package java.com.vawsum.activities.launchScreen;

import android.Manifest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.schibsted.spain.barista.interaction.PermissionGranter;
import com.schibsted.spain.barista.rule.BaristaRule;
import com.schibsted.spain.barista.rule.flaky.Repeat;
import com.vawsum.R;
import com.vawsum.activities.LoginActivity;
import com.vawsum.activities.credentials.CredentialInformation;
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
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsNot.not;

/*
 * Created by Krishnendu Mukherjee on 12-02-2018.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForgotPasswordTest
{
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

    public void forgotPassword (String phoneNumber)
    {
        clickOn(R.id.forgotPasswordTV);
        assertDisplayed("FORGOT PASSWORD");
        writeTo(R.id.mobileNumberET, phoneNumber);
        assertDisplayed(phoneNumber);
        closeKeyboard();
    }
    @Repeat(times = 1)
    @Test
    public void blankPhoneNumberOk() throws Exception   //Test case for blank number input and then click on the "OK" button
    {
        //To grant permissions
        PermissionGranter.allowPermissionsIfNeeded(Manifest.permission.MEDIA_CONTENT_CONTROL);
        PermissionGranter.allowPermissionsIfNeeded(Manifest.permission.CAMERA);

        assertDisplayed("Forgot Password?");
        forgotPassword("");
        clickOn(R.id.changePwdBtn);
        closeKeyboard();
        assertDisplayed("FORGOT PASSWORD");
        sleep(2000);
    }
    @Repeat(times = 1)
    @Test
    public void blankPhoneNumberCancel() throws Exception     //Test case for blank number input and then click on the "CANCEL" button
    {
        assertDisplayed("Forgot Password?");
        forgotPassword("");
        clickOn(R.id.cancelBtn);
        assertDisplayed("Forgot Password?");     //Back to launch screen
        Thread.sleep(2000);
    }
    @Repeat(times = 1)
    @Test
    public void invalidPhoneNumberOk() throws Exception     //Test case for invalid number input and then click on the "OK" button
    {
        assertDisplayed("Forgot Password?");
        forgotPassword("krishnendu");
        clickOn(R.id.changePwdBtn);
        assertDisplayed("FORGOT PASSWORD");          //The dialog box does not close
        Thread.sleep(2000);
    }
    @Repeat(times = 1)
    @Test
    public void invalidPhoneNumberCancel() throws Exception   //Test case for invalid number input and then click on the "CANCEL" button
    {
        assertDisplayed("Forgot Password?");
        forgotPassword("krishnendu");
        clickOn(R.id.cancelBtn);
        assertDisplayed("Forgot Password?");     //Back to launch screen
        Thread.sleep(2000);
    }
    @Repeat(times = 1)
    @Test
    public void incorrectPhoneNumberOk() throws Exception     //Test case for incorrect number and then click on the "OK" button
    {
        assertDisplayed("Forgot Password?");
        forgotPassword("56935441121");
        clickOn(R.id.changePwdBtn);
        assertDisplayed("Forgot Password?");        //Back to launch screen
        Thread.sleep(2000);
    }
    @Repeat(times = 1)
    @Test
    public void incorrectPhoneNumberCancel() throws Exception      //Test case for incorrect number and then click on the "CANCEL" button
    {
        assertDisplayed("Forgot Password?");
        forgotPassword("5223141465");
        clickOn(R.id.cancelBtn);
        assertDisplayed("Forgot Password?");     //Back to launch screen
        Thread.sleep(2000);
    }
    @Repeat(times = 1)
    @Test
    public void correctPhoneNumberOk() throws Exception               //Test case for correct number and then click on the "OK" button
    {
        assertDisplayed("Forgot Password?");
        forgotPassword("9474830332");
        clickOn(R.id.changePwdBtn);
        sleep(3000);
        onView(withText(startsWith("You will receive an sms with credentials shortly"))).
                inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Thread.sleep(2000);
    }
    @Repeat(times = 1)
    @Test
    public void correctPhoneNumberCancel() throws Exception          //Test case for correct number and then click on the "CANCEL" button
    {
        assertDisplayed("Forgot Password?");
        forgotPassword("9474830332");
        clickOn(R.id.cancelBtn);
        assertDisplayed("Forgot Password?");     //Back to launch screen
        Thread.sleep(2000);
    }
}
