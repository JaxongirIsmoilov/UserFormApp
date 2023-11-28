package uz.gita.jaxongir.userformapp.presenter.drafts_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.ui.components.DraftItem

class DraftScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val vm: DraftContract.ViewModel = getViewModel<DraftViewModelImpl>()
        DraftsScreenComponent(
            vm.uiState.collectAsState(),
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
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .height(70.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                onClick = {

                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = Color.White
                )
            }

            Text(
                text = "My Drafts:",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.Center),
                color = Color.White
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            items(uiState.value.drafts.size) {
//                DraftItem(uiState.value.drafts[it]) {
//                    onEventDispatcher.invoke(
//                        DraftContract.Intent.DraftDetails(
//                            uiState.value.drafts[it]
//                        )
//                    )
//                }
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
