package kr.hs.dgsw.stac.semo.di

import kr.hs.dgsw.stac.semo.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { MainViewModel() }
    viewModel { SelectViewModel() }
    viewModel { CameraKitViewModel() }

    viewModel { NextViewModel() }
}