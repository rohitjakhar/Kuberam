package com.kuberam.android.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuberam.android.data.DataStorePreferenceStorage
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.data.model.ProfileModel
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.data.remote.RemoteDataSource
import com.kuberam.android.utils.Constant.INCOME_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {
    var isLogin: MutableState<Boolean> = mutableStateOf(false)
    val userProfile: MutableState<ProfileModel> = mutableStateOf(ProfileModel("", "", "", ""))
    val allTransaction: MutableState<List<TransactionDetailsModel>> = mutableStateOf(emptyList())
    val expenseData: MutableState<List<CategoryDataModel>> = mutableStateOf(emptyList())
    val incomeData: MutableState<List<CategoryDataModel>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch(IO) {
            isLogin.value = dataStorePreferenceStorage.isLogin.first()
        }
        getAllTransaction()
        getIncomeData()
        getExpenseData()
    }

    fun checkLogin() {
        viewModelScope.launch(IO) {
            isLogin.value = dataStorePreferenceStorage.isLogin.first()
        }
    }

    fun saveProfile(userProfile: ProfileModel) {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.saveProfile(userProfile)
        }
    }

    fun changeLogin(isLogin: Boolean) {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.isLogin(isLogin)
        }
    }

    fun getUserDetails() {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.userProfile.collect {
                userProfile.value = it
            }
        }
    }

    fun addTransaction(transactionDetailsModel: TransactionDetailsModel) {
        viewModelScope.launch(IO) {
            remoteDataSource.addTransaction(
                transactionDetailsModel,
                successListener = {
                    val categoryDataModel = CategoryDataModel(
                        amount = transactionDetailsModel.transactionAmount.toLong(),
                        categoryName = transactionDetailsModel.transactionCategory,
                        transactionType = transactionDetailsModel.transactionType
                    )
                    if (transactionDetailsModel.transactionType == INCOME_DATA) {
                        addIncomeData(categoryDataModel)
                        updateTotalIncomeDate(transactionDetailsModel.transactionAmount.toLong())
                    } else {
                        addExpenseData(categoryDataModel)
                        updateTotalExpenseDate(transactionDetailsModel.transactionAmount.toLong())
                    }
                },
                failureListener = {
                }
            )
        }
    }

    fun getAllTransaction() {
        viewModelScope.launch(IO) {
            remoteDataSource.getAllTransaction(
                successListener = {
                    allTransaction.value = it
                },
                failureListener = {
                }
            )
        }
    }

    fun getIncomeData() {
        viewModelScope.launch(IO) {
            remoteDataSource.getIncomeData(
                successListener = {
                    incomeData.value = it
                },
                failureListener = {
                }
            )
        }
    }

    fun getExpenseData() {
        viewModelScope.launch(IO) {
            remoteDataSource.getExpenseData(
                successListener = {
                    expenseData.value = it
                },
                failureListener = {
                }
            )
        }
    }

    fun createCategory(categoryDataModel: CategoryDataModel) {
        viewModelScope.launch(IO) {
            remoteDataSource.createCategory(
                categoryDataModel,
                successListener = {},
                failureListener = {}
            )
        }
    }

    private fun addIncomeData(categoryDataModel: CategoryDataModel) {
        viewModelScope.launch(IO) {
            remoteDataSource.addIncomeData(
                categoryDataModel = categoryDataModel,
                successListener = {},
                failureListener = {}
            )
        }
    }

    private fun addExpenseData(categoryDataModel: CategoryDataModel) {
        viewModelScope.launch(IO) {
            remoteDataSource.addExpenseData(
                categoryDataModel = categoryDataModel,
                successListener = {},
                failureListener = {}
            )
        }
    }

    private fun updateTotalIncomeDate(incomeAmout: Long) {
        viewModelScope.launch(IO) {
            remoteDataSource.updateTotalIncomeData(
                incomeAmount = incomeAmout,
                successListener = {},
                failureListener = {}
            )
        }
    }

    private fun updateTotalExpenseDate(expenseAmout: Long) {
        viewModelScope.launch(IO) {
            remoteDataSource.updateTotalExpense(
                successListener = {},
                failureListener = {},
                expenseAmount = expenseAmout
            )
        }
    }
}
