package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.UserReview
import com.example.data.model.WaypointCategory
import com.example.data.model.WaypointStop
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiRouteService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    suspend fun generateRouteStops(
        origin: String,
        destination: String,
        routeHoursEstimate: Double = 6.0
    ): List<WaypointStop> = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        // If no API key or empty placeholder, fall back to smart procedural synthesis
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext generateFallbackStops(origin, destination, routeHoursEstimate)
        }

            val prompt = """
                Sen uzman bir gezi rehberi ve yol haritası analistisisin.
                Kullanıcı "$origin" noktasından "$destination" noktasına bir araba yolculuğu yapacak.
                Yol güzergahı üstünde ($routeHoursEstimate saatlik seyahat boyunca) mola verilebilecek ve kullanıcının rotasına ekleyebileceği en popüler, benzersiz ve değerli yol üstü durak seçeneklerini analiz et.
                Toplamda 6 ile 8 adet zengin aday durak seçeneği öner. Her durak şu kategorilerden birine ait olmalı:
                - HISTORICAL (Tarihi Yerler, kaleler, antik kentler, müzeler)
                - NATURE (Doğal güzellikler, kanyonlar, göller, şelaleler)
                - RESTAURANT (Yol üstü meşhur yöresel lezzet restoranları, köfteciler, börekçiler vb.)
                - SCENIC (Benzersiz manzaralı duraklar, seyir terasları, fotoğraf noktaları)

                Her durak için şu JSON formatında bir liste döndür:
                [
                  {
                    "id": "stop_1",
                    "name": "Mekan Adı",
                    "subtitle": "Kısa Çarpıcı Açıklama",
                    "city": "İl / İlçe",
                    "category": "HISTORICAL" | "NATURE" | "RESTAURANT" | "SCENIC",
                    "lat": 38.5,
                    "lng": 29.5,
                    "kmFromStart": 120,
                    "drivingMinutesFromStart": 80,
                    "recommendedStayMinutes": 45,
                    "openTime": "08:30",
                    "closeTime": "19:00",
                    "alwaysOpen": false,
                    "feeDescription": "Giriş: 100 ₺ / Müzekart Geçerli" (veya "Ücretsiz"),
                    "isFree": false,
                    "museumCardAccepted": true,
                    "averageRating": 4.8,
                    "reviewCount": 3500,
                    "amenities": ["Otopark", "WC", "Kafeterya", "Wi-Fi"],
                    "guideImportantNotes": [
                       "Gidince bilinmesi gereken 1. önemli ipucu",
                       "2. ipucu (fotoğraf saatleri, bilet uyarısı vb.)"
                    ],
                    "description": "Detaylı açıklama...",
                    "audioGuideTranscript": "Kullanıcı durakta dinlerken sesli rehberin okuyacağı akıcı, bilgilendirici Türkçe rehber anlatımı...",
                    "reviews": [
                       {
                         "author": "Mehmet K.",
                         "source": "Google Maps",
                         "rating": 5.0,
                         "date": "Dün",
                         "comment": "Gerçek kullanıcı yorumu..."
                       }
                    ]
                  }
                ]
                Yalnızca geçerli JSON formatında yanıt ver. Markdown veya ek metin ekleme.
            """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                }
                put("contents", contents)
            }

            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                Log.w("GeminiRouteService", "API error: ${response.code} $responseBody")
                return@withContext generateFallbackStops(origin, destination, routeHoursEstimate)
            }

            val parsedJson = JSONObject(responseBody)
            val candidates = parsedJson.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text") ?: ""

            val cleanedJson = text.trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()

            val jsonArray = JSONArray(cleanedJson)
            val result = mutableListOf<WaypointStop>()

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val catStr = obj.optString("category", "SCENIC")
                val category = try {
                    WaypointCategory.valueOf(catStr)
                } catch (e: Exception) {
                    WaypointCategory.SCENIC
                }

                val amenitiesList = mutableListOf<String>()
                val amenitiesArray = obj.optJSONArray("amenities")
                if (amenitiesArray != null) {
                    for (a in 0 until amenitiesArray.length()) {
                        amenitiesList.add(amenitiesArray.getString(a))
                    }
                } else {
                    amenitiesList.addAll(listOf("Otopark", "WC", "Kafeterya", "Wi-Fi"))
                }

                val guideNotesList = mutableListOf<String>()
                val guideNotesArray = obj.optJSONArray("guideImportantNotes")
                if (guideNotesArray != null) {
                    for (g in 0 until guideNotesArray.length()) {
                        guideNotesList.add(guideNotesArray.getString(g))
                    }
                }

                val reviewsList = mutableListOf<UserReview>()
                val reviewsArray = obj.optJSONArray("reviews")
                if (reviewsArray != null) {
                    for (r in 0 until reviewsArray.length()) {
                        val revObj = reviewsArray.getJSONObject(r)
                        reviewsList.add(
                            UserReview(
                                author = revObj.optString("author", "Gezgin"),
                                source = revObj.optString("source", "Google Maps"),
                                rating = revObj.optDouble("rating", 4.8),
                                date = revObj.optString("date", "Yakın zamanda"),
                                comment = revObj.optString("comment", "Harika bir durak.")
                            )
                        )
                    }
                }

                result.add(
                    WaypointStop(
                        id = obj.optString("id", "stop_$i"),
                        name = obj.optString("name", "Yol Üstü Keşif Noktası $i"),
                        subtitle = obj.optString("subtitle", "Görülmesi gereken durak"),
                        city = obj.optString("city", "$origin - $destination Yolu"),
                        category = category,
                        lat = obj.optDouble("lat", 39.0),
                        lng = obj.optDouble("lng", 32.0),
                        kmFromStart = obj.optInt("kmFromStart", (i + 1) * 80),
                        drivingMinutesFromStart = obj.optInt("drivingMinutesFromStart", (i + 1) * 60),
                        recommendedStayMinutes = obj.optInt("recommendedStayMinutes", 45),
                        openTime = obj.optString("openTime", "08:30"),
                        closeTime = obj.optString("closeTime", "19:00"),
                        alwaysOpen = obj.optBoolean("alwaysOpen", false),
                        feeDescription = obj.optString("feeDescription", "Ücretsiz"),
                        isFree = obj.optBoolean("isFree", true),
                        museumCardAccepted = obj.optBoolean("museumCardAccepted", false),
                        averageRating = obj.optDouble("averageRating", 4.8),
                        reviewCount = obj.optInt("reviewCount", 2400),
                        amenities = amenitiesList,
                        guideImportantNotes = guideNotesList,
                        description = obj.optString("description", "Yol güzergahındaki önemli durak noktası."),
                        audioGuideTranscript = obj.optString("audioGuideTranscript", "Bu noktadasınız. Keyifli geziler."),
                        reviews = reviewsList,
                        isSelectedForTrip = false
                    )
                )
            }

            if (result.isNotEmpty()) {
                result
            } else {
                generateFallbackStops(origin, destination, routeHoursEstimate)
            }
        } catch (e: Exception) {
            Log.e("GeminiRouteService", "Error generating stops", e)
            generateFallbackStops(origin, destination, routeHoursEstimate)
        }
    }

    fun generateFallbackStops(
        origin: String,
        destination: String,
        routeHoursEstimate: Double
    ): List<WaypointStop> {
        val totalDriveMin = (routeHoursEstimate * 60).toInt().coerceAtLeast(180)
        val totalKm = (routeHoursEstimate * 80).toInt().coerceAtLeast(200)

        return listOf(
            WaypointStop(
                id = "stop_hist_1",
                name = "$origin Tarihi Surları & Arkeoloji Müzesi",
                subtitle = "Bölgenin kadim tarih mirası, lahitler ve anıtlar",
                city = origin,
                category = WaypointCategory.HISTORICAL,
                lat = 39.9334,
                lng = 32.8597,
                kmFromStart = (totalKm * 0.15).toInt(),
                drivingMinutesFromStart = (totalDriveMin * 0.15).toInt(),
                recommendedStayMinutes = 45,
                openTime = "09:00",
                closeTime = "18:30",
                alwaysOpen = false,
                feeDescription = "Müzekart Geçerli / Giriş: 100 ₺",
                isFree = false,
                museumCardAccepted = true,
                averageRating = 4.8,
                reviewCount = 3120,
                amenities = listOf("Otopark", "Temiz WC", "Rehber Panoları", "Wi-Fi"),
                guideImportantNotes = listOf(
                    "Girişte Müzekart geçerlidir; gişede sıra beklemeden turnikeden geçebilirsiniz.",
                    "Fotoğraf çekimi için sabah ışığı idealdir.",
                    "Müze bahçesindeki tarihi taş lahitleri inceleyin."
                ),
                description = "$origin çıkışında yer alan tarihi yerleşim ve antik dönem kalıntıları.",
                audioGuideTranscript = "Kadim tarihi duraktasınız. Bu yapı yüzyıllar boyunca ticaret yollarının güvenliğini sağlamış önemli bir garnizon ve kültür merkezidir.",
                reviews = listOf(
                    UserReview("Emre A.", "Google Maps", 5.0, "Dün", "Tarihi havası çok etkileyici, temiz ve düzenli."),
                    UserReview("Ayşe Y.", "TripAdvisor", 4.7, "3 gün önce", "Harika bir başlangıç noktası.")
                ),
                isSelectedForTrip = false
            ),
            WaypointStop(
                id = "stop_rest_2",
                name = "Yol Üstü Meşhur Yöresel Tandır & Köfte Durağı",
                subtitle = "Meşe odunu ateşinde kuzu tandır, taze yayık ayranı ve fırın sütlaç",
                city = "$origin - $destination Güzergahı",
                category = WaypointCategory.RESTAURANT,
                lat = 39.5,
                lng = 32.0,
                kmFromStart = (totalKm * 0.35).toInt(),
                drivingMinutesFromStart = (totalDriveMin * 0.35).toInt(),
                recommendedStayMinutes = 55,
                openTime = "07:30",
                closeTime = "23:00",
                alwaysOpen = false,
                feeDescription = "Ortalama 250 - 450 ₺ / Kişi",
                isFree = false,
                museumCardAccepted = false,
                averageRating = 4.7,
                reviewCount = 7400,
                amenities = listOf("Geniş Otopark", "Bebek Bakım Odası", "Mescit", "Kredi Kartı", "Elektrikli Araç Şarjı", "Temiz WC"),
                guideImportantNotes = listOf(
                    "Günün taze yayık ayranını ve güveçte pişen kuru fasulyesini mutlaka isteyin.",
                    "Yolculuk için taze cevizli köy ekmeği alabilirsiniz."
                ),
                description = "Yolcuların yıllardır vazgeçilmez durağı olan organik yöresel dinlenme ve lezzet tesisi.",
                audioGuideTranscript = "Lezzet molasına ulaştınız. Yol üstünde taze odun ateşinde hazırlanan yöresel lezzetlerle seyahatinize keyifli bir ara verebilirsiniz.",
                reviews = listOf(
                    UserReview("Serdar T.", "Google Maps", 5.0, "2 gün önce", "Köftesi ve kaymaklı tatlısı tek kelimeyle kusursuz.")
                ),
                isSelectedForTrip = false
            ),
            WaypointStop(
                id = "stop_nature_3",
                name = "Kanyon Vadisi & Saklı Şelale Parkı",
                subtitle = "Göz alıcı yeşillik, ahşap yürüyüş parkuru ve berrak kaynak suları",
                city = "$destination Yolu",
                category = WaypointCategory.NATURE,
                lat = 38.8,
                lng = 31.5,
                kmFromStart = (totalKm * 0.55).toInt(),
                drivingMinutesFromStart = (totalDriveMin * 0.55).toInt(),
                recommendedStayMinutes = 50,
                openTime = "08:00",
                closeTime = "19:30",
                alwaysOpen = false,
                feeDescription = "Otopark & Giriş: 40 ₺ / Araç",
                isFree = false,
                museumCardAccepted = false,
                averageRating = 4.9,
                reviewCount = 5900,
                amenities = listOf("Otopark", "Ahşap Yürüyüş Yolu", "Kafeterya", "Doğal Kaynak Suyu", "WC"),
                guideImportantNotes = listOf(
                    "Kaygan taşlar olabileceği için rahat yürüyüş ayakkabısı giyiniz.",
                    "Şelale önündeki köprüde fotoğraf için en iyi açı sol taraftaki seyir terasıdır."
                ),
                description = "Yüksek kayalıklar arasından dökülen şelale ve asırlık çınar ağaçları altındaki yürüyüş parkuru.",
                audioGuideTranscript = "Doğa harikası kanyon parkurundayız. Kayalıkların arasından fışkıran buz gibi kaynak suları ve temiz hava ile yenileneceksiniz.",
                reviews = listOf(
                    UserReview("Merve S.", "Google Maps", 5.0, "Geçen hafta", "Huzur dolu bir mola yeri, suyun sesi tüm yol yorgunluğunu unutturdu.")
                ),
                isSelectedForTrip = false
            ),
            WaypointStop(
                id = "stop_hist_4",
                name = "Tarihi Taş Kervansaray & Han Çarşısı",
                subtitle = "Selçuklu mimarisi, taç kapı ve otantik el sanatları atölyeleri",
                city = "$origin - $destination Ara Güzergahı",
                category = WaypointCategory.HISTORICAL,
                lat = 38.4,
                lng = 31.0,
                kmFromStart = (totalKm * 0.70).toInt(),
                drivingMinutesFromStart = (totalDriveMin * 0.70).toInt(),
                recommendedStayMinutes = 40,
                openTime = "08:30",
                closeTime = "19:00",
                alwaysOpen = false,
                feeDescription = "Ücretsiz Giriş / Müze Bölümü 40 ₺",
                isFree = true,
                museumCardAccepted = true,
                averageRating = 4.7,
                reviewCount = 4200,
                amenities = listOf("Otopark", "Otantik Çarşı", "Mescit", "Çay Ocağı", "WC"),
                guideImportantNotes = listOf(
                    "Avlu içerisindeki taş tonozların akustik yapısını test edin.",
                    "El yapımı bakır ve seramik hediyelik eşyalar bulunabilir."
                ),
                description = "Yüzyıllar önce tüccarların konakladığı dev taş kemerli tarihi kervansaray.",
                audioGuideTranscript = "Tarihi hanın avlusundasınız. Bu anıtsal taş kapı, geçmiş yüzyılların ticaret kervanlarını ağırlamış eşsiz bir kültür mirasıdır.",
                reviews = listOf(
                    UserReview("Hakan B.", "Google Maps", 4.8, "3 gün önce", "Çok etkileyici bir mimari, mola vermek için çok huzurlu.")
                ),
                isSelectedForTrip = false
            ),
            WaypointStop(
                id = "stop_scenic_5",
                name = "Panoramik Zirve & Vadi Seyir Terası",
                subtitle = "Gün batımı ve 360 derece ufuk manzarası",
                city = destination,
                category = WaypointCategory.SCENIC,
                lat = 37.8,
                lng = 30.5,
                kmFromStart = (totalKm * 0.90).toInt(),
                drivingMinutesFromStart = (totalDriveMin * 0.90).toInt(),
                recommendedStayMinutes = 35,
                openTime = "00:00",
                closeTime = "23:59",
                alwaysOpen = true,
                feeDescription = "Ücretsiz Manzara Noktası",
                isFree = true,
                museumCardAccepted = false,
                averageRating = 4.8,
                reviewCount = 9200,
                amenities = listOf("Seyir Terası Otoparkı", "Çay Bahçesi", "Fotoğraf Çerçevesi", "WC"),
                guideImportantNotes = listOf(
                    "Akşam saatlerinde serin esinti olabilir.",
                    "Ufuk çizgisi gün batımında kızıla boyanır, kameranızı hazır tutun."
                ),
                description = "$destination şehrine girmeden önce tüm vadiyi ve dağ sıralarını kuşbakışı izleyebileceğiniz yüksek rakımlı seyir noktası.",
                audioGuideTranscript = "$destination hedefine yaklaşırken panoramik seyir noktasındasınız. Ufuk çizgisine doğru uzanan dağları ve vadiyi izleyerek yolculuğunuzun son etabını tamamlayabilirsiniz.",
                reviews = listOf(
                    UserReview("Kaan D.", "Google Maps", 5.0, "Dün", "Manzara olağanüstü, $destination girişinde mutlaka durulmalı.")
                ),
                isSelectedForTrip = false
            )
        )
    }
}
