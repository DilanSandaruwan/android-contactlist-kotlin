package com.dilan.example.android.ctcontactlistapplication.views.fragments

import org.junit.Test

internal class DetailsFragmentTest {

//  TODO : with navigation and fragments

//    @Rule
//    val activityRule = ActivityTestRule(ContactListActivity::class.java)
//
//    @Before
//    fun setUp() {
//        // Initialize Mockito
//        MockitoAnnotations.initMocks(this)
//
//        // Create a test NavHostController
//        val navController = TestNavHostController(
//            InstrumentationRegistry.getInstrumentation().targetContext
//        )
//
//        // Set the NavController for the fragment
//        val scenario =
//            launchFragmentInContainer(themeResId = R.style.Base_Theme_CTContactListApplication) {
//                DetailsFragment().also { fragment ->
//                    fragment.arguments = null
//                    fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
//                        if (viewLifecycleOwner != null) {
//                            Navigation.setViewNavController(fragment.requireView(), navController)
//                        }
//                    }
//                }
//            }
//
//        // Set up the navigation graph
//        navController.setGraph(R.navigation.nav_graph)
//
//        // Navigate to the DetailsFragment with arguments
//        var contactData = ContactData(
//            "John",
//            "Doe",
//            "0771234567",
//            "john@example.com"
//        )
//
//        val args = DetailsFragmentArgs(contactData).toBundle()
//        scenario.onFragment { fragment ->
//            navController.setCurrentDestination(R.id.detailsFragment)
//            fragment.arguments = args
//        }
}

@Test
fun testEditButton() {
//        // Click the Edit button
//        Espresso.onView(ViewMatchers.withId(R.id.btn_edit)).perform(ViewActions.click())
//
//        // Check if the EditText fields are enabled
//        Espresso.onView(ViewMatchers.withId(R.id.et_contact_first_name))
//            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
//        Espresso.onView(ViewMatchers.withId(R.id.et_contact_last_name))
//            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
//        Espresso.onView(ViewMatchers.withId(R.id.et_contact_number_1))
//            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
//        Espresso.onView(ViewMatchers.withId(R.id.et_contact_email))
//            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
//    }

//    @Test
//    fun testEditButton() {
//        // Launch the fragment
//        val scenario = launchFragmentInContainer<DetailsFragment>()
//
//        // Perform a click on the "Edit" button
//        onView(withId(R.id.btn_edit)).perform(click())
//
//        // Verify that the EditText fields are enabled after clicking "Edit"
//        onView(withId(R.id.et_contact_first_name)).check(matches(isEnabled()))
//        onView(withId(R.id.et_contact_last_name)).check(matches(isEnabled()))
//        //onView(withId(R.id.et_contact_number_1)).check(matches(isEnabled()))
//        onView(withId(R.id.et_contact_email)).check(matches(isEnabled()))
//    }

//    @Test
//    fun testSaveButton() {
//        // Launch the fragment
//        val scenario = launchFragmentInContainer<DetailsFragment>()
//
//        // Perform a click on the "Save" button
//        onView(withId(R.id.btn_save)).perform(click())
//
//        // Write assertions to validate the behavior after clicking "Save"
//        // For example, you can check if a Snackbar appears
//        onView(withText(R.string.contact_validation_msg)).check(matches(isDisplayed()))
//    }
}