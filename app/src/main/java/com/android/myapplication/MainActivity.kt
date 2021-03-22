package com.android.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity : AppCompatActivity() {
    private var flutterFragment: FlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpFlutterEngine()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpFlutterFragment();
    }


    private fun setUpFlutterFragment() {
        flutterFragment =
            supportFragmentManager.findFragmentByTag("flutter") as FlutterFragment?
        if (flutterFragment == null) {
            flutterFragment =
                FlutterFragment.withCachedEngine("engine").build()
            flutterFragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .add(
                        R.id.container,
                        it,
                        "flutter"
                    ).commit()
            }
        } else {
            supportFragmentManager.beginTransaction().show(flutterFragment!!).commit()
        }
    }

    private fun setUpFlutterEngine() {
        var flutterEngine = FlutterEngineCache.getInstance().get("engine")

        if (flutterEngine == null) {
            flutterEngine = FlutterEngine(this)
            FlutterEngineCache.getInstance().put("engine", flutterEngine)

            flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )
        }
    }

    override fun onBackPressed() {
        flutterFragment?.onBackPressed()
    }

}