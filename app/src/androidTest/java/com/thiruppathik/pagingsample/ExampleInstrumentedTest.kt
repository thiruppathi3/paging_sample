package com.thiruppathik.pagingsample

import android.support.test.InstrumentationRegistry
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import com.thiruppathik.pagingsample.api.UserResponse
import com.thiruppathik.pagingsample.api.UserService
import com.thiruppathik.pagingsample.db.UserDatabase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    var response: Response<UserResponse>? = null

    @Before
    fun setup() {
        response = UserService.create().getUsers(1).execute()
    }

    @Test
    fun checkResponse() {
        assertTrue("Response Success", response != null && response!!.isSuccessful)
        assertTrue("Has Data!", response!!.body() != null && response!!.body()!!.data != null)
        assertEquals("Page has 3 users", 3, response!!.body()!!.data.size)
    }

    @Test
    fun checkDBOperations() {
        var data = response!!.body()!!.data
        data.forEach {
            it.page = response!!.body()!!.page
        }
        val context = InstrumentationRegistry.getTargetContext()
        UserDatabase.getInstance(context).reposDao().insert(data)

        data.forEach {
            var cachedData = UserDatabase.getInstance(context).reposDao().userDataById(it.id)
            assertTrue("Data Insert Check!!",
                    (cachedData.id == it.id) && (cachedData.page == response!!.body()!!.page))
        }
    }
}
