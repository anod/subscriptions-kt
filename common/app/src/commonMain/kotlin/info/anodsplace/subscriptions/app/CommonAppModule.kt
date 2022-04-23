package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.currency.ExchangeRate
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun createCommonAppModule(): Module = module {
    factoryOf(::ExchangeRate)
    factory { ViewModelScope() }
    factoryOf(::CommonLoginViewModel) bind LoginViewModel::class
    factoryOf(::CommonEditViewModel) bind EditViewModel::class
    factoryOf(::CommonMainViewModel) bind MainViewModel::class
}