package com.l24o.findmylove.modules.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.l24o.workcalendar.R
import com.l24o.workcalendar.common.CalendarAssetsManager
import com.l24o.workcalendar.data.rest.models.Calendar
import com.l24o.workcalendar.modules.GodObject
import com.l24o.workcalendar.modules.calendar.CalendarActivity
import com.redmadrobot.core.extension.schedulersIoToMain
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initViews()
    }

    private fun initViews() {
//        activity_splash_container_lottie.setAnimation(ANIM.ANIM_3.resId)
        activity_splash_container_lottie.setAnimation(ANIM.values()[Random().nextInt(ANIM.values().size)].resId)
        activity_splash_container_lottie.playAnimation()

        disposable = Single.zip(
                CalendarAssetsManager.calendar().doOnSuccess { it.forEach { GodObject.set(it) } },
                Single.timer(4, TimeUnit.SECONDS),
                BiFunction<List<Calendar>, Long, List<Calendar>> { t1, _ -> t1 }
        )
                .schedulersIoToMain()
                .subscribe({
                    startActivity(Intent(this@SplashActivity, CalendarActivity::class.java))
                    finish()
                }, {
                    it.printStackTrace()
                    startActivity(Intent(this@SplashActivity, CalendarActivity::class.java))
                    finish()
                })
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    enum class ANIM(val resId: String) {
        ANIM_1("anim/anim_1.json"),
        ANIM_2("anim/anim_2.json"),
        //        ANIM_3("anim/anim_3.json"),
        ANIM_5("anim/anim_5.json")
    }
}