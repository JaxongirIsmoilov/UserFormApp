package uz.gita.jaxongir.userformapp.presenter.drafts_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.ui.components.DraftItem
import uz.gita.jaxongir.userformapp.utills.myLog2

class DraftScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val vm: DraftContract.ViewModel = getViewModel<DraftViewModelImpl>()
        DraftsScreenComponent(
            uiState = vm.uiState.collectAsState(),
            onEventDispatcher = vm::onEventDispatcher
        )

    }
}

@Composable
fun DraftsScreenComponent(
    uiState: State<DraftContract.UIState>,
    onEventDispatcher: (DraftContract.Intent) -> Unit
) {


    Column {
        Box(
            modifier = Modifier
                .background(Color(0xFFFF3951))
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Drafts",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp

            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "backHome", modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 10.dp)
                    .size(30.dp)
                    .clickable {
                        onEventDispatcher.invoke(DraftContract.Intent.BackToMain)
                    }, tint = Color.White
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            myLog2("list size: ${uiState.value.list}")
            items(uiState.value.list) {
                DraftItem(entity = it) {
                    onEventDispatcher.invoke(DraftContract.Intent.ClickItem(it.listComponentIds))
                }
            }
        }
    }

}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DraftScreenPreview() {
    DraftsScreenComponent(uiState = mutableStateOf(DraftContract.UIState()), onEventDispatcher = {})
}
