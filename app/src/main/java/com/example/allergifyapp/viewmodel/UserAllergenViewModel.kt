package com.example.allergifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allergifyapp.data.remote.model.userallergen.UserAllergen
import com.example.allergifyapp.data.remote.model.userallergenadd.UserAllergenAdd
import com.example.allergifyapp.data.remote.model.userallergendelete.UserAllergenDeleteResponse
import com.example.allergifyapp.data.remote.model.userallergenupdate.UpdateAllergenResponse
import com.example.allergifyapp.repository.remote.UserAllergenRepository
import com.example.allergifyapp.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAllergenViewModel @Inject constructor(
    private val userAllergenRepository: UserAllergenRepository
): ViewModel() {

    private val _userAllergens = MutableLiveData<DataStatus<List<UserAllergen>>>()
    val userAllergens: LiveData<DataStatus<List<UserAllergen>>>
        get() = _userAllergens

    fun getUserAllergens() {
        viewModelScope.launch {
            userAllergenRepository.getUserAllergens().collect {
                _userAllergens.value = it
            }
        }
    }

    private val _userAllergensUpdate = MutableLiveData<DataStatus<UpdateAllergenResponse>>()
    val userUpdateAllergens: LiveData<DataStatus<UpdateAllergenResponse>>
        get() = _userAllergensUpdate

    fun updateUserAllergens(id: String, name: String) {
        viewModelScope.launch {
            userAllergenRepository.updateUserAllergens(id, name).collect {
                _userAllergensUpdate.value = it
                getUserAllergens()
            }
        }
    }

    private val _addUserAllergens = MutableLiveData<DataStatus<UserAllergenAdd>>()
    val addUserAllergens: LiveData<DataStatus<UserAllergenAdd>>
        get() = _addUserAllergens

    fun addUserAllergens(name: String) {
        viewModelScope.launch {
            userAllergenRepository.addUserAllergens(name).collect {
                _addUserAllergens.value = it
                getUserAllergens()
            }
        }
    }

    private val _deleteUserAllergens = MutableLiveData<DataStatus<UserAllergenDeleteResponse>>()
    val deleteUserAllergens: LiveData<DataStatus<UserAllergenDeleteResponse>>
        get() = _deleteUserAllergens

    fun deleteUserAllergens(id: String) {
        viewModelScope.launch {
            userAllergenRepository.deleteUserAllergens(id).collect {
                _deleteUserAllergens.value = it
                getUserAllergens()
            }
        }
    }

}