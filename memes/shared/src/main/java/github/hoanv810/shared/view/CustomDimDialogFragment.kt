package github.hoanv810.shared.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

/**
 * @author hoanv
 * @since 8/30/18
 */
open class CustomDimDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return CustomDimDialog(context)
    }
}