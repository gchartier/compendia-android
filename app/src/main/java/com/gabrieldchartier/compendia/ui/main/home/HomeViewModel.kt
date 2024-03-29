package com.gabrieldchartier.compendia.ui.main.home

import androidx.lifecycle.LiveData
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.repository.main.HomeRepository
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.BaseViewModel
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.main.home.state.HomeStateEvent
import com.gabrieldchartier.compendia.ui.main.home.state.HomeStateEvent.*
import com.gabrieldchartier.compendia.ui.main.home.state.HomeViewState
import com.gabrieldchartier.compendia.util.AbsentLiveData
import javax.inject.Inject

class HomeViewModel
@Inject
constructor(
        val sessionManager: SessionManager,
        val homeRepository: HomeRepository
): BaseViewModel<HomeStateEvent, HomeViewState>()
{
    override fun initNewViewState(): HomeViewState {
        return HomeViewState()
    }

    override fun handleStateEvent(stateEvent: HomeStateEvent): LiveData<DataState<HomeViewState>> {
        when(stateEvent) {
            is UpdateAccountPropertiesEvent -> {
                return AbsentLiveData.create()
            }

            is ChangePasswordEvent -> {
                return AbsentLiveData.create()
            }

            is GetAccountPropertiesEvent -> {
                return AbsentLiveData.create()
            }

            is None -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if(update.accountProperties == accountProperties)
            return
        update.accountProperties = accountProperties
        _viewState.value = update
    }

    fun logout() {
        sessionManager.logout()
    }
}