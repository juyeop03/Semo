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
            description = "세탁의 모든 것, 세모\n\n오늘부터 여러분들의 옷 세탁을 도와줍니다.",
            imageDrawable = R.drawable.ic_logo2,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "1. 인공지능을 활용한 카메라 인식\n\n옷에 존재하는 세탁 기호를 자동으로 인식합니다.",
            imageDrawable = R.drawable.ic_logo2,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "2. 사용자의 수동적 세탁 기호 선택\n\n카메라 촬영이 불가능한 경우를 대비해 존재합니다.",
            imageDrawable = R.drawable.ic_logo2,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "3. 다양한 종류의 세탁 기호\n\n32가지의 세탁 기호들에 대한 세탁법을 알려줍니다.",
            imageDrawable = R.drawable.ic_logo2,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "4. 나만의 세탁법 보관함\n\n이전의 세탁법들을 언제나 확인할 수 있습니다.",
            imageDrawable = R.drawable.ic_logo2,
            descriptionColor = Color.WHITE,
            backgroundColor = Color.parseColor("#3F8EBF"),
            titleTypefaceFontRes = R.font.font_bold,
            descriptionTypefaceFontRes = R.font.font
        ))
        addSlide(AppIntroFragment.newInstance(
            description = "세탁의 모든 것, 세모\n\n이제 여러분들의 세탁 보관함을 채워보세요.",
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