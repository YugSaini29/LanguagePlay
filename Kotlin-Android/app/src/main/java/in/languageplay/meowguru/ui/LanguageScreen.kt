package `in`.languageplay.meowguru.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.languageplay.meowguru.data.Lang
import `in`.languageplay.meowguru.data.Langs

/**
 * 2-step wizard: pehle "meri bhasha", phir "kya seekhna hai" — web jaisa.
 */
@Composable
fun LanguageScreen(onDone: (source: String, target: String) -> Unit) {

    var step by remember { mutableIntStateOf(1) }
    var source by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("") }

    val filtered = remember(query, step) {
        val all = Langs.ALL.filter { step == 1 || it.name != source }
        if (query.isBlank()) all
        else all.filter {
            it.name.contains(query, true) || it.native.contains(query, true)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BgLight, Color2(0xFFCBE6FF), Color2(0xFFFFE8C9)))
            )
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(18.dp))

        CatView(mode = CatMode.IDLE, modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally))

        Spacer(Modifier.height(10.dp))

        Text(
            if (step == 1) "🐱 Meow Guru" else "Kya seekhna hai?",
            fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Navy,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            if (step == 1) "Pehle batao — aap kaunsi bhasha bolte ho?"
            else "Aap $source bolte ho. Ab kaunsi bhasha seekhni hai?",
            fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Muted,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 4.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("🔍 Bhasha dhundo…") },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        LazyColumn(Modifier.weight(1f)) {
            val indian = filtered.filter { it.indian }
            val world = filtered.filter { !it.indian }

            if (indian.isNotEmpty()) {
                item { SectionHead("🇮🇳 Indian Languages") }
                items(indian) { l -> LangRow(l) { pick(l, step, source, { source = it }, { step = it }, { query = "" }, onDone) } }
            }
            if (world.isNotEmpty()) {
                item { SectionHead("🌍 World Languages") }
                items(world) { l -> LangRow(l) { pick(l, step, source, { source = it }, { step = it }, { query = "" }, onDone) } }
            }
            item { Spacer(Modifier.height(20.dp)) }
        }

        if (step == 2) {
            TextButton(
                onClick = { step = 1; query = "" },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { Text("← Wapas", color = Muted, fontWeight = FontWeight.Bold) }
        }
    }
}

private fun pick(
    l: Lang, step: Int, source: String,
    setSource: (String) -> Unit, setStep: (Int) -> Unit, clearQ: () -> Unit,
    onDone: (String, String) -> Unit
) {
    if (step == 1) {
        setSource(l.name); setStep(2); clearQ()
    } else {
        onDone(source, l.name)
    }
}

@Composable
private fun SectionHead(t: String) {
    Text(
        t, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Muted,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun LangRow(l: Lang, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(l.native, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
                Text(l.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Muted)
            }
            Text("→", fontSize = 18.sp, color = OrangeM, fontWeight = FontWeight.ExtraBold)
        }
    }
}

private fun Color2(v: Long) = androidx.compose.ui.graphics.Color(v)
