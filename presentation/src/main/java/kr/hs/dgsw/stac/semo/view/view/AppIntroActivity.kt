package kr.hs.dgsw.stac.semo.view.view

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import kr.hs.dgsw.stac.semo.R
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish

class AppIntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(AppIntroFragment.newInstance(
            description = "세탁의 모든 것, 세모\n\n오늘부터 여러분들의 옷 세탁을 책임집니다.",
            imageDrawable = R.drawable.ic_logo2,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "1. 다양한 종류의 세탁 취급 표시\n\n32가지의 세탁 취급 표시에 대한 세탁법을 알아보세요.",
            imageDrawable = R.drawable.img_intro1,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "2. 나만의 세탁법 보관함\n\n이전에 저장한 세탁법을 언제든지 확인하세요.",
            imageDrawable = R.drawable.img_intro2,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "3. 인공지능을 통한 세탁 취급 표시 인식\n\n세탁 취급 표시를 카메라로 촬영하면 자동으로 인식합니다.",
            imageDrawable = R.drawable.img_intro3,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "4. 세탁 취급 표시 선택\n\n촬영이 불가능한 경우 직접 세탁 취급 표시를 선택하세요.",
            imageDrawable = R.drawable.img_intro4,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "세탁의 모든 것, 세모\n\n이제 나만의 세탁법 보관함을 채워보세요.",
            imageDrawable = R.drawable.ic_logo2,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))

        setTransformer(AppIntroPageTransformerType.Depth)
        setProgressIndicator()
        indicatorController!!.selectedIndicatorColor = Color.parseColor("#FFFFFF")
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        startActivityWithFinish(applicationContext, MainActivity::class.java)
    }
    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivityWithFinish(applicationContext, MainActivity::class.java)
    }
}