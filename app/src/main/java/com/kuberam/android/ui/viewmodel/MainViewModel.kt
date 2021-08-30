package com.kuberam.android.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.kuberam.android.data.local.DataStorePreferenceStorage
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.data.model.ProfileDataModel
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.data.remote.AuthRepo
import com.kuberam.android.data.remote.FetchTransaction
import com.kuberam.android.data.remote.RemoteDataSource
import com.kuberam.android.utils.Constant.INCOME_DATA
import com.kuberam.android.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Currency
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStorePreferenceStorage: DataStorePreferenceStorage,
    private val remoteDataSource: RemoteDataSource,
    private val authRepo: AuthRepo,
    private val fetchTransaction: FetchTransaction
) : ViewModel() {
    var isLogin: MutableState<Boolean> = mutableStateOf(false)
    val userProfileData: MutableState<NetworkResponse<ProfileDataModel>> =
        mutableStateOf(NetworkResponse.Loading())
    val allTransaction: MutableState<NetworkResponse<List<TransactionDetailsModel>>> =
        mutableStateOf(NetworkResponse.Loading())
    val expenseData: MutableState<List<CategoryDataModel>> =
        mutableStateOf(arrayListOf())
    val incomeData: MutableState<List<CategoryDataModel>> =
        mutableStateOf(arrayListOf())
    val loginState: MutableState<NetworkResponse<String>> =
        mutableStateOf(NetworkResponse.Failure(""))
    val firstTime: MutableState<Boolean> = mutableStateOf(true)
    val appLock: MutableState<Boolean> = mutableStateOf(false)
    val darkTheme: MutableState<Boolean> = mutableStateOf(false)
    val currency: MutableState<String> = mutableStateOf("INR")

    fun firstTime() {
        viewModelScope.launch(IO) {
            firstTime.value = dataStorePreferenceStorage.isFirstTime.first()
        }
    }

    fun changeCurrency(currency: String) {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.changeCurrency(currency)
        }
    }

    fun getCurrentCurrency() {
        viewModelScope.launch(IO) {
            currency.value =
                Currency.getInstance(dataStorePreferenceStorage.currenetCurrency.first()).symbol
        }
    }

    fun changeFirstTime() {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.firstTime(false)
        }
    }

    fun checkAppLock() {
        viewModelScope.launch(IO) {
            appLock.value = dataStorePreferenceStorage.isLockEnable.first()
        }
    }

    fun changeAppLock(isLockEnable: Boolean) {
        viewModelScope.launch {
            dataStorePreferenceStorage.isLockEnable(isLockEnable)
        }
    }

    fun checkTheme() {
        viewModelScope.launch(IO) {
            darkTheme.value = dataStorePreferenceStorage.isDarkTheme.first()
        }
    }

    fun changeTheme(isDarkTheme: Boolean) {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.darkTheme(isDarkTheme)
            darkTheme.value = isDarkTheme
        }
    }

    fun checkLogin() {
        viewModelScope.launch(IO) {
            try {
                isLogin.value = dataStorePreferenceStorage.isLogin.first()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isLogin.value = dataStorePreferenceStorage.isLogin.first()
                }
            }
        }
    }

    fun saveProfile(userProfileData: ProfileDataModel) {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.saveProfile(userProfileData)
        }
    }

    fun changeLogin(isLogin: Boolean) {
        viewModelScope.launch(IO) {
            dataStorePreferenceStorage.isLogin(isLogin)
        }
    }

    fun getUserDetails() {
        viewModelScope.launch(IO) {
            authRepo.getUserProfile(
                successListener = {
                    userProfileData.value = NetworkResponse.Success(it)
                },
                failureListener = {
                    userProfileData.value =
                        NetworkResponse.Failure(it.localizedMessage ?: "Unknown Error")
                }
            )
        }
    }

    fun addTransaction(
        transactionDetailsModel: TransactionDetailsModel,
        successListener: () -> Unit,
        failureListener: () -> Unit
    ) {
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
                    successListener.invoke()
                },
                failureListener = {
                    failureListener.invoke()
                }
            )
        }
    }

    fun getAllTransaction() {
        viewModelScope.launch(IO) {
            fetchTransaction.getAllTransaction(
                successListener = {
                    allTransaction.value = NetworkResponse.Success(data = it)
                },
                failureListener = {
                    allTransaction.value =
                        NetworkResponse.Failure(it.localizedMessage ?: "Unknown Error")
                }
            )
        }
    }

    fun getIncomeData() {
        viewModelScope.launch(IO) {
            fetchTransaction.getIncomeData(
                successListener = {
                    incomeData.value = it
                },
                failureListener = {
                    incomeData.value =
                        emptyList()
                }
            )
        }
    }

    fun getExpenseData() {
        viewModelScope.launch(IO) {
            fetchTransaction.getExpenseData(
                successListener = {
                    expenseData.value = it
                },
                failureListener = {
                    expenseData.value =
                        emptyList()
                }
            )
        }
    }

    fun createCategory(
        categoryDataModel: CategoryDataModel,
        successListener: () -> Unit,
        failureListener: () -> Unit
    ) {
        viewModelScope.launch(IO) {
            remoteDataSource.createCategory(
                categoryDataModel,
                successListener = {
                    successListener.invoke()
                },
                failureListener = {
                    failureListener.invoke()
                }
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

    fun loginuser(
        context: Context,
        auth0: Auth0,
    ) {
        authRepo.loginUser(
            context, auth0,
            successListener = {
                authRepo.loadProfile(
                    it.accessToken,
                    auth0,
                    successListener = { it ->
                        val profileModel = ProfileDataModel(
                            name = it.name ?: "",
                            email = it.email ?: "",
                            profileUrl = it.pictureURL ?: "",
                            userId = it.getId() ?: "",
                        )
                        viewModelScope.launch(IO) {
                            authRepo.addUserToFirebase(
                                userid = profileModel.userId,
                                profileDataModel = profileModel,
                                successListener = {
                                    changeLogin(true)
                                    saveProfile(
                                        profileModel
                                    )
                                    loginState.value = NetworkResponse.Success("Success")
                                },
                                failureListener = {
                                    loginState.value = NetworkResponse.Failure(
                                        it.localizedMessage ?: "Unknown Error"
                                    )
                                }
                            )
                        }
                    },
                    failureListener = {
                        loginState.value =
                            NetworkResponse.Failure(it.localizedMessage ?: "Unknown Error")
                    }
                )
            },
            failureListener = {
                loginState.value = NetworkResponse.Failure(it.localizedMessage ?: "Unknown Error")
            }
        )
    }

    fun logoutUser(
        auth0: Auth0,
        context: Context,
        successListener: () -> Unit,
        failureListener: () -> Unit
    ) {
        viewModelScope.launch(IO) {
            authRepo.logoutUser(
                auth = auth0,
                context = context,
                successListener = {
                    successListener.invoke()
                },
                failureListener = {
                    failureListener.invoke()
                }
            )
        }
    }

    fun deleteTransaction(transactionDetailsModel: TransactionDetailsModel) {
        viewModelScope.launch(IO) {
            remoteDataSource.deleteTransaction(
                transactionDetailsModel,
                successListener = {
                    val categoryDataModel = CategoryDataModel(
                        amount = -transactionDetailsModel.transactionAmount.toLong(),
                        categoryName = transactionDetailsModel.transactionCategory,
                        transactionType = transactionDetailsModel.transactionType
                    )
                    if (transactionDetailsModel.transactionType == INCOME_DATA) {
                        addIncomeData(categoryDataModel)
                        updateTotalIncomeDate(categoryDataModel.amount)
                    } else {
                        addExpenseData(categoryDataModel)
                        updateTotalExpenseDate(categoryDataModel.amount)
                    }
                },
                failureListener = {}
            )
        }
    }

    fun uploadFeedback(
        feedbackText: String,
        successListener: () -> Unit,
        failureListener: () -> Unit
    ) {
        viewModelScope.launch(IO) {
            remoteDataSource.addFeedback(
                feedbackText = feedbackText,
                successListener = {
                    successListener.invoke()
                },
                failureListener = {
                    failureListener.invoke()
                }
            )
        }
    }
}
