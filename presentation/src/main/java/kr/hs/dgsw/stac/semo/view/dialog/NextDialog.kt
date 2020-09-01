package kr.hs.dgsw.stac.semo.view.dialog

import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import kr.hs.dgsw.stac.semo.base.BaseDialog
import kr.hs.dgsw.stac.semo.databinding.DialogNextBinding
import kr.hs.dgsw.stac.semo.viewmodel.dialog.NextViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import org.koin.androidx.viewmodel.ext.android.getViewModel

class NextDialog : BaseDialog<DialogNextBinding, NextViewModel>() {

    val onMoveEvent = SingleLiveEvent<Unit>()

    override val viewModel: NextViewModel
        get() = getViewModel(NextViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onPositiveEvent.observe(this@NextDialog, Observer {
                Toast.makeText(context, "추가적으로 기호를 인식시키기 바랍니다.", Toast.LENGTH_SHORT).show()
                dismiss()
             })
            onNegativeEvent.observe(this@NextDialog, Observer {
                onMoveEvent.call()
                dismiss()
            })
        }
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, tag)
        this.isCancelable = false
    }
}