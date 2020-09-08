package kr.hs.dgsw.stac.semo.di

import kr.hs.dgsw.stac.semo.viewmodel.dialog.DeleteViewModel
import kr.hs.dgsw.stac.semo.viewmodel.dialog.NextViewModel
import kr.hs.dgsw.stac.semo.viewmodel.view.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(androidApplication()) }
    viewModel { SignInViewModel(androidApplication()) }
    viewModel { SignUpViewModel() }
    viewModel { MainViewModel() }
    viewModel { SelectViewModel() }
    viewModel { CameraKitViewModel() }
    viewModel { ListViewModel() }
    viewModel { InfoViewModel() }
    viewModel { AddViewModel() }
    viewModel { MyLaundryViewModel() }
    viewModel { ModifyViewModel() }

    viewModel { NextViewModel() }
    viewModel { DeleteViewModel() }
}