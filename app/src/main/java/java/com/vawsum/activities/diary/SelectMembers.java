package java.com.vawsum.activities.diary;
/*
 * Created by admin on 08-02-2018.
 */

import com.vawsum.R;

import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;

class SelectMembers
{
    void backIconTest ()
    {
        assertDisplayed("Select Members");
        clickOn(R.id.prevIV);
    }
    void tickMarkIconTest ()
    {
        assertDisplayed("Select Members");
        clickOn(R.id.nextIV);
    }
    void selectTeachers ()
    {
        assertDisplayed("Select Members");
        clickOn(R.id.teacherBtn);
    }
    void selectParentStudent ()
    {
        assertDisplayed("Select Members");
        clickOn(R.id.studentParentBtn);
    }
    void selectOthers ()
    {
        assertDisplayed("Select Members");
        clickOn(R.id.othersBtn);
    }
}
