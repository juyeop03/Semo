package kr.hs.dgsw.stac.semo.view.view

import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image.*
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityImageBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.ImageViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ImageActivity : BaseActivity<ActivityImageBinding, ImageViewModel>() {

    override val viewModel: ImageViewModel
        get() = getViewModel(ImageViewModel::class)

    override fun init(){
        val imageUri = intent.getStringExtra("imageUri")
        Glide.with(applicationContext).load(imageUri).into(imageView)
    }
    override fun observerViewModel() {}
}