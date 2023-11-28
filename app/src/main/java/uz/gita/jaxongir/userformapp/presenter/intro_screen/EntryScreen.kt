package uz.gita.jaxongir.userformapp.presenter.intro_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.R

class EntryScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: EntryScreenContract.ViewModel = getViewModel<EntryScreenViewModelImpl>()
        EntryScreenContent(
            uiState = viewModel.uiState.collectAsState(),
            onEventDispatchers = viewModel::onEventDispatcher
        )
    }
}
@Composable
fun EntryScreenContent(
    uiState: State<EntryScreenContract.UIState>,
    onEventDispatchers: (EntryScreenContract.Intent) -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFFF3951))
                .height(70.dp)
        ) {
            Text(
                text = "User: ${uiState.value.userName}",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, Color(0xFFFF7686), RoundedCornerShape(10.dp))
                .clickable {
                    onEventDispatchers.invoke(EntryScreenContract.Intent.MoveToAdd)
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_note_add_24),
                contentDescription = "add",
                tint = Color(0xFFFF3159)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Add New Form")
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = "arrow",
                tint = Color(0xFFFF3159)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, Color(0xFFFF7686), RoundedCornerShape(10.dp))
                .clickable {
                    onEventDispatchers.invoke(EntryScreenContract.Intent.MoveToSubmit)
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_playlist_add_check_24),
                contentDescription = "add",
                tint = Color(0xFFFF3159)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "My Submitted Forms")
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = "arrow",
                tint = Color(0xFFFF3159)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, Color(0xFFFF7686), RoundedCornerShape(10.dp))
                .clickable {
                    onEventDispatchers.invoke(EntryScreenContract.Intent.MoveToDraft)
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_note_24),
                contentDescription = "add",
                tint = Color(0xFFFF3159)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "My Drafts")
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = "arrow",
                tint = Color(0xFFFF3156)
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun EntryPreview() {
    EntryScreenContent(mutableStateOf(EntryScreenContract.UIState()), {})
}