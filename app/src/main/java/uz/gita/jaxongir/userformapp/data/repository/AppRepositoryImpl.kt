package uz.gita.jaxongir.userformapp.data.repository

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.ImageTypeEnum
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormData
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormRequest
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import uz.gita.jaxongir.userformapp.utills.myLog2
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val pref: MyPref,
    @ApplicationContext val context: Context
) : AppRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val converter = Gson()
    override fun addDraftedItems(request: FormRequest): Flow<Result<String>> = callbackFlow {
        firestore.collection("Forms").add(request).addOnSuccessListener {
            trySend(Result.success("Success"))
        }
            .addOnFailureListener {
                trySend(Result.failure(IllegalArgumentException("Failed")))
            }

        awaitClose()
    }

    override fun addSavedItems(request: FormRequest): Flow<Result<String>> = callbackFlow {
        firestore.collection("Forms").add(request).addOnSuccessListener {
            trySend(Result.success("Success"))
        }
            .addOnFailureListener {
                trySend(Result.failure(IllegalArgumentException("Failed")))
            }

        awaitClose()
    }


    override fun getAllSavedItemsList(userID: String): Flow<Result<List<FormData>>> = callbackFlow {
        val savedItemList = arrayListOf<FormData>()
        firestore.collection("Forms").whereEqualTo("draft", false).get()
            .addOnSuccessListener {
                myLog2("saved list")
                it.documents.forEach {
                    savedItemList.add(
                        FormData(
                            it.id, converter.fromJson(
                                it.data?.getOrDefault("listComponentIds", "[]").toString(),
                                Array<String>::class.java
                            ).asList(), isDraft = false, userID
                        )
                    )
                }
                trySend(Result.success(savedItemList))
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to get", Toast.LENGTH_SHORT).show()
                trySend(Result.failure(IllegalArgumentException("Failed to get")))
            }

        awaitClose()
    }


    override fun getAllDraftedItemsList(userID: String): Flow<Result<List<FormData>>> =
        callbackFlow {
            val draftedItemsList = arrayListOf<FormData>()
            firestore.collection("Forms").whereEqualTo("draft", true).get()
                .addOnSuccessListener {
                    it.documents.forEach {
                        myLog2("list document $it")
                     draftedItemsList.add(
                            FormData(
                                it.id,
                                converter.fromJson(
                                    it.data?.getOrDefault("listComponentIds", "[]").toString(),
                                    Array<String>::class.java
                                ).asList(),
                                isDraft = true,
                                userID
                            )
                        )

                    }
                    myLog2("size2 :${draftedItemsList.size}")
                    trySend(Result.success(draftedItemsList))
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to get", Toast.LENGTH_SHORT).show()
                    trySend(Result.failure(IllegalArgumentException("Failed to get")))
                }

            awaitClose()
        }

    override fun getComponentByComponentId(componentId: String): Flow<Result<ComponentData>> =
        callbackFlow {
            val componentList = arrayListOf<ComponentData>()
            firestore.collection("Components")
                .whereEqualTo("id", componentId)
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
                                selectedSpinnerText = it.data?.getOrDefault(
                                    "selectedSpinnerText",
                                    ""
                                ).toString(),
                                imageType = converter.fromJson(
                                    it.data?.getOrDefault(
                                        "imageType",
                                        ImageTypeEnum.NONE.toString()
                                    ).toString(), ImageTypeEnum::class.java
                                ),
                                inValues = converter.fromJson(
                                    it.data?.getOrDefault("inValues", "[]").toString(),
                                    Array<String>::class.java
                                ).asList(),

                                )

                        )
                    }
                    trySend(Result.success(componentList.first()))
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
                                selectedSpinnerText = it.data?.getOrDefault(
                                    "selectedSpinnerText",
                                    ""
                                ).toString(),
                                imageType = ImageTypeEnum.NONE,
                                inValues = converter.fromJson(
                                    it.data?.getOrDefault("inValues", "[]").toString(),
                                    Array<String>::class.java
                                ).asList(),
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