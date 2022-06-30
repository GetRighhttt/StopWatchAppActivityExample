package com.example.stopwatchappactivityexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

/**
 * Here is the main code for the Stopwatch app!
 * Var stopwatch will hold reference to the Chronometer's View.
 * Var running will record whether the watch is running or not.
 * Var offset is used to display the correct time on the watch.
 * Incorporates two methods to make the code more readable.
 * setBaseTime() sets the watch time
 * saveOffset() saves the time on the watch
 */


class MainActivity : AppCompatActivity() {

    // These values below will be used to help control the stopwatch!

    lateinit var stopwatch: Chronometer // The stopwatch
    var running = false                 // Is the stopwatch running? True = clicked, False = stopped
    var offset: Long = 0                // Displays the correct time for the stopwatch, base = 0

    /**
     * We are going to add in 3 constants to use to save properties, and bundle them in case
     * the configuration of the app changes below.
     */

    // Add key strings for use with Bundle
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    /**
     * onCreate used for every Android program
     * Automatically generated when any layout is created.
     * onSavedInstanceState() allows us to bundle properties to save before onDestroy()
     * gets called. Those properties are then picked up by onCreate().
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Must get a reference to the Stopwatch..
        stopwatch = findViewById<Chronometer>(R.id.stopwatch)


        /**
         * Below we are going to use our constants to save the instance state and restore
         * the state of offset and running before the onDestroy() method gets called.
         *
         * If savedInstanceState() is not empty, restore the state of offset and running.
         *
         * Then we set the timer on the stopwatch and start it if the watch should be running.
         *
         * We use the get() methods to retrieve the properties outlined in your notes.
         */

        if (savedInstanceState != null) {
           offset = savedInstanceState.getLong(OFFSET_KEY)
           running = savedInstanceState.getBoolean(RUNNING_KEY)
           if(running) {
               stopwatch.base = savedInstanceState.getLong(BASE_KEY)
               stopwatch.start()
           } else setBaseTime()
        }

        // The start button starts the stopwatch is it's not running
        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
           // If the watch is not running, set the base time and start the watch
            // Make sure the watch is running the correct time..
            if(!running) {
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }

        // The pause button pauses the watch if it is running
        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {
            // If the watch IS running, save the time, and then stop it
            if(running) {
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }

        // The reset button sets the offset and stopwatch to 0
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            // Sets the stopwatch back to 0
            offset = 0
            setBaseTime()
        }

    }

    /**
     * onPause() and onResume() examples below:
     * onPause() called when the activity stops but is still partially visible to the user.
     * onResume() called immediately when the activity becomes visible to the user again.
     */

    // if running, save the time and update the offset property
    override fun onPause() {
        super.onPause()
        if(running) {
            saveOffset()
            stopwatch.stop()
        }
    }

    // if running, set the base time, and start the watch.
    override fun onResume() {
        super.onResume()
        if(running) {
            setBaseTime()
            stopwatch.start()
            offset = 0
        }
    }

    /**
     * Below we are going to use the onSaveInstanceState() method to save the state of offset,
     * running, and stopwatch BASE properties. This is how you save information for a Bundle.
     * We use the put() methods to save the properties outlined in your notes.
     */


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    /**
     * System.elapsedRealTime() returns the number of milliseconds since the device
     * was booted. Setting the base property to this value means that the time
     * displayed is set to 0.
     */

    // Method below used to update the vase time, allowing for any offset time
    private fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    // Method below records the offset of the time.
    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }

}