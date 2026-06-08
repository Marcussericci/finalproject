package com.example.cityexplorer.data

import com.example.cityexplorer.R
data class Category(val id: String, val name: String)

data class Place(
    val id: String,
    val categoryId: String,
    val name: String,
    val shortDesc: String,
    val longDesc: String,
    val address: String,
    val hours: String,
    val rating: Double,
    val imageRes: Int,  // <- добавили поле для картинки
    var isFavorite: Boolean
)

object DataStore {

    val categories = listOf(
        Category("cafe", "☕ Кафе"),
        Category("restaurant", "🍽️ Рестораны"),
        Category("park", "🌳 Парки"),
        Category("museum", "🏛️ Музеи"),
        Category("landmark", "🏰 Достопримечательности")
    )

    val allPlaces = mutableListOf<Place>()

    fun initPlaces() {
        if (allPlaces.isNotEmpty()) return

        // КАФЕ
        allPlaces.add(Place("c1", "cafe", "Coffee Street", "Лучший кофе в городе",
            "Уютная кофейня с авторским кофе и домашней выпечкой.",
            "ул. Центральная, 15", "08:00-21:00", 4.8, R.drawable.cafe1, false))
        allPlaces.add(Place("c2", "cafe", "Book & Coffee", "Кофейня-библиотека",
            "Тихое место с тысячами книг и ароматным кофе.",
            "пер. Литературный, 7", "10:00-22:00", 4.6, R.drawable.cafe2, false))
        allPlaces.add(Place("c3", "cafe", "Dessert House", "Рай для сладкоежек",
            "Огромный выбор тортов, пирожных и десертов.",
            "пр. Славы, 42", "11:00-23:00", 4.9, R.drawable.cafe3, false))
        allPlaces.add(Place("c4", "cafe", "Green Bean", "Эко-кафе",
            "Веганское меню, свежеобжаренный кофе, внутренний дворик.",
            "ул. Садовая, 28", "09:00-21:00", 4.7, R.drawable.cafe4, false))

        // РЕСТОРАНЫ
        allPlaces.add(Place("r1", "restaurant", "La Piazza", "Итальянская кухня",
            "Аутентичная пицца из дровяной печи, домашняя паста, живая музыка.",
            "ул. Итальянская, 10", "12:00-00:00", 4.9, R.drawable.rest1, false))
        allPlaces.add(Place("r2", "restaurant", "Sakura", "Японский ресторан",
            "Лучшие суши и роллы в городе, свежайшие морепродукты.",
            "ул. Восточная, 5", "11:00-23:00", 4.7, R.drawable.rest2, false))
        allPlaces.add(Place("r3", "restaurant", "Steak House", "Лучшие стейки",
            "Мраморная говядина, приготовленная на открытом огне.",
            "ул. Мясницкая, 22", "12:00-02:00", 4.8, R.drawable.rest3, false))
        allPlaces.add(Place("r4", "restaurant", "Balkan Garden", "Балканская кухня",
            "Плескавица, чайное мясо, домашняя ракия.",
            "ул. Балканская, 8", "11:00-23:00", 4.6, R.drawable.rest4, false))

        // ПАРКИ
        allPlaces.add(Place("p1", "park", "Central Park", "Главный парк города",
            "Вековые дубы, фонтаны, ухоженные аллеи.",
            "Центральный район", "Круглосуточно", 4.8, R.drawable.park1, false))
        allPlaces.add(Place("p2", "park", "Botanical Garden", "Ботанический сад",
            "Редкие растения со всего мира, оранжереи, японский сад.",
            "ул. Ботаническая, 3", "09:00-20:00", 4.9, R.drawable.park2, false))
        allPlaces.add(Place("p3", "park", "Victory Park", "Парк памяти",
            "Мемориальный комплекс, вечный огонь, спортивные площадки.",
            "пр. Победы, 1", "Круглосуточно", 4.7, R.drawable.park3, false))
        allPlaces.add(Place("p4", "park", "Seaside Park", "Парк у воды",
            "Красивая набережная, велодорожки, пляж.",
            "Набережная, 15", "Круглосуточно", 4.8, R.drawable.park4, false))

        // МУЗЕИ
        allPlaces.add(Place("m1", "museum", "Art Museum", "Шедевры искусства",
            "Картины Айвазовского, Репина, Левитана и других великих мастеров.",
            "ул. Музейная, 2", "10:00-18:00", 4.9, R.drawable.museum1, false))
        allPlaces.add(Place("m2", "museum", "History Museum", "История города",
            "Артефакты, старинные документы, интерактивные экспозиции.",
            "пл. Историческая, 1", "10:00-19:00", 4.7, R.drawable.museum2, false))
        allPlaces.add(Place("m3", "museum", "Science Museum", "Музей науки",
            "Всё можно трогать! Экспонаты по физике, химии, космонавтике.",
            "ул. Научная, 12", "10:00-20:00", 4.8, R.drawable.museum3, false))
        allPlaces.add(Place("m4", "museum", "Modern Art Center", "Современное искусство",
            "Инсталляции, медиа-арт, перформансы.",
            "ул. Творческая, 9", "12:00-21:00", 4.6, R.drawable.museum4, false))

        // ДОСТОПРИМЕЧАТЕЛЬНОСТИ
        allPlaces.add(Place("l1", "landmark", "Old Fortress", "Древняя крепость",
            "Крепость XIII века, сохранившиеся стены, смотровая башня.",
            "ул. Крепостная, 1", "09:00-18:00", 4.9, R.drawable.landmark1, false))
        allPlaces.add(Place("l2", "landmark", "Cathedral Square", "Главная площадь",
            "Величественный собор, старинная ратуша, купеческие дома.",
            "пл. Соборная", "Круглосуточно", 4.8, R.drawable.landmark2, false))
        allPlaces.add(Place("l3", "landmark", "Old Town Street", "Историческая улица",
            "Брусчатка, старинные фонари, сувенирные лавки.",
            "ул. Историческая", "Круглосуточно", 4.8, R.drawable.landmark3, false))
        allPlaces.add(Place("l4", "landmark", "Bridge of Love", "Романтичный мост",
            "Мост, где влюблённые оставляют замочки.",
            "наб. Речная", "Круглосуточно", 4.7, R.drawable.landmark4, false))
    }

    fun getPlacesByCategory(categoryId: String): List<Place> {
        return allPlaces.filter { it.categoryId == categoryId }
    }

    fun getFavorites(): List<Place> {
        return allPlaces.filter { it.isFavorite }
    }
}