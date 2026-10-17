package com.example.bai1tuan4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

private val Blue = Color(0xFF1E88E5)
private val LightBlue = Color(0xFFBBDEFB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { AppNavigation() }
        }
    }
}

/* ---------------- Navigation ---------------- */

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(nav) }
        composable("list") { ComponentsListScreen(nav) }
        composable(
            "detail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { entry ->
            DetailScreen(nav, entry.arguments?.getString("name") ?: "")
        }
    }
}

/* ---------------- Màn 1: I'm Ready ---------------- */

@Composable
fun WelcomeScreen(nav: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Logo",
            tint = Blue,
            modifier = Modifier.size(120.dp)
        )
        Spacer(Modifier.height(24.dp))
        Text("Jetpack Compose", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            "Jetpack Compose is a modern UI toolkit for building native Android " +
                    "applications using a declarative programming approach.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        Spacer(Modifier.weight(1f))
        Button(
            onClick = { nav.navigate("list") },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(26.dp)
        ) { Text("I'm ready") }
    }
}

/* ---------------- Màn 2: UI Components List ---------------- */

@Composable
fun ComponentsListScreen(nav: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "UI Components List",
            color = Blue, fontSize = 20.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        Section("Display")
        ComponentItem("Text", "Displays text", nav)
        ComponentItem("Image", "Displays an image", nav)

        Section("Input")
        ComponentItem("TextField", "Input field for text", nav)
        ComponentItem("PasswordField", "Input field for passwords", nav)

        Section("Layout")
        ComponentItem("Column", "Arranges elements vertically", nav)
        ComponentItem("Row", "Arranges elements horizontally", nav)
    }
}

@Composable
fun Section(title: String) {
    Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
}

@Composable
fun ComponentItem(title: String, desc: String, nav: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(LightBlue, RoundedCornerShape(8.dp))
            .clickable { nav.navigate("detail/$title") }
            .padding(14.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold)
        Text(desc, fontSize = 13.sp)
    }
}

/* ---------------- Màn 3: Detail ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(nav: NavHostController, name: String) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("$name Detail", color = Blue) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Blue)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (name) {
                "Text" -> TextDemo()
                "Image" -> ImageDemo()
                "TextField" -> TextFieldDemo()
                "PasswordField" -> PasswordDemo()
                "Column" -> ColumnDemo()
                "Row" -> RowDemo()
            }
        }
    }
}

@Composable
fun TextDemo() {
    val text = buildAnnotatedString {
        append("The ")
        withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("quick ") }
        withStyle(SpanStyle(color = Color(0xFFB8860B), fontSize = 30.sp)) { append("Brown\n") }
        append("fox ")
        withStyle(SpanStyle(letterSpacing = 6.sp)) { append("jumps ") }
        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) { append("over\n") }
        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append("the ") }
        withStyle(SpanStyle(fontFamily = FontFamily.Cursive, fontWeight = FontWeight.Bold)) { append("lazy ") }
        append("dog.")
    }
    Text(text, fontSize = 24.sp, lineHeight = 40.sp)
}

@Composable
fun ImageDemo() {
    // Thay bằng ảnh của bạn trong res/drawable nếu muốn
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Demo image",
        modifier = Modifier.size(200.dp).background(LightBlue, RoundedCornerShape(12.dp))
    )
}

@Composable
fun TextFieldDemo() {
    var value by remember { mutableStateOf("") }
    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        label = { Text("Nhập văn bản") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordDemo() {
    var pass by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Mật khẩu") },
            visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = show, onCheckedChange = { show = it })
            Text("Hiện mật khẩu")
        }
    }
}

@Composable
fun ColumnDemo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(3) { Box(Modifier.size(80.dp).background(Blue, RoundedCornerShape(8.dp)), Alignment.Center) { Text("${it + 1}", color = Color.White) } }
    }
}

@Composable
fun RowDemo() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        repeat(3) { Box(Modifier.size(80.dp).background(Blue, RoundedCornerShape(8.dp)), Alignment.Center) { Text("${it + 1}", color = Color.White) } }
    }
}