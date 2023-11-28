package uz.gita.jaxongir.userformapp.presenter.submitedScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel


class SubmitedScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val vm = getViewModel<SubmitedScreenViewModelImpl>()
        SubmitedScreenContent()
    }
}

data class User(val id: Int, val name: String, val age: Int)

@Composable
fun SubmitedScreenContent(

) {
    val list = listOf(
        User(1, "", 1),
        User(1, "", 1),
        User(1, "", 1),
        User(1, "", 1),
    )
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(content = {
            items(list.size) {

            }
        })
    }
}


//@Composable
//@Preview(showBackground = true)
//fun SubmitedPrev() {
//    SubmitedScreenContent()
//}