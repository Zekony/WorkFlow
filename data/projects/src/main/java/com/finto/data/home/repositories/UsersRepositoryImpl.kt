package com.finto.data.home.repositories

import com.finto.domain.home.entities.User
import com.finto.domain.home.repositories.UsersRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import java.util.NoSuchElementException

class UsersRepositoryImpl(
    private val usersDatabase: DatabaseReference,
    private val coroutineScope: CoroutineScope
) : UsersRepository {

    override fun addNewUserToDB(user: User) {
        usersDatabase.child(user.id).setValue(user)
    }

    override fun getUsersProjectIds(user: User): Flow<Result<List<String>>> =
        channelFlow {
            usersDatabase.child(user.id).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    coroutineScope.launch {
                        if (snapshot.exists()) send(
                            Result.success(
                                (snapshot.getValue(User::class.java) ?: return@launch).userProjectsIds
                            )
                        ) else {
                            addNewUserToDB(user)
                            send(Result.failure(NoSuchElementException()))
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.launch {
                        send(Result.failure(error.toException()))
                    }
                }
            })

            awaitClose()
        }

    override fun getUsersList(): Flow<Result<User>> =
        channelFlow {
            usersDatabase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    coroutineScope.launch {
                        if (snapshot.exists()) {
                            snapshot.children.forEach {
                                send(
                                    Result.success(it.getValue(User::class.java) ?: return@launch)
                                )
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.launch {
                        send(Result.failure(error.toException()))
                    }
                }
            })
            awaitClose()
        }

    override fun updateUserProjectsIds(projectId: String, user: User) {
        usersDatabase.child(user.id).setValue(
            user.copy(
                userProjectsIds = user.userProjectsIds.toMutableList().apply { add(projectId) }
            )
        )
    }
}
