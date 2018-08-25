package java.com.vawsum.activities.diary;

import android.Manifest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.schibsted.spain.barista.interaction.PermissionGranter;
import com.schibsted.spain.barista.rule.BaristaRule;
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

import static android.os.SystemClock.sleep;
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

/*
 * Created by Krishnendu Mukherjee on 07-FEB-2018.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateDiaryTest
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

    public void  openCreateDiary()
    {
        clickMenu(R.id.actionNavigationDrawer);
        clickOn("com/vawsum/activities/diary");
        clickOn("Create Diary");
    }
    public void createDiaryTest(String diaryTitle, String diaryDescription)
    {
        if (diaryTitle.equals("") && diaryDescription.equals(""))                             //Situation where no Diary title is given and at the same time no com.vawsum.test.diary description is given.
        {
            openCreateDiary();
            writeTo(R.id.diaryTitleET, "");
            clickOn(R.id.diaryTitleET);
            writeTo(R.id.diaryTitleET,diaryTitle);
            assertDisplayed(diaryTitle);
            closeKeyboard();

            writeTo(R.id.diaryDescET, "");
            clickOn(R.id.diaryDescET);
            writeTo(R.id.diaryDescET,diaryDescription);
            assertDisplayed(diaryDescription);
            closeKeyboard();

            clickOn(R.id.nextButton);
            onView(withText(startsWith("Enter Diary Title"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }

        else if (diaryTitle.equals("") && !diaryDescription.equals(""))                       //Situation where no Diary title is given but com.vawsum.test.diary description is given.
        {
            openCreateDiary();
            writeTo(R.id.diaryTitleET, "");
            clickOn(R.id.diaryTitleET);
            writeTo(R.id.diaryTitleET,diaryTitle);
            assertDisplayed(diaryTitle);
            closeKeyboard();
            writeTo(R.id.diaryDescET, "");
            clickOn(R.id.diaryDescET);
            writeTo(R.id.diaryDescET,diaryDescription);
            assertDisplayed(diaryDescription);
            closeKeyboard();

            clickOn(R.id.nextButton);
            onView(withText(startsWith("Enter Diary Title"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }

        else if (!diaryTitle.equals("") && !diaryTitle.equals("Complete Diary") && diaryDescription.equals(""))    //Situation where Diary title is "Complete Diary" and com.vawsum.test.diary description is not given.
        {
            openCreateDiary();
            writeTo(R.id.diaryTitleET, "");
            clickOn(R.id.diaryTitleET);
            writeTo(R.id.diaryTitleET,diaryTitle);
            assertDisplayed(diaryTitle);
            closeKeyboard();
            writeTo(R.id.diaryDescET, "");
            clickOn(R.id.diaryDescET);
            writeTo(R.id.diaryDescET,diaryDescription);
            assertDisplayed(diaryDescription);
            closeKeyboard();

            clickOn(R.id.nextButton);

            onView(withText(startsWith("Enter Diary Descripction"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }

        else if (diaryTitle.equals("Complete Diary") && !diaryDescription.equals(""))         //Situation where Diary title, Diary description is given and name of the com.vawsum.test.diary title is "Complete Diary" is given.
        {
            openCreateDiary();
            writeTo(R.id.diaryTitleET, "");
            clickOn(R.id.diaryTitleET);
            writeTo(R.id.diaryTitleET,diaryTitle);
            assertDisplayed(diaryTitle);
            closeKeyboard();
            writeTo(R.id.diaryDescET, "");
            clickOn(R.id.diaryDescET);
            writeTo(R.id.diaryDescET,diaryDescription);
            assertDisplayed(diaryDescription);
            closeKeyboard();
            clickOn(R.id.nextButton);

            onView(withText(startsWith("Diary name already exists"))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }

        else                                                                                  //Situation where Diary title, Diary description is given and name of the com.vawsum.test.diary title is not "Complete Diary".
        {
            openCreateDiary();
            writeTo(R.id.diaryTitleET, "");
            clickOn(R.id.diaryTitleET);
            writeTo(R.id.diaryTitleET,diaryTitle);
            assertDisplayed(diaryTitle);
            closeKeyboard();
            writeTo(R.id.diaryDescET, "");
            clickOn(R.id.diaryDescET);
            writeTo(R.id.diaryDescET,diaryDescription);
            assertDisplayed(diaryDescription);
            closeKeyboard();
            clickOn(R.id.nextButton);

            onView(withText(startsWith("Diary created successfully! Please add members."))).
                    inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                    check(matches(isDisplayed()));
        }
    }

    //Test case for testing home button
    @Test
    public void homeTest () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        openCreateDiary();
        clickOn(R.id.home_page);

        //Situation where the home button behaves differently when the navigation drawer is open
        clickOn(R.id.actionNavigationDrawer);
        clickOn("Create Diary");
        clickOn(R.id.actionNavigationDrawer);
        clickOn(R.id.home_page);
        sleep(2000);
    }

    //Test case for testing back button
    @Test
    public void backTest () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        openCreateDiary();
        clickBack();
        sleep(2000);

        //Situation where the back button behaves differently when the navigation drawer is open
        clickOn(R.id.actionNavigationDrawer);
        clickOn("Create Diary");
        clickOn(R.id.actionNavigationDrawer);
        clickBack();
        sleep(2000);
    }
    //Situation where no Diary title is given and at the same time no com.vawsum.test.diary description is given.
    @Test
    public void createDiaryA () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("", "");
    }

    //Situation where no Diary title is given but com.vawsum.test.diary description is given.
    @Test
    public void createDiaryB () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("", "This is a test description");
    }

    //Situation where Diary title is given and com.vawsum.test.diary description is not given.
    @Test
    public void createDiaryC () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("Test com.vawsum.test.diary title", "");
    }

    //Situation where Diary title is same as that of an existing com.vawsum.test.diary title and com.vawsum.test.diary description is also given.
    @Test
    public void createDiaryD () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("Complete Diary", "Existing com.vawsum.test.diary testing");
    }

    //Situation where Diary title is given and com.vawsum.test.diary description is also given.
    @Test
    public void createDiaryE () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("New Diary only", "This is a test com.vawsum.test.diary description");
    }
    @Test
    public void createDiaryF () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("New Diary with back button", "This is a test com.vawsum.test.diary description");
        SelectMembers obj =new SelectMembers();
        obj.backIconTest();
    }
    @Test
    public void createDiaryG () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("nfgnnsanfntick mxqgxb3btgtdxhark", "This is a test com.vawsum.test.diary description");
        SelectMembers obj =new SelectMembers();
        obj.tickMarkIconTest();
        sleep(2000);
        onView(withText(startsWith("Completed adding members"))).
                inRoot(withDecorView(not(is(baristaRule.getActivityTestRule().getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void createDiaryH () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("New Diary select teachers", "This is a test com.vawsum.test.diary description");
        SelectMembers obj =new SelectMembers();
        obj.selectTeachers();
    }

    @Test
    public void createDiaryI () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("New Diary select parent and students", "This is a test com.vawsum.test.diary description");
        SelectMembers obj =new SelectMembers();
        obj.selectParentStudent();
    }
    @Test
    public void createDiaryJ () throws Exception
    {
        login(credentials.getUserName(),credentials.getPassword());
        sleep(2000);
        createDiaryTest("New Diary other members", "This is a test com.vawsum.test.diary description");
        SelectMembers obj =new SelectMembers();
        obj.selectOthers();
    }
}
