package ru.ikom.feature_menu

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import ru.ikom.feature_menu.data.cache.MealCache
import ru.ikom.feature_menu.data.cache.MealsDao
import ru.ikom.feature_menu.data.cache.MealsDb

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RoomUnitTest {

    private lateinit var dao: MealsDao
    private lateinit var db: MealsDb

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MealsDb::class.java
        ).build()
        dao = db.mealsDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `adding_items_to_to_database`() = runBlocking {
        val expected = listOf(
            MealCache("0", "strMeal", "strMealThumb"),
            MealCache("1", "strMeal", "strMealThumb")
        )
        dao.addMeals(expected)
        assertEquals(expected, dao.fetchMeals())
    }

    @Test
    fun `update_items`() = runBlocking {
        val expected = listOf(
            MealCache("0", "strMeal", "strMealThumb"),
            MealCache("1", "strMeal", "strMealThumb")
        )
        dao.addMeals(expected)
        assertEquals(expected, dao.fetchMeals())
        dao.delete()
        dao.addMeals(expected)
        assertEquals(expected, dao.fetchMeals())
    }
}