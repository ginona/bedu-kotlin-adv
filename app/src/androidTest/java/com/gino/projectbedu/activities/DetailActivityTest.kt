package com.gino.projectbedu.activities

import android.widget.LinearLayout
import androidx.test.rule.ActivityTestRule
import com.gino.projectbedu.R
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailActivityTest{
    @get:Rule
    val mActivityRule = ActivityTestRule(DetailActivity::class.java)
    var mActivity: DetailActivity? = null

    @Before
    fun setUp() {
        mActivity = mActivityRule.activity
    }

    @Test
    fun testLaunch() {
        var lContainer: LinearLayout? = mActivity?.findViewById(R.id.detail_container)
        assertNotNull(lContainer)
    }

    @After
    fun tearDown() {
        mActivity = null
        mActivity = null
    }
}