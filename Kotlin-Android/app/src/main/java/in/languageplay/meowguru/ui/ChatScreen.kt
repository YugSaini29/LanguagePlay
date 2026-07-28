package `in`.languageplay.meowguru.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.languageplay.meowguru.data.Langs

@Composable
fun ChatScreen(
    vm: MeowVm,
    listening: Boolean,
    onMic: () -> Unit,
    onStopMic: () -> Unit,
    onSpeak: (text: String, roman: String, slow: Boolean) -> Unit,
    onTryPron: (target: String, roman: String) -> Unit
) {
    val s by vm.ui.collectAsState()
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(s.msgs.size) {
        if (s.msgs.isNotEmpty()) listState.animateScrollToItem(s.msgs.size - 1)
    }

    Column(Modifier.fillMaxSize()) {

        /* ---------- CAT + STATUS ---------- */
        Box(
            Modifier
                .fillMaxWidth()
                .weight(0.42f),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CatView(mode = s.mode, modifier = Modifier.size(170.dp))
                Spacer(Modifier.height(6.dp))
                if (s.status.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 2.dp
                    ) {
                        Text(
                            s.status, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                            color = Navy, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        /* ---------- CHAT ---------- */
        Surface(
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 12.dp,
            modifier = Modifier.fillMaxWidth().weight(0.58f)
        ) {
            Column(Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {

                LazyColumn(state = listState, modifier = Modifier.weight(1f)) {
                    itemsIndexed(s.msgs) { _, m ->
                        if (m.mine) MyBubble(m) else CatBubble(m, vm, onSpeak, onTryPron)
                        Spacer(Modifier.height(8.dp))
                    }
                }

                /* ---------- INPUT ROW ---------- */
                Row(
                    Modifier.fillMaxWidth().padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        placeholder = { Text("✍️ Ya yahan type karo…", fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.weight(1f),
                        enabled = !s.busy
                    )
                    Spacer(Modifier.width(8.dp))
                    FilledIconButton(
                        onClick = {
                            if (input.isNotBlank()) { vm.send(input.trim()); input = "" }
                        },
                        enabled = !s.busy && input.isNotBlank(),
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = Sky)
                    ) { Icon(Icons.Default.Send, "Bhejo", tint = Color.White) }
                }

                /* ---------- MIC ---------- */
                Box(Modifier.fillMaxWidth().padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                    FilledIconButton(
                        onClick = { if (listening) onStopMic() else onMic() },
                        enabled = !s.busy,
                        modifier = Modifier.size(64.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = if (listening) RedM else OrangeM
                        )
                    ) {
                        Icon(
                            if (listening) Icons.Default.Stop else Icons.Default.Mic,
                            contentDescription = if (listening) "Band karo" else "Bolo",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Text(
                    if (listening) "👂 Bolo… chup hote hi khud bhej dunga"
                    else "🎤 tap karke bolo — Made with ❤️ by Rocket Examica",
                    fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Muted,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun MyBubble(m: Msg) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Surface(
            shape = RoundedCornerShape(18.dp, 18.dp, 4.dp, 18.dp),
            color = Sky,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            Text(
                m.text, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun CatBubble(
    m: Msg,
    vm: MeowVm,
    onSpeak: (String, String, Boolean) -> Unit,
    onTryPron: (String, String) -> Unit
) {
    val s by vm.ui.collectAsState()
    Column(Modifier.fillMaxWidth()) {
        Surface(
            shape = RoundedCornerShape(18.dp, 18.dp, 18.dp, 4.dp),
            color = MaterialTheme.colorScheme.background,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Sky.copy(alpha = 0.35f)),
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Column(Modifier.padding(12.dp)) {
                // 1. target script
                Text(m.speak, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Sky)
                // 2. roman
                if (m.roman.isNotBlank() && m.roman != m.speak)
                    Text("🔤 ${m.roman}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OrangeM)
                // 3. user ki bhasha
                if (m.source.isNotBlank())
                    Text(m.source, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GreenM,
                        modifier = Modifier.padding(top = 3.dp))
                // 4. english
                if (m.english.isNotBlank() && m.english != m.source)
                    Text(m.english, fontSize = 11.5.sp, color = Muted, fontWeight = FontWeight.Medium)

                if (m.tip.isNotBlank())
                    Text("💡 ${m.tip}", fontSize = 11.5.sp, color = Muted,
                        modifier = Modifier.padding(top = 5.dp))

                // buttons
                Row(Modifier.padding(top = 8.dp)) {
                    MiniBtn("🔊") { onSpeak(m.speak, m.roman, false) }
                    Spacer(Modifier.width(6.dp))
                    MiniBtn("🐢 Slow") { onSpeak(m.speak, m.roman, true) }
                    Spacer(Modifier.width(6.dp))
                    MiniBtn("🗣 Try") { onTryPron(m.speak, m.roman) }
                    Spacer(Modifier.width(6.dp))
                    MiniBtn("⭐") { vm.savePhrase(m) }
                }
            }
        }
        // correction
        if (m.corrected.isNotBlank()) {
            Spacer(Modifier.height(5.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFF6E8),
                modifier = Modifier.widthIn(max = 320.dp)
            ) {
                Text(
                    "✏️ ${m.corrected}", fontSize = 12.sp, fontWeight = FontWeight.Bold,
                    color = Color(0xFF8A6420), modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
private fun MiniBtn(label: String, onClick: () -> Unit) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Sky.copy(alpha = 0.4f)),
        onClick = onClick
    ) {
        Text(
            label, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Navy,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
