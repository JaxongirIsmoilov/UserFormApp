package uz.gita.jaxongir.userformapp.presenter.submitedScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.R


class SubmitedScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val vm = getViewModel<SubmitedScreenViewModelImpl>()
        SubmitedScreenContent(
            uiState = vm.uiState.collectAsState(),
            onEventDispatcher = vm::onEventDispatcher

        )
    }
}

data class User(val id: Int, val name: String, val age: Int)

@Composable
fun SubmitedScreenContent(
    uiState: State<SubmitedScreenContract.UIState>,
    onEventDispatcher: (SubmitedScreenContract.Intent) -> Unit
) {
    val list = listOf(
        User(1, "", 1),
        User(1, "", 1),
        User(1, "", 1),
        User(1, "", 1),
    )
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(content = {
            items(uiState.value.list) {

            }
        }, modifier = Modifier.padding(top = 30.dp))
        
        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
            contentDescription = "backHome", modifier = Modifier
                .padding(20.dp)
                .size(30.dp)
                .clickable {
                    onEventDispatcher.invoke(SubmitedScreenContract.Intent.Back)
                }
        )

    }


}


@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showBackground = true)
fun SubmitedPrev() {
    SubmitedScreenContent(mutableStateOf(SubmitedScreenContract.UIState()), {})
}