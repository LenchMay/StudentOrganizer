package lench.may.studentorganizer;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addTaskTest() {
        //на текущей странице найти пункт меню с id «tasks»
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.tasksBtn), withContentDescription("Задачи"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()));
        //нажать на пункт меню
        bottomNavigationItemView.perform(click());
        //на текущей странице найти кнопку с id «buttonAdd» и текстом «Добавить задачу»
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.buttonAdd), withText("Добавить задачу"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        //нажать на кнопку
        appCompatButton.perform(click());
        //на текущей странице найти поле для ввода текста с id «SubjName»
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.SubjName),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()));
        //вставить текст со значением «Математика» в поле и закрыть клавиатуру
        appCompatEditText.perform(replaceText("Дипломное проектирование"), closeSoftKeyboard());
        //на текущей странице найти поле для ввода текста с id «SubjPed»
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.SubjPed),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                2),
                        isDisplayed()));
        //вставить текст со значением «В 14 часов» в поле и закрыть клавиатуру
        appCompatEditText2.perform(replaceText("Написать введение"), closeSoftKeyboard());
        //на текущей странице найти кнопку с id «buttonAdd» и текстом «Сохранить»
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.buttonAdd), withText("Сохранить"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        //нажать на кнопку
        appCompatButton2.perform(click());
        //на текущей странице найти поле с id «text1» и текстом со значением «Математика»
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Математика"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.taskList),
                                        0),
                                1),
                        isDisplayed()));
        //проверить, совпадает ли значение найденного поля со значением «Математика»
        textView.check(matches(withText("Математика")));
    }

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
}
