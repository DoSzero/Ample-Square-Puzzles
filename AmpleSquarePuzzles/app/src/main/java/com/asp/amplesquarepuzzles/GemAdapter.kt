package com.asp.amplesquarepuzzles

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.graphics.scale
import com.asp.amplesquarepuzzles.databinding.RowLayoutBinding


class GemAdapter(var ctx: Context, var puzzlist: ArrayList<Int>, var island: Boolean) :

    ArrayAdapter<Int>(ctx, R.layout.row_layout, puzzlist) {

    private lateinit var binding: RowLayoutBinding

    override fun getCount(): Int {
        return puzzlist.size
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getItem(position: Int): Int {
        return puzzlist[position]
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(ctx)
        binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
        binding.ampleSquarePuzzlesBar = BitmapDrawable(
            ctx.resources,
            drawTextInt(getItem(position).toString())
        )
        return binding.root
    }

    private fun drawTextInt(number: String): Bitmap {
        val bitmapConfig = Bitmap.Config.ARGB_8888
        if (island) {
            val bitmap = Bitmap.createBitmap(100, 100, bitmapConfig)
            bitmap.scale(100,100, filter = false)
            val canvas = Canvas(bitmap)
            val p = Paint(Paint.ANTI_ALIAS_FLAG)
            p.color = getColor(ctx.resources, R.color.woodother, null)
            val textpaint = Paint(Paint.ANTI_ALIAS_FLAG)
            textpaint.textSize = 50f
            textpaint.color = getColor(ctx.resources, R.color.white, null)

            if (number == "0") {
                p.color = getColor(ctx.resources, R.color.wood, null)
                canvas.drawColor(getColor(ctx.resources, R.color.wood, null))
                canvas.drawRect(0f, 0f, 100f, 100f, p)
                p.color = getColor(ctx.resources, R.color.woodother, null)
                canvas.drawLine(25f,50f, 60f, 50f, p)
                canvas.drawLine(25f,51f, 60f, 51f, p)
                canvas.drawLine(25f,52f, 60f, 52f, p)
            } else {
                p.color = getColor(ctx.resources, R.color.woodother, null)
                canvas.drawColor(getColor(ctx.resources, R.color.woodother, null))
                canvas.drawRect(0f, 0f, 100f, 100f, p)
                p.color = getColor(ctx.resources, R.color.wood, null)
                canvas.drawLine(25f,50f, 60f, 50f, p)
                canvas.drawLine(25f,51f, 60f, 51f, p)
                canvas.drawLine(25f,52f, 60f, 52f, p)
                canvas.drawText(number, 35f, 70f, textpaint)
            }

            Log.d("position","${canvas.isHardwareAccelerated}")
            return bitmap
        } else {
            val bitmap = Bitmap.createBitmap(50, 50, bitmapConfig)
            bitmap.scale(50,50)
            val canvas = Canvas(bitmap)
            val p = Paint(Paint.ANTI_ALIAS_FLAG)
            p.color = getColor(ctx.resources, R.color.woodother, null)
            val textpaint = Paint(Paint.ANTI_ALIAS_FLAG)
            textpaint.textSize = 30f
            val radius = 10f

            if (number == "0") {
                p.color = getColor(ctx.resources, R.color.wood, null)
                canvas.drawColor(getColor(ctx.resources, R.color.wood, null))
                canvas.drawRoundRect(20f, 20f, 50f, 2 * radius, radius, radius, p)
                canvas.drawRect(0f, 0f, 50f, 50f, p)
                p.color = getColor(ctx.resources, R.color.woodother, null)
                canvas.drawLine(5f,25f, 30f, 25f, p)
            } else {
                p.color = getColor(ctx.resources, R.color.woodother, null)
                canvas.drawColor(getColor(ctx.resources, R.color.woodother, null))
                canvas.drawRoundRect(0f, 0f, 50f, 2 * radius, radius, radius, p)
                canvas.drawRect(0f, radius, 50f, 50f, p)
                p.color = getColor(ctx.resources, R.color.wood, null)
                canvas.drawLine(5f,25f, 30f, 25f, p)
                textpaint.color = Color.WHITE
                canvas.drawText(number, 15f, 30f, textpaint)
            }
            Log.d("position","${canvas.isHardwareAccelerated}")
            return bitmap
        }

    }

}