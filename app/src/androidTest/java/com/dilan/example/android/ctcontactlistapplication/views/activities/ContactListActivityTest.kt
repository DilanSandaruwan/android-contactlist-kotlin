package com.dilan.example.android.ctcontactlistapplication.views.activities

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.viewmodels.ContactListViewModel
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class ContactListActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(ContactListActivity::class.java)

    private lateinit var viewModel: ContactListViewModel

    @Before
    fun setUp() {
        val activityScenario = activityRule.scenario
        activityScenario.onActivity { activity ->
            viewModel = ViewModelProvider(activity)[ContactListViewModel::class.java]
        }
    }

    @Test
    fun testBackButtonClickNavigation() {

        // touch the back button
        onView(withId(R.id.btn_arrow_back)).perform(
            click()
        )

        // check
        //assertTrue(activityRule.scenario.result.resultCode == RESULT_OK)

        // Verify that the activity is finishing
        activityRule.scenario.onActivity { activity ->
            assertTrue(activity.isFinishing)
        }
    }

    @Test
    fun testExitButton() {
        // Click the exit button
        onView(withId(R.id.btn_exit)).perform(click())

        // Verify that the activity is finishing
        activityRule.scenario.onActivity { activity ->
            assertTrue(activity.isFinishing)
        }
    }

    @Test
    fun testContactListInitialization() {

        // Get the initialized contact list from the ViewModel
        val contactList = viewModel.contactList.value

        // Verify that the contact list is not empty
        assertNotNull(contactList)
        contactList?.let { assertTrue(it.isNotEmpty()) }
    }


}