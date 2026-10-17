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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Blue = Color(0xFF1E88E5)
private val LightBlue = Color(0xFFBBDEFB)
private val Pink = Color(0xFFF8B4B4)
private const val LOGO_URL =
    "https://giaothongvantaitphcm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { AppNavigation() } }
    }
}

/* ================= Navigation ================= */

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(nav) }
        composable("list") { ComponentsListScreen(nav) }
        composable(
            "detail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { entry -> DetailScreen(nav, entry.arguments?.getString("name") ?: "") }
        composable("explore") { ExploreScreen(nav) }
    }
}

/* ================= Màn 1: I'm Ready ================= */

@Composable
fun WelcomeScreen(nav: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.Home, "Logo", tint = Blue, modifier = Modifier.size(120.dp))
        Spacer(Modifier.height(24.dp))
        Text("Jetpack Compose", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            "Jetpack Compose is a modern UI toolkit for building native Android " +
                    "applications using a declarative programming approach.",
            textAlign = TextAlign.Center, fontSize = 14.sp
        )
        Spacer(Modifier.weight(1f))
        Button(
            onClick = { nav.navigate("list") },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(26.dp)
        ) { Text("I'm ready") }
    }
}

/* ================= Màn 2: UI Components List ================= */

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
            "UI Components List", color = Blue, fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        SectionTitle("Display")
        ComponentItem("Text", "Displays text") { nav.navigate("detail/Text") }
        ComponentItem("Image", "Displays an image") { nav.navigate("detail/Image") }

        SectionTitle("Input")
        ComponentItem("TextField", "Input field for text") { nav.navigate("detail/TextField") }
        ComponentItem("PasswordField", "Input field for passwords") { nav.navigate("detail/PasswordField") }

        SectionTitle("Layout")
        ComponentItem("Column", "Arranges elements vertically") { nav.navigate("detail/Column") }
        ComponentItem("Row", "Arranges elements horizontally") { nav.navigate("detail/Row") }

        Spacer(Modifier.height(8.dp))
        ComponentItem("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản", Pink) {
            nav.navigate("explore")
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
}

@Composable
fun ComponentItem(
    title: String,
    desc: String,
    color: Color = LightBlue,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(color, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold)
        Text(desc, fontSize = 13.sp)
    }
}

/* ================= Màn 3: Detail ================= */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(nav: NavHostController, name: String) {
    val title = when (name) {
        "Text" -> "Text Detail"
        "Image" -> "Images"
        "TextField" -> "TextField"
        "PasswordField" -> "PasswordField"
        "Column" -> "Column Layout"
        "Row" -> "Row Layout"
        else -> name
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, color = Blue) },
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
                "Column" -> GridDemo(rows = 3, cols = 1)
                "Row" -> GridDemo(rows = 4, cols = 3)
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
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Ảnh 1: tải từ URL (cần Coil + quyền INTERNET)
        AsyncImage(
            model = LOGO_URL,
            contentDescription = "Ảnh từ URL",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().height(180.dp).clip12()
        )
        Text(LOGO_URL, fontSize = 12.sp, textDecoration = TextDecoration.Underline, textAlign = TextAlign.Center)

        Spacer(Modifier.height(8.dp))

        // Ảnh 2: ảnh trong app (thay bằng ảnh của bạn trong res/drawable)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Ảnh trong app",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().height(180.dp).clip12().background(LightBlue)
        )
        Text("In app", fontSize = 13.sp)
    }
}

private fun Modifier.clip12(): Modifier = this.clip(RoundedCornerShape(12.dp))

@Composable
fun TextFieldDemo() {
    var value by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            placeholder = { Text("Thông tin nhập") },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = if (value.isEmpty()) "Tự động cập nhật dữ liệu theo textfield" else value,
            color = Color.Red,
            fontSize = 13.sp
        )
    }
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

