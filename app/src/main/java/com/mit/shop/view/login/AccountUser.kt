package com.mit.shop.view.login

data class AccountUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String? = null,
    val gender: String? = null,
    val birthDate: String? = null
) {
    companion object {

        private val users: MutableList<AccountUser> = mutableListOf(
            AccountUser(
                id = 1,
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                password = "password123",
                phoneNumber = "123-456-7890",
                gender = "Male",
                birthDate = "1990-01-01"
            ),
            AccountUser(
                id = 2,
                firstName = "Jane",
                lastName = "Smith",
                email = "jane.smith@example.com",
                password = "securePass!",
                phoneNumber = "987-654-3210",
                gender = "Female",
                birthDate = "1985-05-15"
            ),
            AccountUser(
                id = 3,
                firstName = "Alice",
                lastName = "Jones",
                email = "alice.jones@example.com",
                password = "mypassword",
                phoneNumber = "555-123-4567",
                gender = "Female",
                birthDate = "1992-09-30"
            ),
            AccountUser(
                id = 4,
                firstName = "Bob",
                lastName = "Brown",
                email = "bob.brown@example.com",
                password = "password456",
                phoneNumber = "555-987-6543",
                gender = "Male",
                birthDate = "1988-11-22"
            )
        )

        fun addUser(user: AccountUser) {
            users.add(user)
        }

        fun getUser(email: String): AccountUser? {
            return users.find { it.email == email }
        }

        fun getAllUsers(): List<AccountUser> {
            return users
        }


    }
}
