package uz.gita.jaxongir.userformapp.data.repository

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.data.local.room.dao.Dao
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.data.model.DraftModel
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import uz.gita.jaxongir.userformapp.utills.myLog2
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val pref: MyPref,
    private val dao: Dao,
) : AppRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    override fun getAllRowItemsById(rowId: String): Flow<Result<List<ComponentData>>> =
        callbackFlow {
            val converter = Gson()
            val componentList = arrayListOf<ComponentData>()
            firestore.collection("Components")
                .whereEqualTo("rowId", rowId)
                .get()
                .addOnSuccessListener {
                    it.documents.forEach {
                        componentList.add(
                            ComponentData(
                                id = it.id,
                                userId = it.data?.getOrDefault("userId", "null").toString(),
                                locId = it.data?.getOrDefault("locId", "0").toString()
                                    .toLong(),
                                idEnteredByUser = it.data?.getOrDefault(
                                    "idEnteredByUser",
                                    "null"
                                )
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
                                connectedIds = converter.fromJson(
                                    it.data?.getOrDefault(
                                        "connectedIds",
                                        ""
                                    ).toString(), Array<String>::class.java
                                ).asList(),
                                connectedValues = converter.fromJson(
                                    it.data?.getOrDefault(
                                        "connectedValues",
                                        ""
                                    ).toString(), Array<String>::class.java
                                ).asList(),
                                operators = converter.fromJson(
                                    it.data?.getOrDefault(
                                        "operators",
                                        ""
                                    ).toString(), object : TypeToken<List<String>>() {}.type
                                ),
                                type = converter.fromJson(
                                    it.data?.getOrDefault("type", "").toString(),
                                    ComponentEnum::class.java
                                ),
                                enteredValue = it.data?.getOrDefault("enteredValue", "")
                                    .toString(),
                                isVisible = it.data?.getOrDefault("visible", "true")
                                    .toString() == "true",
                                isRequired = it.data?.getOrDefault("required", false)
                                    .toString() == "true",

                                imgUri = it.data?.getOrDefault("imgUri", "").toString(),
                                ratioX = Integer.parseInt(
                                    it.data?.getOrDefault("ratioX", "0").toString()
                                ),
                                ratioY = Integer.parseInt(
                                    it.data?.getOrDefault("ratioY", "0").toString()
                                ),
                                customHeight = it.data?.getOrDefault("customHeight", "0")
                                    .toString(),
                                backgroundColor = it.data?.getOrDefault(
                                    "backgroundColor",
                                    "${Color.Transparent.toArgb()}"
                                )
                                    .toString().toInt(),
                                rowId = it.data?.getOrDefault("rowId", "0").toString(),
                                weight = it.data?.getOrDefault("weight", "").toString(),
                                draftId = it.data?.getOrDefault(
                                    "draftId",
                                    ""
                                ).toString(),
                            )
                        )
                    }
                    trySend(Result.success(componentList))
                }
                .addOnFailureListener {
                    trySend(Result.failure(Exception("Error occurs")))
                }
        }

    override fun getDraftedItems(draftId: String, userID: String): Flow<Result<List<DraftModel>>> = callbackFlow {
        firestore.collection("Drafts")
            .whereEqualTo("draftId", draftId)
            .get()
            .addOnSuccessListener {
                val list = arrayListOf<DraftModel>()

                it.documents.forEach {
                    list.add(
                        DraftModel(
                            id = it.data?.getOrDefault("draftId", "").toString(),
                            componentId = it.data?.getOrDefault("componentId", "").toString(),
                            value = it.data?.getOrDefault("value", "").toString(),
                            locId = it.data?.getOrDefault("locId", "0").toString().toLong(),
                            name = it.data?.getOrDefault("name", "").toString()
                        )
                    )
                }
                trySend(Result.success(list))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun getSavedComponents(draftId: String, userID: String): Flow<Result<List<DraftModel>>> = callbackFlow {
        firestore.collection("Submits")
            .whereEqualTo("draftId", draftId)
            .get()
            .addOnSuccessListener {
                val list = arrayListOf<DraftModel>()

                it.documents.forEach {
                    list.add(
                        DraftModel(
                            id = it.data?.getOrDefault("draftId", "").toString(),
                            componentId = it.data?.getOrDefault("componentId", "").toString(),
                            value = it.data?.getOrDefault("value", "").toString(),
                            locId = it.data?.getOrDefault("locId", "0").toString().toLong(),
                            name = it.data?.getOrDefault("name", "").toString()
                        )
                    )
                }
                trySend(Result.success(list))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()

    }

    override fun addAsDraft(
        componentData: ComponentData,
        value: String,
        name: String,
        draftId: String,
    ): Flow<Result<String>> = callbackFlow {
        firestore.collection("Drafts")
            .add(
                DraftModel(
                    id = draftId,
                    componentId = componentData.id,
                    value = value,
                    locId = componentData.locId,
                    name = name
                )
            )
            .addOnSuccessListener {
                trySend(Result.success("Success"))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun addAsSaved(
        componentData: ComponentData,
        value: String,
        name: String,
        draftId: String,
    ): Flow<Result<String>> = callbackFlow {
        firestore.collection("Submits")
            .add(
                DraftModel(
                    id = draftId,
                    componentId = componentData.id,
                    value = value,
                    locId = componentData.locId,
                    name = name
                )
            )
            .addOnSuccessListener {
                trySend(Result.success("Success"))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }


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
                    it.documents.forEach {
                        resultList.add(
                            ComponentData(
                                id = it.id,
                                userId = it.data?.getOrDefault("userId", "null").toString(),
                                locId = it.data?.getOrDefault("locId", "0").toString()
                                    .toLong(),
                                idEnteredByUser = it.data?.getOrDefault(
                                    "idEnteredByUser",
                                    "null"
                                )
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
                                connectedIds = converter.fromJson(
                                    it.data?.getOrDefault(
                                        "connectedIds",
                                        ""
                                    ).toString(), Array<String>::class.java
                                ).asList(),
                                connectedValues = converter.fromJson(
                                    it.data?.getOrDefault(
                                        "connectedValues",
                                        ""
                                    ).toString(), Array<String>::class.java
                                ).asList(),
                                operators = converter.fromJson(
                                    it.data?.getOrDefault(
                                        "operators",
                                        ""
                                    ).toString(), object : TypeToken<List<String>>() {}.type
                                ),
                                type = converter.fromJson(
                                    it.data?.getOrDefault("type", "").toString(),
                                    ComponentEnum::class.java
                                ),
                                enteredValue = it.data?.getOrDefault("enteredValue", "")
                                    .toString(),
                                isVisible = it.data?.getOrDefault("visible", "true")
                                    .toString() == "true",
                                isRequired = it.data?.getOrDefault("required", false)
                                    .toString() == "true",
                                imgUri = it.data?.getOrDefault("imgUri", "").toString(),
                                ratioX = Integer.parseInt(
                                    it.data?.getOrDefault("ratioX", "0").toString()
                                ),
                                ratioY = Integer.parseInt(
                                    it.data?.getOrDefault("ratioY", "0").toString()
                                ),
                                customHeight = it.data?.getOrDefault("customHeight", "0")
                                    .toString(),
                                backgroundColor = it.data?.getOrDefault(
                                    "backgroundColor",
                                    "${Color.Transparent.toArgb()}"
                                )
                                    .toString().toInt(),
                                rowId = it.data?.getOrDefault("rowId", "0").toString(),
                                weight = it.data?.getOrDefault("weight", "").toString(),
                                draftId = it.data?.getOrDefault(
                                    "draftId",
                                    ""
                                ).toString(),
                            )
                        )
                    }


                    trySend(Result.success(resultList))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }

            awaitClose()
        }

    override fun updateComponent(componentData: ComponentData): Flow<Result<Unit>> =
        callbackFlow {
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