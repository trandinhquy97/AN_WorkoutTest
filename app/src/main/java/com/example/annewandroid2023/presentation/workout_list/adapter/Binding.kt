package com.example.annewandroid2023.presentation.workout_list.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.annewandroid2023.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("dateOfWeek")
fun TextView.setDateOfWeek(date: Date?) {
    if (date != null) {
        try {
            this.text = SimpleDateFormat("EE", Locale.ENGLISH).format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}

@BindingAdapter("dateOfMonth")
fun TextView.setDateOfMonth(date: Date?) {
    if (date != null) {
        try {
            this.text = SimpleDateFormat("dd", Locale.ENGLISH).format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("highLightCurrentDate")
fun TextView.setHighLightCurrentDate(date: Date?) {
    if (date != null) {
        try {
            val df = SimpleDateFormat("yyyy-MM-dd")
            if (df.format(date) == df.format(Calendar.getInstance().time)) {
                this.setTextColor(Color.parseColor("#7470EF"))
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}

@BindingAdapter("date", "status")
fun setTitle(view: TextView, date: Date, status: Int) {
    when (compareDate(date1 = date, date2 = Calendar.getInstance().time)) {
        1 -> {
            view.setTextColor(Color.parseColor("#7B7E91"))
        }
        -1 -> {
            view.setTextColor(Color.parseColor("#1E0A3C"))
            if (status == 2) {
                view.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
        0 -> {
            view.setTextColor(Color.parseColor("#1E0A3C"))
            if (status == 2) {
                view.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }
}

@BindingAdapter("dateEx", "statusEx")
fun setExercise(view: TextView, date: Date, status: Int) {
    when (compareDate(date1 = date, date2 = Calendar.getInstance().time)) {
        1 -> {
            view.setTextColor(Color.parseColor("#7B7E91"))
        }
        -1 -> {
            if (status == 2) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }
        }
        0 -> {
            view.setTextColor(Color.parseColor("#1E0A3C"))
            if (status == 2) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }
        }
    }
}

@BindingAdapter("dateSt", "statusSt")
fun setStatus(view: TextView, date: Date, status: Int) {
    when (compareDate(date1 = date, date2 = Calendar.getInstance().time)) {
        -1 -> {
            if (status == 0) {
                view.text = view.context.getString(R.string.missed)
                view.setTextColor(Color.parseColor("#FF5E5E"))
            } else {
                view.text = ""
            }

            if (status == 2) {
                view.text = view.context.getString(R.string.completed)
                view.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
        0 -> {
            if (status == 2) {
                view.text = view.context.getString(R.string.completed)
                view.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
        else -> {
            view.text = ""
        }
    }
}

@BindingAdapter("dateDot", "statusDot")
fun setDot(view: TextView, date: Date, status: Int) {
    when (compareDate(date1 = date, date2 = Calendar.getInstance().time)) {
        -1 -> {
            if (status == 0) {
                view.text = " • "
            }
            if (status == 2) {
                view.text = ""
            }
        }
        0 -> {
            if (status == 2) {
                view.text = ""
            }
        }
    }
}

@BindingAdapter("dateBg", "statusBg")
fun setBackground(view: LinearLayout, date: Date, status: Int) {
    if (status == 2
        && compareDate(date1 = date, date2 = Calendar.getInstance().time) != 1
    ) {
        view.background = ContextCompat.getDrawable(view.context, R.drawable.item_select_bg)
        return
    }
    view.background = ContextCompat.getDrawable(view.context, R.drawable.item_normal_bg)
}

@BindingAdapter("dateTick", "statusTick")
fun setTick(view: ImageView, date: Date, status: Int) {
    if (status == 2
        && compareDate(date1 = date, date2 = Calendar.getInstance().time) != 1
    ) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@SuppressLint("SimpleDateFormat")
fun compareDate(date1: Date, date2: Date): Int {
    val df = SimpleDateFormat("yyyy-MM-dd")
    return if (df.format(date1) == df.format(date2)) {
        0
    } else {
        if (date1.time > date2.time) 1 else -1
    }
}