package com.mit.shop.network.account

import com.mit.shop.model.AccountModel


data class AccountResponse(
    val success: Boolean,
    val message: String?,
    val account: AccountModel? // Changed from Int? to AccountModel?
)

