package com.example.cityexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.cityexplorer.data.Category
import com.example.cityexplorer.data.DataStore
import com.example.cityexplorer.data.Place
import com.example.cityexplorer.ui.theme.CityExplorerTheme
import androidx.compose.ui.draw.clip

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataStore.initPlaces()
        setContent {
            CityExplorerTheme {
                val view = LocalView.current
                SideEffect {
                    val window = (view.context as androidx.activity.ComponentActivity).window
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                    window.statusBarColor = android.graphics.Color.parseColor("#006D5B")
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    CityExplorerApp()
                }
            }
        }
    }
}

@Composable
fun CityExplorerApp() {
    var showFavorites by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    fun toggleFavorite(place: Place) {
        val p = DataStore.allPlaces.find { it.id == place.id }
        if (p != null) {
            p.isFavorite = !p.isFavorite
        }
    }

    when {
        selectedPlace != null -> {
            DetailScreen(selectedPlace!!, { selectedPlace = null }, ::toggleFavorite)
        }
        showFavorites -> {
            FavoritesScreen({ showFavorites = false }, { selectedPlace = it })
        }
        selectedCategory != null -> {
            CategoryScreen(selectedCategory!!, { selectedCategory = null }, { selectedPlace = it }, ::toggleFavorite)
        }
        else -> {
            HomeScreen({ selectedCategory = it }, { showFavorites = true })
        }
    }
}

@Composable
fun HomeScreen(
    onCategoryClick: (Category) -> Unit,
    onFavoritesClick: () -> Unit
) {
    var search by remember { mutableStateOf("") }

    val filtered = if (search.isEmpty()) {
        DataStore.categories
    } else {
        DataStore.categories.filter { it.name.contains(search, ignoreCase = true) }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF006D5B))
                .padding(16.dp)
                .padding(top = 12.dp)
        ) {
            Text(
                text = "City Explorer",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = onFavoritesClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("❤️", color = Color(0xFF006D5B))
            }
        }

        TextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            placeholder = { Text("🔍 Поиск категории...") },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5)
            )
        )

        if (filtered.isEmpty()) {
            Text("Ничего не найдено", modifier = Modifier.padding(32.dp))
        } else {
            LazyColumn {
                items(filtered) { cat ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable { onCategoryClick(cat) },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(cat.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("${DataStore.getPlacesByCategory(cat.id).size} мест", color = Color.Gray)
                            }
                            Text("→", fontSize = 20.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryScreen(
    category: Category,
    onBack: () -> Unit,
    onPlaceClick: (Place) -> Unit,
    onFavorite: (Place) -> Unit
) {
    var search by remember { mutableStateOf("") }
    val places = DataStore.getPlacesByCategory(category.id)
    val filtered = if (search.isEmpty()) places else places.filter { it.name.contains(search, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF006D5B)).padding(16.dp).padding(top = 12.dp)
        ) {
            Text("← Назад", fontSize = 18.sp, color = Color.White, modifier = Modifier.clickable { onBack() })
            Text(category.name, fontSize = 18.sp, color = Color.White, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }

        TextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            placeholder = { Text("🔍 Поиск места...") },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5)
            )
        )

        if (filtered.isEmpty()) {
            Text("Места не найдены", modifier = Modifier.padding(32.dp))
        } else {
            LazyColumn {
                items(filtered) { place ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable { onPlaceClick(place) },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            androidx.compose.foundation.Image(
                                painter = painterResource(place.imageRes),
                                contentDescription = place.name,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.weight(1f).padding(start = 12.dp)
                            ) {
                                Text(place.name, fontWeight = FontWeight.Bold)
                                Text(place.shortDesc, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
                                Text("★ ${place.rating}", fontSize = 12.sp, color = Color(0xFFFFB74D))
                            }
                            Text(
                                text = if (place.isFavorite) "❤️" else "🤍",
                                fontSize = 24.sp,
                                modifier = Modifier.clickable { onFavorite(place) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(
    place: Place,
    onBack: () -> Unit,
    onFavorite: (Place) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF006D5B)).padding(16.dp).padding(top = 12.dp)
        ) {
            Text("← Назад", fontSize = 18.sp, color = Color.White, modifier = Modifier.clickable { onBack() })
            Text(place.name, fontSize = 18.sp, color = Color.White, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, maxLines = 1)
            Text(if (place.isFavorite) "❤️" else "🤍", fontSize = 24.sp, modifier = Modifier.clickable { onFavorite(place) })
        }

        androidx.compose.foundation.Image(
            painter = painterResource(place.imageRes),
            contentDescription = place.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(place.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("★ ${place.rating}", fontSize = 14.sp, color = Color(0xFFFFB74D))

            Spacer(modifier = Modifier.height(16.dp))
            Text("ОПИСАНИЕ", fontWeight = FontWeight.Bold, color = Color(0xFF006D5B))
            Text(place.longDesc, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(12.dp))
            Text("АДРЕС", fontWeight = FontWeight.Bold, color = Color(0xFF006D5B))
            Text(place.address, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(12.dp))
            Text("ЧАСЫ РАБОТЫ", fontWeight = FontWeight.Bold, color = Color(0xFF006D5B))
            Text(place.hours, fontSize = 14.sp)
        }
    }
}

@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
    onPlaceClick: (Place) -> Unit
) {
    val favorites = DataStore.getFavorites()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF006D5B)).padding(16.dp).padding(top = 12.dp)
        ) {
            Text("← Назад", fontSize = 18.sp, color = Color.White, modifier = Modifier.clickable { onBack() })
            Text("❤️ Избранное", fontSize = 18.sp, color = Color.White, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }

        if (favorites.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("❤️", fontSize = 60.sp)
                Text("Нет избранных мест", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Нажмите ❤️ на карточке места", fontSize = 14.sp, color = Color.Gray)
            }
        } else {
            LazyColumn {
                items(favorites) { place ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable { onPlaceClick(place) },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            androidx.compose.foundation.Image(
                                painter = painterResource(place.imageRes),
                                contentDescription = place.name,
                                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                                Text(place.name, fontWeight = FontWeight.Bold)
                                Text(place.shortDesc, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
                                Text("★ ${place.rating}", fontSize = 12.sp, color = Color(0xFFFFB74D))
                            }
                        }
                    }
                }
            }
        }
    }
}

