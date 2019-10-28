package github.hoanv810.icon.ui.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.StateSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import github.hoanv810.icon.R
import kotlinx.android.synthetic.main.view_button_color.view.*

/**
 * @author hoanv
 * @since 8/10/18
 */
class ColoredButton(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle) {

    @ColorInt
    private var bgColor: Int = 0
    private val radius = context.resources.getDimension(R.dimen.control_radius)

    init {
        View.inflate(context, R.layout.view_button_color, this)
        if (attrs != null) init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.ColoredButton, 0, 0)
        try {
            bgColor = array.getColor(R.styleable.ColoredButton_color, 0)
            val textColor = array.getColor(R.styleable.ColoredButton_textColor, 0)

            text.setTextColor(textColor)
            draw()
        } finally {
            array.recycle()
        }
    }

    private fun draw() {
        val enabledDrawable = createDrawable(0xFF)
        background = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), createDrawable(0xE6))
            addState(intArrayOf(android.R.attr.state_enabled), createDrawable(0x99))
            addState(intArrayOf(android.R.attr.state_enabled), enabledDrawable)
            addState(StateSet.WILD_CARD, enabledDrawable)
        }
    }

    private fun createDrawable(alpha: Int) = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        cornerRadius = radius
        setColor(ColorUtils.setAlphaComponent(bgColor, alpha))
    }
}