package uz.gita.jaxongir.userformapp.presenter.submitedScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.ui.components.SubmitedItem


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

@Composable
fun SubmitedScreenContent(
    uiState: State<SubmitedScreenContract.UIState>,
    onEventDispatcher: (SubmitedScreenContract.Intent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
            contentDescription = "backHome", modifier = Modifier
                .padding(20.dp)
                .size(30.dp)
                .clickable {
                    onEventDispatcher.invoke(SubmitedScreenContract.Intent.Back)
                }, tint = Color(0xFFFF3951)
        )

        LazyColumn(content = {
            items(uiState.value.list) {
                SubmitedItem(id = it.id, itemCount = it.listComponents.size, formEntity = it)
            }
        }, modifier = Modifier.padding(top = 30.dp))


    }


}


@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showBackground = true)
fun SubmitedPrev() {
    SubmitedScreenContent(mutableStateOf(SubmitedScreenContract.UIState()), {})
}