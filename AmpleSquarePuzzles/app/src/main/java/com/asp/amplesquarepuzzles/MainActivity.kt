package com.asp.amplesquarepuzzles

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.GridView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.asp.amplesquarepuzzles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listNumber: ArrayList<Int>
    private lateinit var gemAdapter: GemAdapter
    private lateinit var correct: ArrayList<Int>
    private var posisic = 0
    private var posisizero =0
    private var gridenable=false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).also { binding = it }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            val windowInsets = window.decorView.rootWindowInsets
            if (windowInsets != null) {
                val displayCutout = windowInsets.displayCutout
                if (displayCutout != null) {
                    val safeInsetTop = displayCutout.safeInsetTop
                    val newLayoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
                    newLayoutParams.setMargins(0, safeInsetTop, 0, 0)
                    binding.root.layoutParams = newLayoutParams
                }
            }
        }
        setContentView(binding.root)
        binding.buttonEnab = true

        listNumber = ArrayList()
        listNumber.add(1)
        listNumber.add(2)
        listNumber.add(3)
        listNumber.add(4)
        listNumber.add(5)
        listNumber.add(6)
        listNumber.add(7)
        listNumber.add(8)
        listNumber.add(0)
        correct = ArrayList()
        correct.addAll(listNumber)

        gemAdapter = if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            GemAdapter(this,listNumber,true)
        } else {
            GemAdapter(this,listNumber,false)
        }

        binding.button.setOnClickListener { _ ->
            while (true) {
                var inversion = 0
                listNumber.shuffle()
                listNumber.forEach { number ->
                    for (i in (listNumber.indexOf(number)+1) until listNumber.size)
                        if (number > listNumber[i]&&listNumber[i]!=0) inversion++
                }

                if (inversion % 2 == 0) {
                    gridenable = true
                    binding.buttonEnab = false
                    binding.time.base = SystemClock.elapsedRealtime()
                    binding.time.start()
                    gemAdapter.notifyDataSetChanged()
                    break
                } else {
                    Log.d("position", "Not Solvable")
                }
            }
        }

        binding.gridpuzz.adapter = gemAdapter
        binding.gridpuzz.setOnTouchListener { v, event ->
            val valueX = event.x.toInt()
            val valueY = event.y.toInt()

            when(event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    posisizero = listNumber.indexOf(0)
                    posisic = (v as? GridView)?.pointToPosition(valueX, valueY)!!
                    if(posisic> -1) {
                        if (gridenable) {
                            if (posisic == 0 && ((posisic + 1) == posisizero || (posisic + 3) == posisizero)) {
                                val n = listNumber[posisic]
                                listNumber.set(posisizero,n)
                                listNumber.set(posisic, 0)
                                if(posisic+1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideRight()
                                if(posisic+3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideDown()
                            } else if (posisic == 1 && ((posisic + 1) == posisizero || (posisic + 3) == posisizero || (posisic - 1) == posisizero)) {
                                val n = listNumber[posisic]
                                listNumber.set(posisizero,n)
                                listNumber.set(posisic, 0)
                                if(posisic+1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideRight()
                                if(posisic+3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideDown()
                                if(posisic-1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideLeft()
                            } else if (posisic == 2 && ((posisic + 3) == posisizero || (posisic - 1) == posisizero)) {
                                val n = listNumber[posisic]
                                listNumber.set(posisizero,n)
                                listNumber.set(posisic, 0)
                                if(posisic+3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideDown()
                                if(posisic-1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideLeft()
                            } else if (posisic == 3 && ((posisic + 1) == posisizero || (posisic + 3) == posisizero || (posisic-3)==posisizero)) {
                                if (posisic - 1 != posisizero) {
                                    val n = listNumber[posisic]
                                    listNumber.set(posisizero,n)
                                    listNumber.set(posisic, 0)
                                    if(posisic+1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideRight()
                                    if(posisic-3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideUp()
                                    if(posisic+3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideDown()
                                } else {
                                    //GemAdapter.notifyDataSetChanged()
                                }
                            } else if (posisic == 4 && ((posisic + 1) == posisizero || (posisic + 3) == posisizero || (posisic - 1) == posisizero || (posisic - 3) == posisizero)) {
                                val n = listNumber[posisic]
                                listNumber.set(posisizero,n)
                                listNumber.set(posisic, 0)
                                if(posisic+1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideRight()
                                if(posisic+3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideDown()
                                if(posisic-1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideLeft()
                                if(posisic-3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideUp()
                            } else if (posisic == 5 && ((posisic - 1) == posisizero || (posisic + 3) == posisizero || (posisic - 3) == posisizero)) {
                                val n = listNumber[posisic]
                                listNumber.set(posisizero,n)
                                listNumber.set(posisic, 0)
                                if(posisic+1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideRight()
                                if(posisic+3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideDown()
                                if(posisic-1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideLeft()
                                if(posisic-3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideUp()
                            } else if (posisic == 6 && ((posisic + 1) == posisizero || (posisic - 3) == posisizero)) {
                                if (posisic - 1 != posisizero) {
                                    val n = listNumber[posisic]
                                    listNumber.set(posisizero,n)
                                    listNumber.set(posisic, 0)
                                    if(posisic+1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideRight()
                                    if(posisic-3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideUp()
                                }
                            } else if (posisic == 7 && ((posisic + 1) == posisizero || (posisic - 1) == posisizero || (posisic - 3) == posisizero)) {
                                val n = listNumber[posisic]
                                listNumber.set(posisizero,n)
                                listNumber.set(posisic, 0)
                                if(posisic+1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideRight()
                                if(posisic-1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideLeft()
                                if(posisic-3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideUp()
                            } else if (posisic == 8 && ((posisic - 1) == posisizero || (posisic - 3) == posisizero)) {
                                val n = listNumber[posisic]
                                listNumber.set(posisizero,n)
                                listNumber.set(posisic, 0)
                                if(posisic-1==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideLeft()
                                if(posisic-3==posisizero) ((v as? GridView)?.getChildAt(posisic) as LinearLayout).slideUp()
                            }else{
                                Log.d("position","illegal move")
                            }
                            v.invalidate()
                            gemAdapter.notifyDataSetChanged()

                            if(listNumber.equals(correct)) {
                                val timeRecord = (SystemClock.elapsedRealtime() - binding.time.base)
                                binding.time.stop()
                                binding.time.base = SystemClock.elapsedRealtime()
                                var second = Math.floorDiv(timeRecord, 1000)
                                var minute = 0L
                                var hour = 0L

                                var bestSeconds: Long
                                var bestMinute = 0L
                                var bestHour = 0L

                                if(second >=60) {
                                    minute = Math.floorDiv(second, 60)
                                    second = (second % 60)
                                }
                                if(minute >= 60){
                                    hour = Math.floorDiv(minute, 60)
                                    minute %= 60
                                }
                                gridenable = false
                                binding.buttonEnab = true

                                var sharedpref = getPreferences(MODE_PRIVATE)
                                val bestValue = sharedpref.getLong(getString(R.string.best), 0L)
                                val alert = AlertDialog.Builder(binding.root.context)
                                alert.setTitle("Ваш результат")

                                if(bestValue == 0L) {
                                    with(sharedpref.edit()) {
                                        putLong(getString(R.string.best), timeRecord)
                                        commit()
                                    }
                                }else {
                                    if(timeRecord < bestValue){
                                        with(sharedpref.edit()){
                                            putLong(getString(R.string.best), timeRecord)
                                            commit()
                                        }
                                    }
                                }

                                if((bestValue == 0L) || (bestValue > timeRecord)) {
                                    alert.setMessage(
                                        "Вы решили эту головоломку за %02d:%02d:%02d\nВаши лучший резльтат %02d:%02d:%02d"
                                        .format( hour,minute,second, hour, minute, second)
                                    )
                                } else {
                                    bestSeconds = Math.floorDiv(bestValue, 1000)
                                    if(bestSeconds >= 60){
                                        bestMinute = Math.floorDiv(bestSeconds, 60)
                                        bestSeconds %= 60
                                    }
                                    if(bestMinute >= 60){
                                        bestHour = Math.floorDiv(bestMinute, 60)
                                        bestMinute %= 60
                                    }

                                    alert.setMessage(
                                        "Вы решили эту головоломку в %02d:%02d:%02d\n Ваши лучший результат %02d:%02d:%02d"
                                        .format(hour, minute, second, bestHour, bestMinute, bestSeconds )
                                    )
                                }

                                alert.setNeutralButton("OK", DialogInterface.OnClickListener {
                                        dialog, _ ->
                                        dialog.cancel()
                                })
                                alert.setCancelable(false)
                                alert.create()
                                alert.show()
                            }
                        }
                    }
                }
            }
            true
        }
    }

    private fun View.slideUp(duration: Int = 100) {
        val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

    private fun View.slideDown(duration: Int = 100) {
        val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

    private fun View.slideLeft(duration: Int = 100) {
        val animate = TranslateAnimation(this.width.toFloat(), 0f, 0f, 0f)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

    private fun View.slideRight(duration: Int = 100) {
        val animate = TranslateAnimation(0f, this.width.toFloat(), 0f, 0f)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

}