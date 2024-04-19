package com.maden.cleanrickandmortyandroid.presentation.home_page.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.android.material.card.MaterialCardView
import com.maden.cleanrickandmortyandroid.R

@SuppressLint("ClickableViewAccessibility")
class CardViewAnimHelper(
    private val _context: Context,
    private val _cardView: MaterialCardView,
    private val _animationCompleted: () -> Unit
) : Animation.AnimationListener {

    private var _x1 = 0f
    private var _x2 = 0f
    private val _swipeThreshold = 100

    init {
        _cardView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    _x1 = event.x
                    true
                }

                MotionEvent.ACTION_UP -> {
                    _x2 = event.x
                    val deltaX = _x2 - _x1
                    if (deltaX > _swipeThreshold) {
                        likeAnim()
                    } else if (deltaX < -_swipeThreshold) {
                        disLikeAnim()
                    }
                    //else _onClickListener()
                    true
                }

                else -> false
            }
        }
    }

    fun likeAnim(): Boolean {
        if (_lock) return false
        val slideOutLeft =
            AnimationUtils.loadAnimation(_context, R.anim.slide_out_right)
        slideOutLeft.setAnimationListener(this)
        _cardView.startAnimation(slideOutLeft)
        return true
    }

    fun disLikeAnim(): Boolean {
        if (_lock) return false
        val slideOutRight =
            AnimationUtils.loadAnimation(_context, R.anim.slide_out_left)
        slideOutRight.setAnimationListener(this)
        _cardView.startAnimation(slideOutRight)
        return true
    }

    private var _lock = false

    override fun onAnimationStart(p0: Animation?) {
        _lock = true
    }

    override fun onAnimationEnd(p0: Animation?) {
        _animationCompleted()
        _lock = false
    }

    override fun onAnimationRepeat(p0: Animation?) {

    }
}