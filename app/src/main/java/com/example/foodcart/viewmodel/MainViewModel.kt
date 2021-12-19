package com.example.foodblogs.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.foodblogs.retrofit.ApiResult
import com.example.foodblogs.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

     fun getFoodDetails(): LiveData<ApiResult<*>> {

        val mutableLiveData = MutableLiveData<ApiResult<*>>()
        mutableLiveData.postValue(ApiResult.InProgress)
        try {
            viewModelScope.launch {
                withContext(IO) {
                    val response = RetrofitClient().instance.getFoodDetails()
                    val responseBody = response.body()
                    when {
                        response.isSuccessful -> {
                            when (responseBody != null) {
                                true -> {
                                    mutableLiveData.postValue(ApiResult.Success(responseBody))
                                }
                                else -> {
                                    mutableLiveData.postValue(ApiResult.NoData())
                                }
                            }
                        }
                        else -> mutableLiveData.postValue(ApiResult.NoData("Something went wrong! Try again later."))
                    }

                }
            }
        } catch (e: Exception) {
            Log.e("main :", e.message.orEmpty())
            mutableLiveData.postValue(ApiResult.Failure(e))
        }
        return mutableLiveData
    }
}