/** Lưới ô xanh: giữa là ô đậm. cols = 1 thì thành Column, nhiều cột thì thành Row. */
@Composable
fun GridDemo(rows: Int, cols: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F5FB), RoundedCornerShape(10.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(cols) { c ->
                    val color = if (cols > 1 && c == cols / 2) Color(0xFF4F86F7) else Color(0xFFA9C0F5)
                    Box(
                        Modifier
                            .weight(1f)
                            .height(52.dp)
                            .background(color, RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    }
}

/* ================= Màn "Tự tìm hiểu": tất cả UI cơ bản ================= */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tự tìm hiểu", color = Blue) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Blue)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // ---- Display ----
            SectionTitle("Display")
            ExploreItem("Text", "Displays text") { Text("Hello Compose", fontSize = 18.sp) }
            ExploreItem("Image", "Displays an image") {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).background(Color.White, RoundedCornerShape(8.dp))
                )
            }

            // ---- Input ----
            SectionTitle("Input")
            ExploreItem("TextField", "Input field for text") {
                var v by remember { mutableStateOf("") }
                OutlinedTextField(v, { v = it }, placeholder = { Text("Nhập...") }, modifier = Modifier.fillMaxWidth())
            }
            ExploreItem("PasswordField", "Input field for passwords") {
                var v by remember { mutableStateOf("") }
                OutlinedTextField(
                    v, { v = it }, placeholder = { Text("Mật khẩu") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            ExploreItem("Toggle", "On/off switch") {
                var on by remember { mutableStateOf(false) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = on, onCheckedChange = { on = it })
                    Spacer(Modifier.width(8.dp))
                    Text(if (on) "ON" else "OFF")
                }
            }
            ExploreItem("Slider", "Selects a value from a range") {
                var s by remember { mutableFloatStateOf(0.3f) }
                Column {
                    Slider(value = s, onValueChange = { s = it })
                    Text("Giá trị: ${(s * 100).toInt()}")
                }
            }
            ExploreItem("DatePicker", "Selects a date and time") {
                var open by remember { mutableStateOf(false) }
                var picked by remember { mutableStateOf("Chưa chọn") }
                val state = rememberDatePickerState()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { open = true }) { Text("Chọn ngày") }
                    Spacer(Modifier.width(12.dp))
                    Text(picked)
                }
                if (open) {
                    DatePickerDialog(
                        onDismissRequest = { open = false },
                        confirmButton = {
                            TextButton(onClick = {
                                state.selectedDateMillis?.let {
                                    picked = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                                }
                                open = false
                            }) { Text("OK") }
                        },
                        dismissButton = { TextButton(onClick = { open = false }) { Text("Hủy") } }
                    ) { DatePicker(state = state) }
                }
            }

            // ---- Selection ----
            SectionTitle("Selection")
            ExploreItem("Button", "Triggers an action when tapped") {
                var n by remember { mutableIntStateOf(0) }
                Button(onClick = { n++ }) { Text("Đã bấm $n lần") }
            }
            ExploreItem("Checkbox", "Selects one or multiple options") {
                var a by remember { mutableStateOf(true) }
                var b by remember { mutableStateOf(false) }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(a, { a = it }); Text("Android")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(b, { b = it }); Text("iOS")
                    }
                }
            }
            ExploreItem("RadioButton", "Selects a single option") {
                val options = listOf("Nam", "Nữ", "Khác")
                var selected by remember { mutableStateOf(options[0]) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    options.forEach { o ->
                        RadioButton(selected = selected == o, onClick = { selected = o })
                        Text(o)
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
            ExploreItem("Picker", "Selects a value from a list") {
                val items = listOf("Kotlin", "Java", "Swift")
                var expanded by remember { mutableStateOf(false) }
                var selected by remember { mutableStateOf(items[0]) }
                Box {
                    OutlinedButton(onClick = { expanded = true }) { Text(selected) }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        items.forEach { i ->
                            DropdownMenuItem(text = { Text(i) }, onClick = { selected = i; expanded = false })
                        }
                    }
                }
            }

            // ---- Navigation ----
            SectionTitle("Navigation")
            ExploreItem("NavigationView", "Navigation container with title and back button") {
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(8.dp)).padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Blue)
                    Spacer(Modifier.width(12.dp))
                    Text("Tiêu đề", color = Blue, fontWeight = FontWeight.Medium)
                }
            }
            ExploreItem("TabBar", "Switches between main sections") {
                var tab by remember { mutableIntStateOf(0) }
                val tabs = listOf("Home", "Settings", "Profile")
                Column {
                    TabRow(selectedTabIndex = tab) {
                        tabs.forEachIndexed { i, t ->
                            Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) })
                        }
                    }
                    Text("Đang ở tab: ${tabs[tab]}", modifier = Modifier.padding(top = 8.dp))
                }
            }
            ExploreItem("Menu", "Shows a menu with multiple actions") {
                var expanded by remember { mutableStateOf(false) }
                var last by remember { mutableStateOf("Chưa chọn") }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box {
                        IconButton(onClick = { expanded = true }) { Icon(Icons.Default.MoreVert, "Menu") }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            listOf("Sửa", "Chia sẻ", "Xóa").forEach { a ->
                                DropdownMenuItem(text = { Text(a) }, onClick = { last = a; expanded = false })
                            }
                        }
                    }
                    Text("Đã chọn: $last")
                }
            }

            // ---- Content ----
            SectionTitle("Content")
            ExploreItem("List", "Displays a list of items") {
                Column {
                    listOf("Item 1", "Item 2", "Item 3").forEach {
                        ListItem(
                            headlineContent = { Text(it) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }
                }
            }
            ExploreItem("Card", "Container with content and style") {
                Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                    Text("Nội dung trong Card", modifier = Modifier.padding(16.dp))
                }
            }
            ExploreItem("Divider", "Separates content into sections") {
                Column {
                    Text("Phần trên")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("Phần dưới")
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun ExploreItem(title: String, desc: String, demo: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(LightBlue, RoundedCornerShape(8.dp))
            .padding(14.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold)
        Text(desc, fontSize = 13.sp)
        Spacer(Modifier.height(10.dp))
        demo()
    }
}