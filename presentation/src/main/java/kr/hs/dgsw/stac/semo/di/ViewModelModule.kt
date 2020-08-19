package kr.hs.dgsw.stac.semo.di

import kr.hs.dgsw.stac.semo.viewmodel.MainViewModel
import kr.hs.dgsw.stac.semo.viewmodel.SplashViewModel
import kr.hs.dgsw.stac.semo.viewmodel.SignInViewModel
import kr.hs.dgsw.stac.semo.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { MainViewModel() }
}