package uz.gita.jaxongir.userformapp.data.repository

import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.data.model.Conditions
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import uz.gita.jaxongir.userformapp.utills.myLog
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val pref: MyPref
) : AppRepository {
    override fun login(name: String, password: String): Flow<Result<Unit>> = callbackFlow {
        firestore.collection("Users")
            .whereEqualTo("userName", name)
            .get()
            .addOnSuccessListener {
                if (it.documents.isEmpty()) {
                    trySend(Result.failure(Exception("There is not such user")))
                } else {
                    it.documents.forEach {
                        if (it.data?.getOrDefault("password", "").toString()
                            == password
                        ) {
                            pref.saveId(it.id)
                            myLog(pref.getId())
                            trySend(Result.success(Unit))
                        }
                    }

                }
            }
        awaitClose()
    }

    override fun getComponentsByUserId(userID: String): Flow<Result<List<ComponentData>>> =
        callbackFlow {
            val resultList = arrayListOf<ComponentData>()
            val converter = Gson()
            firestore.collection("Components")
                .whereEqualTo("userId", userID)
                .get()
                .addOnSuccessListener {
                    myLog("success")
                    it.documents.forEach {
                        resultList.add(
                            ComponentData(
                                id = it.id,
                                userId = it.data?.getOrDefault("userId", "null").toString(),
                                locId = it.data?.getOrDefault("locId", "0").toString().toLong(),
                                idEnteredByUser = it.data?.getOrDefault("idEnteredByUser", "null")
                                    .toString(),
                                content = it.data?.getOrDefault("content", "null")
                                    .toString(),
                                textFieldType = converter.fromJson(
                                    it.data?.getOrDefault(
                                        "textFieldType",
                                        "null"
                                    ).toString(), TextFieldType::class.java
                                ),
                                maxLines = Integer.parseInt(
                                    it.data?.getOrDefault("maxLines", "0").toString()
                                ),
                                maxLength = Integer.parseInt(
                                    it.data?.getOrDefault("maxLength", "0").toString()
                                ),
                                minLength = Integer.parseInt(
                                    it.data?.getOrDefault("minLength", "0").toString()
                                ),
                                maxValue = Integer.parseInt(
                                    it.data?.getOrDefault("maxValue", "0").toString()
                                ),
                                minValue = Integer.parseInt(
                                    it.data?.getOrDefault("minValue", "0").toString()
                                ),
                                isMulti = it.data?.getOrDefault("isMulti", "false")
                                    .toString() == "true",
                                variants = converter.fromJson(
                                    it.data?.getOrDefault("variants", "[]").toString(),
                                    Array<String>::class.java
                                ).asList(),
                                selected = converter.fromJson(
                                    it.data?.getOrDefault("selected", "[]").toString(),
                                    Array<Boolean>::class.java
                                ).asList(),
                                conditions = converter.fromJson<List<Conditions>>(
                                    it.data?.getOrDefault("conditions", "[]").toString(),
                                    object : TypeToken<List<Conditions>>() {}.type
                                ),
                                type = converter.fromJson(
                                    it.data?.getOrDefault("type", "").toString(),
                                    ComponentEnum::class.java
                                ),
                                enteredValue = it.data?.getOrDefault("enteredValue", "").toString(),
                                isVisible = it.data?.getOrDefault("visible", "true").toString() == "true"
                            )
                        )
                        myLog("result size:${resultList}")
                    }

                    trySend(Result.success(resultList))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }

            awaitClose()
        }

    override fun updateComponent(componentData: ComponentData): Flow<Result<Unit>> = callbackFlow{
        firestore.collection("Components")
            .document(componentData.id)
            .set(componentData)
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun hasUserInFireBase(userID: String): Flow<Boolean> = callbackFlow {
        firestore.collection("Users")
            .whereEqualTo("userId", userID)
            .get()
            .addOnSuccessListener {
                trySend(true)
            }
            .addOnFailureListener { trySend(false) }

        awaitClose()
    }

}