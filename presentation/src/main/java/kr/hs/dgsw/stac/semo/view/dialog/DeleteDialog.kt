package kr.hs.dgsw.stac.semo.view.dialog

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import kr.hs.dgsw.stac.semo.base.BaseDialog
import kr.hs.dgsw.stac.semo.databinding.DialogDeleteBinding
import kr.hs.dgsw.stac.semo.viewmodel.dialog.DeleteViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DeleteDialog : BaseDialog<DialogDeleteBinding, DeleteViewModel>() {

    val onDeleteEvent = SingleLiveEvent<Unit>()

    override val viewModel: DeleteViewModel
        get() = getViewModel(DeleteViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onPositiveEvent.observe(this@DeleteDialog, Observer {
                onDeleteEvent.call()
                dismiss()
             })
            onNegativeEvent.observe(this@DeleteDialog, Observer {
                dismiss()
            })
        }
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, tag)
        this.isCancelable = false
    }
}