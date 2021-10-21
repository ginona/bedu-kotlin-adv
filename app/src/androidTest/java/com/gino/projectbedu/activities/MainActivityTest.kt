package com.gino.projectbedu.activities

import android.widget.LinearLayout
import androidx.test.rule.ActivityTestRule
import com.gino.projectbedu.R
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java)
    var mActivity: MainActivity? = null

    @Before
    fun setUp() {
        mActivity = mActivityRule.activity
    }

    @Test
    fun testLaunch() {
        var lContainer: LinearLayout? = mActivity?.findViewById(R.id.main_container)
        assertNotNull(lContainer)
    }

    @After
    fun tearDown() {
        mActivity = null
        mActivity = null
    }
}