package com.example.data.remote

import com.example.data.model.UserReview
import com.example.data.model.WaypointCategory
import com.example.data.model.WaypointStop

data class RoadTripPreset(
    val id: String,
    val title: String,
    val origin: String,
    val destination: String,
    val totalDistanceKm: Int,
    val estimatedDriveMinutes: Int,
    val description: String,
    val stops: List<WaypointStop>
)

object PredefinedRoutesData {

    val presets: List<RoadTripPreset> = listOf(
        RoadTripPreset(
            id = "ist_ant",
            title = "İstanbul → Antalya: Göller, Tarih & Akdeniz Yolu",
            origin = "İstanbul",
            destination = "Antalya",
            totalDistanceKm = 695,
            estimatedDriveMinutes = 495, // ~8 hours 15 mins direct driving
            description = "İznik, Bursa, Bilecik, Kütahya, Afyonkarahisar, Isparta ve Burdur üzerinden Akdeniz'e inen; antik kentler, tarihi hanlar, göller ve lezzet duraklarıyla dolu efsanevi gezi rotası.",
            stops = listOf(
                WaypointStop(
                    id = "ist_ant_1",
                    name = "İznik Tarihi Surları & Ayasofya Camii",
                    subtitle = "Çini başkenti, göl kıyısı ve 4 konsilin toplandığı antik surlar",
                    city = "Bursa / İznik",
                    category = WaypointCategory.HISTORICAL,
                    lat = 40.4286,
                    lng = 29.7214,
                    kmFromStart = 135,
                    drivingMinutesFromStart = 95,
                    recommendedStayMinutes = 45,
                    openTime = "08:30",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Giriş: 120 ₺",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.8,
                    reviewCount = 3840,
                    amenities = listOf("Otopark", "Temiz WC", "Çini Atölyesi", "Rehber Hizmeti", "Wi-Fi"),
                    guideImportantNotes = listOf(
                        "Sabah 10:00 öncesinde gelirseniz kalabalık olmadan sakin fotoğraf çekebilirsiniz.",
                        "Ayasofya Camii içi namaz vakitlerinde ziyarete kısa süreli kapalı olabilir.",
                        "Tarihi Lefke Kapısı yanındaki çini atölyelerini mutlaka gezin."
                    ),
                    description = "Roma, Bizans, Selçuklu ve Osmanlı medeniyetlerinin kesişim noktası. 4 konsilin toplandığı antik surlar ve 7. yüzyıldan kalma Ayasofya yapısı.",
                    audioGuideTranscript = "İznik'e hoş geldiniz. Dört bir yanı Roma ve Bizans surlarıyla çevrili bu kadim şehir, Hristiyanlık tarihindeki 1. ve 7. Ekümenik Konsillere ev sahipliği yapmıştır. Ayasofya yapısı içindeki freskleri ve zemin mozaiklerini yakından incelemenizi öneririz.",
                    reviews = listOf(
                        UserReview("Ahmet S.", "Google Maps", 5.0, "2 gün önce", "Tarihi dokusu büyüleyici. Göl kenarında çay molasıyla birleştirilmeli."),
                        UserReview("Elena K.", "TripAdvisor", 4.5, "1 hafta önce", "The tiles and the old Byzantine walls are authentic and well preserved.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_cumalikizik",
                    name = "Cumalıkızık 700 Yıllık Osmanlı Köyü",
                    subtitle = "Taş sokaklar, ahşap konaklar ve serpme köy kahvaltısı",
                    city = "Bursa / Yıldırım",
                    category = WaypointCategory.HISTORICAL,
                    lat = 40.1747,
                    lng = 29.1714,
                    kmFromStart = 160,
                    drivingMinutesFromStart = 115,
                    recommendedStayMinutes = 60,
                    openTime = "08:00",
                    closeTime = "20:00",
                    alwaysOpen = false,
                    feeDescription = "Köy Girişi Ücretsiz / Otopark 40 ₺",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.7,
                    reviewCount = 28500,
                    amenities = listOf("Otopark", "Köy Pazarı", "Yöresel Kahvaltı", "Fotoğraf Noktası", "WC"),
                    guideImportantNotes = listOf(
                        "Dünyanın en dar sokaklarından biri olan Cin Aralığı'nda fotoğraf çekilmeyi unutmayın.",
                        "Taze böğürtlen ve ahududu reçelleri köy halkından doğrudan alınabilir."
                    ),
                    description = "UNESCO Dünya Mirası listesinde yer alan, 700 yıllık Osmanlı sivil mimarisini ve arnavut kaldırımlı dar sokaklarını günümüze taşıyan canlı yaşayan köy.",
                    audioGuideTranscript = "Cumalıkızık köyündesiniz. Uludağ eteklerinde kurulan ve günümüze dek özgün yapısını koruyan bu köyde, Osmanlı döneminin erken dönem yerleşim dokusunu keşfedeceksiniz.",
                    reviews = listOf(
                        UserReview("Sibel N.", "Google Maps", 4.8, "Geçen hafta", "Köy kahvaltısı harikaydı, sokaklarda yürümek adeta zamanda yolculuk.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_inegol",
                    name = "İnegöl Tarihi Meşhur Köfteciler Çarşısı",
                    subtitle = "Yol üstü tescilli ızgara İnegöl köftesi ve piyaz molası",
                    city = "Bursa / İnegöl",
                    category = WaypointCategory.RESTAURANT,
                    lat = 40.0789,
                    lng = 29.5133,
                    kmFromStart = 205,
                    drivingMinutesFromStart = 145,
                    recommendedStayMinutes = 45,
                    openTime = "10:00",
                    closeTime = "23:00",
                    alwaysOpen = false,
                    feeDescription = "Ortalama 250 - 400 ₺ / Kişi",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.8,
                    reviewCount = 14200,
                    amenities = listOf("Otopark", "Bebek Bakım Odası", "Mescit", "Hızlı Servis", "Temiz WC"),
                    guideImportantNotes = listOf(
                        "Geleneksel az baharatlı ve dana eti kıymalı orijinal İnegöl köftesini fırınlanmış sıcak pidelerle deneyin.",
                        "Yol üstünde yarım saatlik en pratik ve lezzetli öğle yemeği durağıdır."
                    ),
                    description = "1890'lardan bu yana süregelen İnegöl köfte geleneği. Odun ızgarasında pişen suyunu kaybetmemiş lokum kıvamında köfteler.",
                    audioGuideTranscript = "İnegöl lezzet durağındasınız. Balkan göçmenlerinin getirdiği ve 130 yıldır reçetesi korunan tescilli İnegöl köftesiyle yolculuğunuza lezzetli bir enerji katabilirsiniz.",
                    reviews = listOf(
                        UserReview("Murat A.", "Google Maps", 5.0, "3 gün önce", "Yol üstü her geçtiğimde dururum. Köftesi ve piyazı mükemmel.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_bilecik",
                    name = "Bilecik Şeyh Edebali Türbesi & Panoramik Vadi Seyri",
                    subtitle = "Osmanlı'nın kuruluş mekanı & Tarihi saat kulesi",
                    city = "Bilecik",
                    category = WaypointCategory.HISTORICAL,
                    lat = 40.1417,
                    lng = 29.9833,
                    kmFromStart = 245,
                    drivingMinutesFromStart = 175,
                    recommendedStayMinutes = 40,
                    openTime = "08:00",
                    closeTime = "19:30",
                    alwaysOpen = false,
                    feeDescription = "Ücretsiz Ziyaret",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.7,
                    reviewCount = 6700,
                    amenities = listOf("Otopark", "Tarihi Park", "Çay Bahçesi", "Hediyelik Dükkan", "WC"),
                    guideImportantNotes = listOf(
                        "Şeyh Edebali'nin Osman Gazi'ye tarihi nasihatinin yazılı olduğu anıt kitabeleri okuyun.",
                        "Vadi üzerindeki seyir terasından tarihi İpek Yolu köprüsünü fotoğraflayabilirsiniz."
                    ),
                    description = "Osmanlı Devleti'nin manevi kurucusu Şeyh Edebali'nin türbesi ve vadinin üzerinde yer alan tarihi açık hava müzesi kompleksi.",
                    audioGuideTranscript = "Bilecik Şeyh Edebali yerleşkesindesiniz. 'İnsanı yaşat ki devlet yaşasın' sözünün doğduğu bu tarihi tepe, Osmanlı'nın beylikten cihan devletine uzanan yolculuğunun başlangıcıdır.",
                    reviews = listOf(
                        UserReview("Fatih K.", "Google Maps", 4.8, "Geçen ay", "Manevi atmosferi ve vadi manzarası çok huzurlu.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_kutahya",
                    name = "Kütahya Tarihi Çini Çarşısı & Kalesi",
                    subtitle = "Dünyaca ünlü el sanatı çiniler ve tepeden şehir panoraması",
                    city = "Kütahya",
                    category = WaypointCategory.SCENIC,
                    lat = 39.4167,
                    lng = 29.9833,
                    kmFromStart = 310,
                    drivingMinutesFromStart = 215,
                    recommendedStayMinutes = 50,
                    openTime = "08:30",
                    closeTime = "20:00",
                    alwaysOpen = false,
                    feeDescription = "Çarşı Girişi Ücretsiz",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.6,
                    reviewCount = 8900,
                    amenities = listOf("Otopark", "El Sanatları Çarşısı", "Döner Restoran", "WC"),
                    guideImportantNotes = listOf(
                        "Çini atölyelerinde ustalardan çarkta çamur şekillendirme sürecini izleyebilirsiniz.",
                        "Otantik el boyaması hediyelik fincan ve tabakları fabrika fiyatına temin edebilirsiniz."
                    ),
                    description = "Selçuklu ve Osmanlı'dan günümüze çini sanatının kalbi. Tarihi kalenin eteğinde yüzlerce el sanatı ustasının sergileri.",
                    audioGuideTranscript = "Çini kenti Kütahya'dasınız. 600 yılı aşkın süredir fırınlanan seramik ve kobalt mavisi çinilerin eşsiz desenlerini inceleyebilirsiniz.",
                    reviews = listOf(
                        UserReview("Merve Y.", "Google Maps", 4.7, "2 hafta önce", "Çiniler büyüleyici, fiyatlar çok makul.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_aizanoi",
                    name = "Aizanoi Antik Kenti & Zeus Tapınağı",
                    subtitle = "Dünyanın ilk borsa binası ve Anadolu'nun en iyi korunmuş Zeus tapınağı",
                    city = "Kütahya / Çavdarhisar",
                    category = WaypointCategory.HISTORICAL,
                    lat = 39.2014,
                    lng = 29.6108,
                    kmFromStart = 355,
                    drivingMinutesFromStart = 245,
                    recommendedStayMinutes = 60,
                    openTime = "08:30",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Giriş: 150 ₺",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.9,
                    reviewCount = 4300,
                    amenities = listOf("Otopark", "Rehber Panoları", "Ziyaretçi Merkezi", "WC"),
                    guideImportantNotes = listOf(
                        "Zeus Tapınağı'nın altındaki tonozlu yeraltı kehanet odasına inmeyi unutmayın.",
                        "Dünyanın ilk gıda ve ticaret borsası yapısındaki Latince fiyat listesi yazıtlarını inceleyin."
                    ),
                    description = "İkinci Efes olarak anılan Aizanoi; 20.000 kişilik tiyatro-stadyum kompleksi ve dimdik ayakta duran anıtsal sütunlarıyla büyüleyicidir.",
                    audioGuideTranscript = "Aizanoi Antik Kenti'ndesiniz. Roma döneminde Penkalas Çayı kıyısında kurulan bu görkemli şehirde, dünyanın ilk borsa yapısını ve görkemli Zeus Tapınağı'nı keşfetmektesiniz.",
                    reviews = listOf(
                        UserReview("Bülent T.", "Google Maps", 5.0, "Geçen hafta", "Tapınağın ayakta kalmış olması olağanüstü. Mutlaka uğranmalı.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_frig",
                    name = "Frig Vadisi, Ayazini Kaya Evleri & Emre Gölü",
                    subtitle = "3000 yıllık kaya yerleşimleri, peri bacaları ve göl üstü kayıklar",
                    city = "Afyonkarahisar / İhsaniye",
                    category = WaypointCategory.NATURE,
                    lat = 39.0119,
                    lng = 30.5528,
                    kmFromStart = 375,
                    drivingMinutesFromStart = 260,
                    recommendedStayMinutes = 60,
                    openTime = "08:00",
                    closeTime = "20:00",
                    alwaysOpen = false,
                    feeDescription = "Vadi Girişi Ücretsiz",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.9,
                    reviewCount = 7800,
                    amenities = listOf("Otopark", "Göl Kıyısı Kafe", "Sandal & Kano", "Tarihi Fırınlar", "WC"),
                    guideImportantNotes = listOf(
                        "Ayazini köyündeki kayaya oyma kilise ve tarihi kaya evlerinin içine girebilirsiniz.",
                        "Emre Gölü kenarında gün batımında Frig kayık turu çok keyiflidir."
                    ),
                    description = "Kapadokya'yı andıran volkanik tüf kayalıkları, Kral Midas'ın mirası Frig kaya anıtları ve Emre Gölü etrafındaki yürüyüş parkurları.",
                    audioGuideTranscript = "Frigya'nın kalbi Ayazini ve Emre Gölü'ndesiniz. Demir Çağı'nda Friglerin kayaları oyarak inşa ettiği tapınakları ve yerleşimleri izleyebilirsiniz.",
                    reviews = listOf(
                        UserReview("Derya S.", "Google Maps", 5.0, "5 gün önce", "Kapadokya kadar etkileyici ama çok daha sakin ve doğal.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_2",
                    name = "Meşhur İkbal & Cumhuriyet Afyon Gastronomi Durağı",
                    subtitle = "Yol üstü tescilli sucuk, manda kaymağı ve fırından ekmek kadayıfı",
                    city = "Afyonkarahisar",
                    category = WaypointCategory.RESTAURANT,
                    lat = 38.7569,
                    lng = 30.5387,
                    kmFromStart = 395,
                    drivingMinutesFromStart = 275,
                    recommendedStayMinutes = 55,
                    openTime = "07:00",
                    closeTime = "23:30",
                    alwaysOpen = false,
                    feeDescription = "Ortalama 300 - 550 ₺ / Kişi",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.7,
                    reviewCount = 8900,
                    amenities = listOf("Geniş Otopark", "Bebek Bakım Odası", "Mescit", "Kredi Kartı", "Elektrikli Şarj", "Temiz WC"),
                    guideImportantNotes = listOf(
                        "Geleneksel manda kaymaklı sıcak ekmek kadayıfını taze fırından isteyiniz.",
                        "Yolculuk için vakumlu paketli sucuk ve lokum paketleme servisi mevcuttur."
                    ),
                    description = "UNESCO Gastronomi Kenti Afyon'un 100 yılı aşkın lezzet geleneği. Odun ateşinde tandır, sucuk ızgara ve meşhur kaymaklı tatlılar.",
                    audioGuideTranscript = "Afyonkarahisar gastronomisinin kalbindesiniz. Manda kaymağı ve dana sucuğuyla enerji depolayabilir, devam eden seyahatiniz öncesinde harika bir mola verebilirsiniz.",
                    reviews = listOf(
                        UserReview("Mehmet Can", "Google Maps", 5.0, "Dün", "Kaymaklı ekmek kadayıfı tek kelimeyle efsane. Tesisler tertemiz."),
                        UserReview("Seda T.", "Foursquare", 4.6, "4 gün önce", "Hızlı servis ve lezzetli tandır.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_dinar",
                    name = "Dinar Suçıkan Şelalesi & Parkı",
                    subtitle = "Büyük Menderes Nehri'nin doğduğu serin kanyon ve su değirmeni",
                    city = "Afyon / Dinar",
                    category = WaypointCategory.NATURE,
                    lat = 38.0667,
                    lng = 30.1667,
                    kmFromStart = 465,
                    drivingMinutesFromStart = 325,
                    recommendedStayMinutes = 35,
                    openTime = "07:00",
                    closeTime = "22:00",
                    alwaysOpen = false,
                    feeDescription = "Ücretsiz Park Girişi",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.6,
                    reviewCount = 5100,
                    amenities = listOf("Otopark", "Su Kenarı Çay Bahçesi", "Çocuk Parkı", "WC"),
                    guideImportantNotes = listOf(
                        "Dağın bağrından çıkan buz gibi kaynak suyunun oluşturduğu gölette su sesinde dinlenin.",
                        "Tarihteki ilk müzik yarışmasının (Apollon ile Marsyas) yapıldığı efsanevi mekandır."
                    ),
                    description = "Kayaların altından gürül gürül kaynayan berrak suların etrafında kurulu asırlık çınar ağaçlarıyla gölgelenen serinleme durağı.",
                    audioGuideTranscript = "Dinar Suçıkan parkındasınız. Ege'nin en büyük nehri Büyük Menderes'in doğduğu bu kaynakta, binlerce yıllık mitolojik efsanelerin izlerini taşıyan su sesinde mola verebilirsiniz.",
                    reviews = listOf(
                        UserReview("Kemal Y.", "Google Maps", 4.7, "Geçen ay", "Yol üstü durup demlik çay içmek için harika ve çok serin bir park.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_egirdir",
                    name = "Eğirdir Gölü & Yeşilada Panoramik Seyir Noktası",
                    subtitle = "Turkuaz dağ gölü, göl levreği ve elma bahçeleri",
                    city = "Isparta / Eğirdir",
                    category = WaypointCategory.SCENIC,
                    lat = 37.8767,
                    lng = 30.8528,
                    kmFromStart = 510,
                    drivingMinutesFromStart = 360,
                    recommendedStayMinutes = 45,
                    openTime = "00:00",
                    closeTime = "23:59",
                    alwaysOpen = true,
                    feeDescription = "Ücretsiz Manzara Seyri",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.8,
                    reviewCount = 9200,
                    amenities = listOf("Otopark", "Göl Kıyısı Kafeler", "Balık Restoranları", "Yürüyüş İskelesi", "WC"),
                    guideImportantNotes = listOf(
                        "Yeşilada'ya doğru uzanan yolda gün batımı saatlerinde büyüleyici renk yansımaları oluşur.",
                        "Taze göl levreği veya kızartma sazan balığı tadabilirsiniz."
                    ),
                    description = "Sivri Dağ gölgesinde berrak sularıyla Göller Bölgesi'nin gözdesi. Göl içine uzanan yarımadası ve tarihi Aya Stefanos Kilisesi.",
                    audioGuideTranscript = "Eğirdir Gölü'ndesiniz. Türkiye'nin dördüncü büyük gölü olan Eğirdir, günde birkaç kez değişen renk tonları sebebiyle 'Yedi Renkli Göl' olarak anılır.",
                    reviews = listOf(
                        UserReview("Cemil H.", "Google Maps", 4.9, "1 hafta önce", "Yol üstünde yarım saat durup çay içmek için harika bir manzara.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_3",
                    name = "Salda Gölü & Beyaz Adalar Seyir Terası",
                    subtitle = "Türkiye'nin Maldivleri & Biyoçeşitlilik Doğa Harikası",
                    city = "Burdur / Yeşilova",
                    category = WaypointCategory.NATURE,
                    lat = 37.5532,
                    lng = 29.6738,
                    kmFromStart = 535,
                    drivingMinutesFromStart = 380,
                    recommendedStayMinutes = 60,
                    openTime = "07:30",
                    closeTime = "20:00",
                    alwaysOpen = false,
                    feeDescription = "Giriş & Otopark: 60 ₺ / Araç",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.9,
                    reviewCount = 14200,
                    amenities = listOf("Otopark", "Seyir Terası", "Doğal Yürüyüş Yolu", "Fotoğraf Noktası", "Kafeterya", "WC"),
                    guideImportantNotes = listOf(
                        "Beyaz hidromanyezit kumullara basmak yasaktır; ahşap seyir iskelesini kullanınız.",
                        "Öğleden sonra 16:30 - 18:00 arası güneş açısı turkuaz rengi en canlı gösteren andır."
                    ),
                    description = "184 metre derinliğiyle Türkiye'nin en berrak göllerinden biri. Mars'ın jeolojik yapısına benzerliğiyle NASA araştırmalarına konu olan beyaz kumsallar.",
                    audioGuideTranscript = "Salda Gölü'ne yaklaşıyorsunuz. Magnezyum zengini beyaz kumulları ve turkuaz rengiyle doğa mucizesi olan bu göl, dünyada Mars'taki Jezero Krateri ile aynı mineral yapısına sahip nadir yerlerdendir.",
                    reviews = listOf(
                        UserReview("Gözde A.", "Google Maps", 5.0, "3 gün önce", "Renk tonları gerçek olamayacak kadar güzel."),
                        UserReview("David M.", "TripAdvisor", 5.0, "2 hafta önce", "Unbelievable shades of turquoise. Pure natural wonder!")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_4",
                    name = "Sagalassos Antik Kenti & Antoninler Çeşmesi",
                    subtitle = "1750 m yükseklikte 2000 yıldır şırıldayan su ve bulutların üstündeki tiyatro",
                    city = "Burdur / Ağlasun",
                    category = WaypointCategory.SCENIC,
                    lat = 37.6775,
                    lng = 30.5208,
                    kmFromStart = 580,
                    drivingMinutesFromStart = 415,
                    recommendedStayMinutes = 80,
                    openTime = "08:30",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Giriş: 180 ₺",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.9,
                    reviewCount = 5200,
                    amenities = listOf("Otopark", "Ziyaretçi Merkezi", "Kafeterya", "WC", "Panoramik Manzara"),
                    guideImportantNotes = listOf(
                        "Antoninler Çeşmesi'nden 2000 yıldır kesintisiz akan dağ kaynağından su içebilirsiniz.",
                        "Tiyatro basamaklarından Toros Dağları manzarası gün batımında nefes kesicidir."
                    ),
                    description = "Pisidia bölgesinin en görkemli kenti. Roma döneminden günümüze çalışan anıtsal çeşmesi, agora ve dağ yamacına asılı antik tiyatrosu.",
                    audioGuideTranscript = "Sagalassos'tasınız. Marcus Aurelius döneminde inşa edilen ve hala berrak dağ suyu akan Antoninler Anıtsal Çeşmesi önünde duruyorsunuz.",
                    reviews = listOf(
                        UserReview("Deniz K.", "Google Maps", 5.0, "Geçen hafta", "Türkiye'nin en etkileyici antik kenti. Çeşmeden su içmek harika bir his.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_insuyu",
                    name = "İnsuyu Mağarası & Yeraltı Gölleri Parkı",
                    subtitle = "Türkiye'nin turizme açılan ilk sarkıt, dikit ve damlataş mağarası",
                    city = "Burdur",
                    category = WaypointCategory.NATURE,
                    lat = 37.6625,
                    lng = 30.3472,
                    kmFromStart = 605,
                    drivingMinutesFromStart = 435,
                    recommendedStayMinutes = 40,
                    openTime = "08:30",
                    closeTime = "18:30",
                    alwaysOpen = false,
                    feeDescription = "Giriş: 50 ₺ / Müzekart Geçersiz",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.6,
                    reviewCount = 6100,
                    amenities = listOf("Otopark", "Mağara Aydınlatması", "Kafeterya", "WC"),
                    guideImportantNotes = listOf(
                        "Mağara içi yıl boyu 14 derece ve serindir, hafif hırka önerilir.",
                        "Milyonlarca yılda oluşan devasa sarkıt sütunları yürüyüş yolu boyunca yakından görebilirsiniz."
                    ),
                    description = "597 metre uzunluğundaki turistik galerisi, tertemiz havası ve kireçtaşı karstik oluşumlarıyla ünlü doğa anıtı.",
                    audioGuideTranscript = "İnsuyu Mağarası'ndayız. 1965 yılında Türkiye'de turizme açılan ilk mağara olan bu karstik galeride, milyonlarca yıllık sarkıt ve dikitlerin arasından yürüyeceksiniz.",
                    reviews = listOf(
                        UserReview("Sinan Ö.", "Google Maps", 4.7, "3 hafta önce", "Çok ferahlatıcı ve mistik bir yer.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_karain",
                    name = "Karain Mağarası & Tarih Öncesi Sit Alanı",
                    subtitle = "500.000 yıllık insanlık tarihi & Toroslar'a açılan pencere",
                    city = "Antalya / Döşemealtı",
                    category = WaypointCategory.HISTORICAL,
                    lat = 36.9856,
                    lng = 30.5706,
                    kmFromStart = 655,
                    drivingMinutesFromStart = 465,
                    recommendedStayMinutes = 45,
                    openTime = "08:30",
                    closeTime = "18:30",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Giriş: 90 ₺",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.7,
                    reviewCount = 3900,
                    amenities = listOf("Otopark", "Müze Salonu", "Tarihi Basamaklar", "WC"),
                    guideImportantNotes = listOf(
                        "Mağaraya ulaşmak için 400 basamaklık bir taş patika çıkışı bulunur.",
                        "Anadolu'daki en eski Neandertal insan kalıntılarının bulunduğu arkeolojik merkezdir."
                    ),
                    description = "Paleolitik çağdan Roma dönemine kadar kesintisiz 500 bin yıl iskan görmüş, Türkiye'nin en büyük mağara yerleşkesi.",
                    audioGuideTranscript = "Antalya sınırındaki Karain Mağarası'ndasınız. Denizden 450 metre yükseklikteki bu mağara, Anadolu'da insanlık tarihinin bilinen en eski izlerini barındırır.",
                    reviews = listOf(
                        UserReview("Levent G.", "Google Maps", 4.8, "Geçen ay", "Tarih meraklıları için hazine niteliğinde.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_5",
                    name = "Düden Şelalesi & Kanyon Seyir Parkı",
                    subtitle = "Antalya girişinde serin kanyon, mağara oyukları ve asırlık çınarlar",
                    city = "Antalya / Kepez",
                    category = WaypointCategory.NATURE,
                    lat = 36.9649,
                    lng = 30.7259,
                    kmFromStart = 685,
                    drivingMinutesFromStart = 490,
                    recommendedStayMinutes = 45,
                    openTime = "08:00",
                    closeTime = "19:30",
                    alwaysOpen = false,
                    feeDescription = "Giriş: 40 ₺ / Müzekart Geçersiz",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.7,
                    reviewCount = 18600,
                    amenities = listOf("Otopark", "Kafe & Dondurma", "Mağara Geçişi", "Hediyelik Eşya", "WC"),
                    guideImportantNotes = listOf(
                        "Şelalenin arkasındaki doğal mağaranın içine girip suyun arkasından dışarıyı izleyin.",
                        "Zemin hafif nemli ve kaygan olabilir, dikkatli yürüyünüz."
                    ),
                    description = "Şehrin içinde yemyeşil bir vaha. Şelalenin döküldüğü kanyon ve şelalenin arkasından geçen doğal oyuk mağara galerisi.",
                    audioGuideTranscript = "Antalya'ya varış noktanızdaki Düden Şelalesi'ndesiniz. Mağara içindeki oyuklardan şelalenin dökülüşünü izleyebilir ve Toroslar'dan gelen serin suların mikroklimasında ferahlayabilirsiniz.",
                    reviews = listOf(
                        UserReview("Hülya G.", "Google Maps", 4.7, "5 gün önce", "Mağaranın içinden şelaleyi görmek çok keyifliydi."),
                        UserReview("Thomas B.", "TripAdvisor", 4.5, "3 hafta önce", "Great cool stop before entering the city center.")
                    ),
                    isSelectedForTrip = false
                )
            )
        ),
        RoadTripPreset(
            id = "ist_ant_ege",
            title = "İstanbul → Antalya: Pamukkale & Ege Yolu",
            origin = "İstanbul",
            destination = "Antalya (Pamukkale & Ege)",
            totalDistanceKm = 720,
            estimatedDriveMinutes = 510,
            description = "Bursa, Susurluk, Kula Volkanik Jeoparkı, Pamukkale Travertenleri, Kaklık Mağarası ve Korkuteli üzerinden Antalya'ya inen alternatif rota.",
            stops = listOf(
                WaypointStop(
                    id = "ist_ant_ege_susurluk",
                    name = "Susurluk Meşhur Tost & Köpüklü Ayran Durağı",
                    subtitle = "Yol üstü tescilli Mihaliç peynirli tost ve yayık ayranı",
                    city = "Balıkesir / Susurluk",
                    category = WaypointCategory.RESTAURANT,
                    lat = 39.9139,
                    lng = 28.1639,
                    kmFromStart = 230,
                    drivingMinutesFromStart = 150,
                    recommendedStayMinutes = 35,
                    openTime = "00:00",
                    closeTime = "23:59",
                    alwaysOpen = true,
                    feeDescription = "Ortalama 120 - 220 ₺ / Kişi",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.8,
                    reviewCount = 24000,
                    amenities = listOf("Otopark", "7/24 Açık", "Hızlı Servis", "Temiz WC"),
                    guideImportantNotes = listOf(
                        "Köpüğü bardağın dışına taşan soğuk yayık ayranı ve çıtır susurluk tostunu ikili menü olarak isteyin."
                    ),
                    description = "Yol üstü kültürünün efsanevi durağı. Hakiki köy tereyağı ve yağlı gözenekli Mihaliç peyniriyle preslenen meşhur tost.",
                    audioGuideTranscript = "Susurluk lezzet durağındasınız. Türkiye karayollarının en klasik lezzeti olan köpüklü yayık ayranı ve kızarmış tost ile mola verebilirsiniz.",
                    reviews = listOf(
                        UserReview("Serdar T.", "Google Maps", 5.0, "Dün", "Gece gündüz değişmeyen nefis lezzet.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_ege_kula",
                    name = "Kula Volkanik Jeoparkı & Peri Bacaları",
                    subtitle = "UNESCO tescilli lav akıntıları ve volkan konileri",
                    city = "Manisa / Kula",
                    category = WaypointCategory.NATURE,
                    lat = 38.5472,
                    lng = 28.6486,
                    kmFromStart = 380,
                    drivingMinutesFromStart = 265,
                    recommendedStayMinutes = 45,
                    openTime = "00:00",
                    closeTime = "23:59",
                    alwaysOpen = true,
                    feeDescription = "Ücretsiz Açık Hava Ziyareti",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.7,
                    reviewCount = 4900,
                    amenities = listOf("Otopark", "Ahşap Yürüyüş Yolu", "Seyir Platformu", "WC"),
                    guideImportantNotes = listOf(
                        "Antik çağda 'Katakekaumene' (Yanık Ülke) olarak anılan volkanik kraterleri ahşap yürüyüş parkurundan izleyin."
                    ),
                    description = "Türkiye'nin ilk ve tek UNESCO Avrupa Jeoparkı. Simetrik volkan konileri, bazalt sütunları ve peri bacaları.",
                    audioGuideTranscript = "Kula Jeoparkı'ndasınız. Milyonlarca yıllık lav akıntılarının oluşturduğu bu siyah volkanik arazide jeolojik zaman tünelindesiniz.",
                    reviews = listOf(
                        UserReview("Aliye M.", "Google Maps", 4.9, "Geçen ay", "Yol üstünde böyle bir doğa harikası olduğunu bilmiyordum, büyüleyici.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_ege_pamukkale",
                    name = "Pamukkale Travertenleri & Hierapolis Antik Havuzu",
                    subtitle = "Beyaz cennet travertenler ve antik sütunlar içinde termal yüzme",
                    city = "Denizli / Pamukkale",
                    category = WaypointCategory.SCENIC,
                    lat = 37.9258,
                    lng = 29.1219,
                    kmFromStart = 490,
                    drivingMinutesFromStart = 345,
                    recommendedStayMinutes = 90,
                    openTime = "08:00",
                    closeTime = "20:00",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Giriş: 700 ₺ (Yabancı) - Müzekart Ücretsiz",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.9,
                    reviewCount = 45000,
                    amenities = listOf("Otopark", "Termal Havuz", "Antik Tiyatro", "Kafe", "WC"),
                    guideImportantNotes = listOf(
                        "Travertenlerde çıplak ayakla yürümek serbesttir, terlik çantası yanınıza alınız.",
                        "Kleopatra Antik Havuzu'nda suyun altındaki 2000 yıllık mermer sütunların üzerinde yüzebilirsiniz."
                    ),
                    description = "Kalsiyum karbonat zengini termal suların oluşturduğu basamaklı beyaz teraslar ve devasa Hierapolis Antik Tiyatrosu.",
                    audioGuideTranscript = "Dünya Mirası Pamukkale'desiniz. Binlerce yıldır şifa arayan kralların ve imparatorların uğrak yeri olan beyaz travertenleri keşfedin.",
                    reviews = listOf(
                        UserReview("Kemal C.", "Google Maps", 5.0, "1 hafta önce", "Antik havuzda yüzmek unutulmaz bir deneyim.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_ege_kaklik",
                    name = "Kaklık Mağarası (Yeraltı Pamukkalesi)",
                    subtitle = "Mağara içinde kükürtlü termal basamaklar ve şelaleler",
                    city = "Denizli / Honaz",
                    category = WaypointCategory.NATURE,
                    lat = 37.8681,
                    lng = 29.3853,
                    kmFromStart = 525,
                    drivingMinutesFromStart = 370,
                    recommendedStayMinutes = 40,
                    openTime = "08:30",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Giriş: 40 ₺ / Müzekart Geçersiz",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.8,
                    reviewCount = 7600,
                    amenities = listOf("Otopark", "Kafeterya", "Ahşap İskele", "WC"),
                    guideImportantNotes = listOf(
                        "Mağara tavanından berrak termal su damlaları süzüldüğü için ahşap köprülerde dikkatli yürüyünüz."
                    ),
                    description = "Tavanı çöken bir mağaranın içinde oluşan basamaklı minyatür travertenler ve kükürtlü şifalı yeraltı suları.",
                    audioGuideTranscript = "Kaklık Mağarası'ndayız. 'Yeraltındaki Pamukkale' olarak adlandırılan bu doğal havuzda, suyun içindeki kükürtün yarattığı zümrüt yeşili tonları görebilirsiniz.",
                    reviews = listOf(
                        UserReview("Eren D.", "Google Maps", 4.9, "3 gün önce", "Çok gizemli ve ferahlatıcı bir atmosfer.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_ege_korkuteli",
                    name = "Korkuteli Meşhur Yanık Dondurma & Şiş Köfte Durağı",
                    subtitle = "Toros keçi sütünden odun ateşi dondurması ve kuzu şiş köfte",
                    city = "Antalya / Korkuteli",
                    category = WaypointCategory.RESTAURANT,
                    lat = 37.0653,
                    lng = 30.1989,
                    kmFromStart = 645,
                    drivingMinutesFromStart = 455,
                    recommendedStayMinutes = 45,
                    openTime = "09:00",
                    closeTime = "23:00",
                    alwaysOpen = false,
                    feeDescription = "Ortalama 200 - 380 ₺ / Kişi",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.8,
                    reviewCount = 8200,
                    amenities = listOf("Otopark", "Açık Bahçe", "Paket Dondurma", "Mescit", "WC"),
                    guideImportantNotes = listOf(
                        "Kazan dibi lezzetindeki hafif karamelimsi yanık keçi sütü dondurmasını külahla veya porsiyon olarak tadın."
                    ),
                    description = "Antalya yaylalarının asırlık lezzeti. Kazanın dibi hafifçe yakılarak elde edilen karamelize dondurma ve közde kuzu şiş.",
                    audioGuideTranscript = "Korkuteli yaylasındasınız. Toroslar'ın kekikleriyle beslenen keçi sütünden yapılan meşhur yanık dondurma ile tatlı bir ferahlık yaşayabilirsiniz.",
                    reviews = listOf(
                        UserReview("Nurdan V.", "Google Maps", 5.0, "Geçen hafta", "Yanık dondurmanın aroması bağımlılık yapıyor.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_ege_termessos",
                    name = "Güllük Dağı & Termessos Antik Kartal Yuvası",
                    subtitle = "Büyük İskender'in fethedemediği 1050 m zirvedeki antik kent",
                    city = "Antalya / Döşemealtı",
                    category = WaypointCategory.SCENIC,
                    lat = 36.9822,
                    lng = 30.4636,
                    kmFromStart = 680,
                    drivingMinutesFromStart = 480,
                    recommendedStayMinutes = 60,
                    openTime = "08:30",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Milli Park Girişi: 80 ₺",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.9,
                    reviewCount = 6800,
                    amenities = listOf("Otopark", "Milli Park Seyir Alanı", "Ziyaretçi Panosu", "WC"),
                    guideImportantNotes = listOf(
                        "Uçurumun kenarındaki antik tiyatrodan Antalya ovasına ve Toroslar'a bakan manzara dünyanın en etkileyici manzaralarındandır."
                    ),
                    description = "Çam ormanları içinde bozulmadan korunmuş sarp dağ kenti. Lahitler, sarnıçlar ve uçurum kenarındaki tiyatro.",
                    audioGuideTranscript = "Termessos'tasınız. Sarp kayalıkları ve cesur Solym halkı sayesinde Büyük İskender'in kuşatıp alamadığı bu efsanevi dağ kartal yuvasını seyredin.",
                    reviews = listOf(
                        UserReview("Okan S.", "Google Maps", 5.0, "Dün", "Tiyatrodan uçurumu izlemek insanın nefesini kesiyor.")
                    ),
                    isSelectedForTrip = false
                )
            )
        ),
        RoadTripPreset(
            id = "ist_ant_eskisehir",
            title = "İstanbul → Antalya: Eskişehir & Frig Yolu",
            origin = "İstanbul",
            destination = "Antalya (Eskişehir Yolu)",
            totalDistanceKm = 705,
            estimatedDriveMinutes = 500,
            description = "Bilecik, Tarihi Odunpazarı Evleri, Eskişehir Çibörekçileri, Seyitgazi Midas Anıtı ve Eğirdir üzerinden Antalya rotası.",
            stops = listOf(
                WaypointStop(
                    id = "ist_ant_esk_odunpazari",
                    name = "Tarihi Odunpazarı Evleri & Lületaşı Müzesi",
                    subtitle = "Rengarenk Osmanlı konakları, cam sanat atölyeleri ve müzeler",
                    city = "Eskişehir / Odunpazarı",
                    category = WaypointCategory.HISTORICAL,
                    lat = 39.7619,
                    lng = 30.5286,
                    kmFromStart = 300,
                    drivingMinutesFromStart = 210,
                    recommendedStayMinutes = 60,
                    openTime = "09:00",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Sokaklar Ücretsiz / Müze: 40 ₺",
                    isFree = true,
                    museumCardAccepted = true,
                    averageRating = 4.8,
                    reviewCount = 22000,
                    amenities = listOf("Otopark", "Cam Atölyesi", "Kafeler", "Hediyelik Dükkanlar", "WC"),
                    guideImportantNotes = listOf(
                        "Kurşunlu Külliyesi içindeki sıcak cam üfleme atölyesinde ustaların cam şekillendirmesini izleyin."
                    ),
                    description = "Eskişehir'in ilk yerleşim yeri. Ahşap cumbalı geleneksel evleri, müzeleri ve sanat sokaklarıyla kültür merkezi.",
                    audioGuideTranscript = "Eskişehir Odunpazarı'ndasınız. Arnavut kaldırımlı sokaklarda gezerken lületaşı oymacılığı ve sıcak cam sanatının inceliklerini keşfedebilirsiniz.",
                    reviews = listOf(
                        UserReview("Zehra K.", "Google Maps", 5.0, "2 gün önce", "Sokaklar cıvıl cıvıl, atmosferi çok keyifli.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_esk_ciborek",
                    name = "Meşhur Papağan Çibörek & Balaban Köfte",
                    subtitle = "Kırım Tatar mutfağının altın sarısı çıtır çiböreği",
                    city = "Eskişehir / Tepebaşı",
                    category = WaypointCategory.RESTAURANT,
                    lat = 39.7767,
                    lng = 30.5189,
                    kmFromStart = 305,
                    drivingMinutesFromStart = 215,
                    recommendedStayMinutes = 40,
                    openTime = "10:30",
                    closeTime = "21:30",
                    alwaysOpen = false,
                    feeDescription = "Porsiyon: 140 - 260 ₺",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.7,
                    reviewCount = 11400,
                    amenities = listOf("Otopark", "Hızlı Servis", "Temiz Salon", "WC"),
                    guideImportantNotes = listOf(
                        "Çıtır çiböreğin içindeki kıymalı lezzetli suyun dökülmemesi için köşesinden hafifçe ısırarak yiyiniz."
                    ),
                    description = "Eskişehir'in gastronomi sembolü. İncecik açılan hamurun kızgın yağda kabarmasıyla yapılan tescilli çibörek.",
                    audioGuideTranscript = "Eskişehir lezzet durağındasınız. Kırım Tatar göçmenlerinin şehre kazandırdığı sıcak çibörek ve yoğurtlu balaban köfte ile yolunuza devam edebilirsiniz.",
                    reviews = listOf(
                        UserReview("Cem B.", "Google Maps", 4.8, "Geçen hafta", "Çıtır çıtır, içi sulu ve çok hafif.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_esk_midas",
                    name = "Yazılıkaya Kral Midas Anıtı & Frig Kaya Tapınağı",
                    subtitle = "17 metre yüksekliğinde devasa Frig kaya fasadı ve yazıtları",
                    city = "Eskişehir / Han",
                    category = WaypointCategory.HISTORICAL,
                    lat = 39.2008,
                    lng = 30.7125,
                    kmFromStart = 370,
                    drivingMinutesFromStart = 260,
                    recommendedStayMinutes = 50,
                    openTime = "00:00",
                    closeTime = "23:59",
                    alwaysOpen = true,
                    feeDescription = "Ücretsiz Ören Yeri",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.9,
                    reviewCount = 3800,
                    amenities = listOf("Otopark", "Rehber Panoları", "Köy Çay Ocağı", "WC"),
                    guideImportantNotes = listOf(
                        "Anıt üzerindeki Frig alfabesiyle yazılmış Midas adını taşıyan kabartmaları sabah güneşi açısında inceleyin."
                    ),
                    description = "M.Ö. 6. yüzyılda Ana Tanrıça Matar Kybele adına tüf kayaya oyulmuş dünyadaki en büyük ve görkemli Frig kaya anıtı.",
                    audioGuideTranscript = "Midas Kenti Yazılıkaya'dasınız. 2600 yıllık bu devasa kaya tapınağı, Frig uygarlığının kutsal başkentidir.",
                    reviews = listOf(
                        UserReview("Kadir U.", "Google Maps", 5.0, "3 hafta önce", "Kayanın büyüklüğü ve detaylar hayret verici.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ist_ant_esk_kursunlu",
                    name = "Kurşunlu Şelalesi Doğa Parkı & Kanyon Göleti",
                    subtitle = "Sarmaşıklar, nilüfer havuzları ve 18 metre şelale",
                    city = "Antalya / Aksu",
                    category = WaypointCategory.NATURE,
                    lat = 36.9614,
                    lng = 30.8208,
                    kmFromStart = 690,
                    drivingMinutesFromStart = 490,
                    recommendedStayMinutes = 45,
                    openTime = "08:00",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Giriş: 45 ₺ / Müzekart Geçersiz",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.8,
                    reviewCount = 16200,
                    amenities = listOf("Otopark", "Doğa Parkuru", "Kafe & Dondurma", "WC"),
                    guideImportantNotes = listOf(
                        "2 kilometrelik kanyon içi ahşap yürüyüş patikasında orman havası alabilirsiniz."
                    ),
                    description = "Yedi göletin birbirine bağlandığı zengin bitki örtülü saklı cennet kanyonu.",
                    audioGuideTranscript = "Kurşunlu Şelalesi'ndesiniz. Zengin fauna ve florasıyla Antalya'ya varış öncesinde eşsiz bir dinlenme molası sunmaktadır.",
                    reviews = listOf(
                        UserReview("Dilek R.", "Google Maps", 4.8, "Geçen ay", "Çok huzurlu ve serin.")
                    ),
                    isSelectedForTrip = false
                )
            )
        ),
        RoadTripPreset(
            id = "ank_kap",
            title = "Ankara → Kapadokya: İpek Yolu & Peri Bacaları Rotası",
            origin = "Ankara",
            destination = "Nevşehir / Kapadokya",
            totalDistanceKm = 295,
            estimatedDriveMinutes = 210,
            description = "Tuz Gölü'nün sonsuz beyazlığından başlayarak Ağzıkarahan, Ihlara Kanyonu, Yeraltı Şehirleri ve Göreme Peri Bacaları vadilerine uzanan masalsı rota.",
            stops = listOf(
                WaypointStop(
                    id = "ank_kap_1",
                    name = "Tuz Gölü Panoramik Seyir & Yürüyüş Alanı",
                    subtitle = "Ufuksuz beyazlık ve pembe flamingoların durağı",
                    city = "Aksaray / Şereflikoçhisar",
                    category = WaypointCategory.SCENIC,
                    lat = 38.8542,
                    lng = 33.5186,
                    kmFromStart = 130,
                    drivingMinutesFromStart = 85,
                    recommendedStayMinutes = 40,
                    openTime = "00:00",
                    closeTime = "23:59",
                    alwaysOpen = true,
                    feeDescription = "Ücretsiz Giriş / Tesis Otoparkı 30 ₺",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.8,
                    reviewCount = 22400,
                    amenities = listOf("Tesis Otoparkı", "Kozmetik Dükkanı", "Ayak Yıkama Çeşmesi", "Kafeterya", "WC"),
                    guideImportantNotes = listOf(
                        "Ayakkabılarınızı çıkarıp tuz kristallerinin üzerinde yalınayak yürüyebilirsiniz (doğal peeling etkisi).",
                        "Yansıma fotoğrafları için kameranızı tuz tabakasının tam sıfır noktasına yaklaştırın.",
                        "Tuzlu sudan sonra çıkışta ayak yıkama alanını kullanabilirsiniz."
                    ),
                    description = "Türkiye'nin tuz ihtiyacının %40'ını karşılayan ve gökyüzünün yansımasıyla dev bir aynaya dönüşen eşsiz göl havzası.",
                    audioGuideTranscript = "Tuz Gölü seyir alanındasınız. Göl yüzeyindeki tuz mineralleri ve 'Dunaliella salina' algleri yaz aylarında göle yer yer pembe ve kızıl tonlar kazandırır. Yalınayak yürüyerek bu ferahlatıcı hissi deneyimleyebilirsiniz.",
                    reviews = listOf(
                        UserReview("Ceren D.", "Google Maps", 5.0, "Dün", "Fotoğraflar adeta bir rüya gibi çıkıyor. Mutlaka durulmalı."),
                        UserReview("Mark W.", "TripAdvisor", 5.0, "1 hafta önce", "Like walking on another planet! Reflection was stunning.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ank_kap_2",
                    name = "Ağzıkarahan Selçuklu Kervansarayı",
                    subtitle = "13. yüzyıl İpek Yolu anıtsal kalesi ve taç kapısı",
                    city = "Aksaray",
                    category = WaypointCategory.HISTORICAL,
                    lat = 38.4419,
                    lng = 34.1417,
                    kmFromStart = 215,
                    drivingMinutesFromStart = 145,
                    recommendedStayMinutes = 35,
                    openTime = "09:00",
                    closeTime = "18:30",
                    alwaysOpen = false,
                    feeDescription = "Giriş: 50 ₺ / Müzekart Geçerli",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.6,
                    reviewCount = 1450,
                    amenities = listOf("Otopark", "Rehber Panoları", "Mescit", "WC"),
                    guideImportantNotes = listOf(
                        "Muazzam taş işçiliğine sahip giriş taç kapısındaki geometrik kabartmaları inceleyin.",
                        "Avlu ortasındaki köşk mescidin merdivenleri etkileyici bir mimari sunar."
                    ),
                    description = "Anadolu Selçuklu döneminin günümüze en sağlam ulaşmış kervansaraylarından biri. İpek Yolu tüccarlarının konakladığı devasa taş kale.",
                    audioGuideTranscript = "İpek Yolu'nun tarihi durağı Ağzıkarahan'dasınız. 1230'lu yıllarda Alaeddin Keykubad devrinde inşa edilen bu han, tüccarların ve kervanların 3 gün boyunca ücretsiz barındığı ve güvenliğinin sağlandığı tarihi bir anıttır.",
                    reviews = listOf(
                        UserReview("Kemal B.", "Google Maps", 4.8, "Geçen hafta", "Taş işçiliği muazzam, yol üstü 30 dakikalık harika bir kültür molası.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ank_kap_3",
                    name = "Ihlara Vadisi & Melendiz Kanyonu",
                    subtitle = "Kaya oyma kiliseler ve su kenarı çardakları",
                    city = "Aksaray / Güzelyurt",
                    category = WaypointCategory.NATURE,
                    lat = 38.2547,
                    lng = 34.3014,
                    kmFromStart = 250,
                    drivingMinutesFromStart = 175,
                    recommendedStayMinutes = 90,
                    openTime = "08:30",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Giriş: 200 ₺",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.9,
                    reviewCount = 16800,
                    amenities = listOf("Otopark", "Kanyon İçi Nehir Çardakları", "WC", "Kafeterya", "Merdiven Asansörü"),
                    guideImportantNotes = listOf(
                        "Vadideki 382 basamaklı merdiven inişi bulunmaktadır, çıkış için tempo ayarlayınız.",
                        "Belisırma köyü mevkiinde suyun üzerinde kurulu tahta çardaklarda saç tava ve taze alabalık molası verilebilir.",
                        "Ağaçaltı ve Sümbüllü Kiliselerindeki erken Hristiyanlık fresklerini kaçırmayın."
                    ),
                    description = "Melendiz Çayı'nın volkanik araziyi aşındırmasıyla oluşan 14 kilometrelik görkemli kanyon ve içine oyulmuş yüzlerce tarihi kilise.",
                    audioGuideTranscript = "Ihlara Vadisi kanyonundayız. Yüzlerce metre dik kayalıkların arasında akan Melendiz Çayı boyunca yürürken, 9. ve 13. yüzyıllar arasında kayalara oyulmuş manastır ve kiliselerin eşsiz fresklerini keşfedeceksiniz.",
                    reviews = listOf(
                        UserReview("Zeynep O.", "Google Maps", 5.0, "3 gün önce", "Nehir üstü çardaklarda çay içmek ve yürümek ruhu dinlendiriyor."),
                        UserReview("François L.", "TripAdvisor", 5.0, "1 hafta önce", "Incredible canyon, peaceful atmosphere and amazing frescoes.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ank_kap_derinkuyu",
                    name = "Derinkuyu 8 Katlı Yeraltı Şehri",
                    subtitle = "85 metre derinlikte binlerce yıllık havalandırma ve tüneller",
                    city = "Nevşehir / Derinkuyu",
                    category = WaypointCategory.HISTORICAL,
                    lat = 38.3736,
                    lng = 34.7350,
                    kmFromStart = 270,
                    drivingMinutesFromStart = 190,
                    recommendedStayMinutes = 60,
                    openTime = "08:00",
                    closeTime = "19:00",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Giriş: 220 ₺",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.8,
                    reviewCount = 13500,
                    amenities = listOf("Otopark", "Rehber Hizmeti", "Hediyelik Dükkanlar", "WC"),
                    guideImportantNotes = listOf(
                        "Klimalı ve serindir (yıl boyu 13 derece), dar tünellerden geçerken dikkatli yürüyünüz.",
                        "Misyoner okulu, vaftiz havuzu ve kilise katlarını sırayla takip eden yön levhalarına uyunuz."
                    ),
                    description = "20.000 insanın hayvanları ve erzaklarıyla aylarca dış dünyaya çıkmadan yaşayabildiği dünyanın en büyük mühendislik harikası yeraltı şehri.",
                    audioGuideTranscript = "Derinkuyu Yeraltı Şehri'ndesiniz. Savunma amacıyla kayalara oyulmuş bu devasa sığınakta değirmen taşından dev kapılar, 55 metrelik havalandırma bacaları ve şaraphaneler bulunmaktadır.",
                    reviews = listOf(
                        UserReview("Berkant S.", "Google Maps", 4.9, "Geçen ay", "Mühendislik aklını zorluyor. Kapadokya'da mutlaka görülmeli.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "ank_kap_4",
                    name = "Tarihi Testi Kebabı & Dibek Mağara Restoranı",
                    subtitle = "Közde testi kırımı ve tandır lezzetleri",
                    city = "Nevşehir / Göreme",
                    category = WaypointCategory.RESTAURANT,
                    lat = 38.6431,
                    lng = 34.8289,
                    kmFromStart = 290,
                    drivingMinutesFromStart = 205,
                    recommendedStayMinutes = 70,
                    openTime = "11:30",
                    closeTime = "23:00",
                    alwaysOpen = false,
                    feeDescription = "Ortalama 400 - 700 ₺ / Kişi",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.8,
                    reviewCount = 4900,
                    amenities = listOf("Otopark", "Mağara Konsepti", "Canlı Testi Kırma Gösterisi", "Wi-Fi", "Mescit"),
                    guideImportantNotes = listOf(
                        "Testi kebabı közde yaklaşık 2 saat piştiği için gitmeden 1 saat önce ön sipariş vermek avantaj sağlar.",
                        "Közde testi kırılırken masanızda dilek tutma geleneğini deneyimleyin."
                    ),
                    description = "Geleneksel Avanos çömleklerinde kısık ateşte pişen kuzu eti, arpacık soğan ve sarımsak karışımı efsanevi Kapadokya lezzeti.",
                    audioGuideTranscript = "Kapadokya'nın en meşhur lezzet durağındasınız. Avanos toprağından yapılan mühürlü testilerde saatlerce pişen kebabınız masanızda özel satırla kırılarak servis edilecektir. Afiyet olsun.",
                    reviews = listOf(
                        UserReview("Caner V.", "Google Maps", 5.0, "Dün", "Testi kebabının yumuşaklığı ve aroması muazzamdı.")
                    ),
                    isSelectedForTrip = false
                )
            )
        ),
        RoadTripPreset(
            id = "izm_fet",
            title = "İzmir → Fethiye: Ege Kıyıları & Likya Yolu Keşfi",
            origin = "İzmir",
            destination = "Muğla / Fethiye",
            totalDistanceKm = 340,
            estimatedDriveMinutes = 260,
            description = "Efes Antik Kenti, Şirince, Bafa Gölü Herakleia, Akyaka Azmak Çayı ve Ölüdeniz rotasıyla Ege'nin en berrak suları ve antik zenginliği.",
            stops = listOf(
                WaypointStop(
                    id = "izm_fet_1",
                    name = "Efes Antik Kenti & Celsus Kütüphanesi",
                    subtitle = "Dünyanın en iyi korunmuş Roma metropolü",
                    city = "İzmir / Selçuk",
                    category = WaypointCategory.HISTORICAL,
                    lat = 37.9407,
                    lng = 27.3417,
                    kmFromStart = 78,
                    drivingMinutesFromStart = 55,
                    recommendedStayMinutes = 110,
                    openTime = "08:00",
                    closeTime = "19:30",
                    alwaysOpen = false,
                    feeDescription = "Müzekart Geçerli / Yabancı Giriş: 700 ₺",
                    isFree = false,
                    museumCardAccepted = true,
                    averageRating = 4.9,
                    reviewCount = 45000,
                    amenities = listOf("Geniş Otopark", "Ziyaretçi Merkezi", "Klimalı Müze Dükkanı", "Temiz WC", "Sesli Rehber"),
                    guideImportantNotes = listOf(
                        "Öğle sıcağından kaçınmak için sabah 08:30 veya akşamüstü 16:30'da giriş yapınız.",
                        "Yamaç Evler bölümü ek bilete tabidir ancak taban mozaikleri için kesinlikle görülmeye değerdir.",
                        "Güneş şapkası ve bol su bulundurunuz; mermer zemin güneşi yansıtır."
                    ),
                    description = "25.000 kişilik tiyatrosu, Celsus Kütüphanesi, Kuretler Caddesi ve anıtsal çeşmeleriyle antik dünyanın başkenti.",
                    audioGuideTranscript = "Efes Antik Kenti'ne geldiniz. Karşınızda yükselen 2 katlı görkemli cephe, Roma valisi Celsus adına oğlu tarafından MS 117 yılında yaptırılan kütüphanedir. Kütüphane nişlerindeki dört heykel; Bilgelik, Erdem, Zekâ ve Bilgiyi simgeler.",
                    reviews = listOf(
                        UserReview("Barış M.", "Google Maps", 5.0, "2 gün önce", "Her taşında tarih fışkırıyor. Celsus Kütüphanesi önünde fotoğraf çekilmeden dönülmez."),
                        UserReview("Sarah J.", "TripAdvisor", 5.0, "Geçen hafta", "One of the most impressive archaeological sites on earth!")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "izm_fet_sirince",
                    name = "Şirince Tarihi Köyü & Meyve Şarabı Mahzenleri",
                    subtitle = "Zeytinlikler arasında beyaz taş evler ve kumda Türk kahvesi",
                    city = "İzmir / Selçuk",
                    category = WaypointCategory.SCENIC,
                    lat = 37.9439,
                    lng = 27.4328,
                    kmFromStart = 88,
                    drivingMinutesFromStart = 68,
                    recommendedStayMinutes = 60,
                    openTime = "09:00",
                    closeTime = "22:00",
                    alwaysOpen = false,
                    feeDescription = "Köy Girişi Ücretsiz / Otopark: 50 ₺",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.7,
                    reviewCount = 31000,
                    amenities = listOf("Otopark", "Köy Çarşısı", "Tarihi Kiliseler", "Kafe & Restoranlar", "WC"),
                    guideImportantNotes = listOf(
                        "Köy meydanındaki közde kumda pişen damla sakızlı Türk kahvesini deneyin.",
                        "St. John Baptist Kilisesi avlusundan tüm vadinin panoramik fotoğrafını çekebilirsiniz."
                    ),
                    description = "Maya takvimi kehanetleriyle dünya çapında ünlenen, incir ve şeftali bahçeleriyle çevrili otantik Ege köyü.",
                    audioGuideTranscript = "Şirince'desiniz. Arnavut kaldırımlı sokakları ve geleneksel mimarisiyle ünlü köyde, tarihi taş mahzenleri ve yerel lezzetleri tadabilirsiniz.",
                    reviews = listOf(
                        UserReview("Merve K.", "Google Maps", 4.8, "Geçen hafta", "Sokakları gezmek ve kumda kahve içmek çok dinlendirici.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "izm_fet_2",
                    name = "Bafa Gölü & Çam İçi Zeytinlik Mola Noktası",
                    subtitle = "Latmos Dağları silueti ve göl kenarı yılan balığı",
                    city = "Aydın / Didim - Muğla Sınırı",
                    category = WaypointCategory.SCENIC,
                    lat = 37.5028,
                    lng = 27.4244,
                    kmFromStart = 155,
                    drivingMinutesFromStart = 115,
                    recommendedStayMinutes = 40,
                    openTime = "00:00",
                    closeTime = "23:59",
                    alwaysOpen = true,
                    feeDescription = "Ücretsiz Manzara Seyri",
                    isFree = true,
                    museumCardAccepted = false,
                    averageRating = 4.7,
                    reviewCount = 3800,
                    amenities = listOf("Yol Üstü Otopark", "Göl Manzaralı Kafe", "Zeytinyağı Satış Noktası", "WC"),
                    guideImportantNotes = listOf(
                        "Göl kıyısındaki Kapıkırı köyünde bulunan Herakleia antik kalıntılarını uzaktan seyredebilirsiniz.",
                        "Yöresel zeytinyağı ve taze kekik almak için ideal duraktır."
                    ),
                    description = "Eski bir Ege denizi koyuyken Büyük Menderes alüvyonlarıyla göle dönüşen, Latmos dağlarının dev kayalıklarıyla çevrili mitolojik göl.",
                    audioGuideTranscript = "Bafa Gölü kenarındasınız. Mitolojide Ay Tanrıçası Selene ile Çoban Endymion'un aşkına sahne olan Latmos Dağları eteklerinde, antik Herakleia kalıntılarını ve göl üzerindeki küçük adacıkları izlemektesiniz.",
                    reviews = listOf(
                        UserReview("Tolga R.", "Google Maps", 4.8, "Geçen ay", "Yol üstü durup sakin göl manzarasında çay içmek tüm yorgunluğu alıyor.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "izm_fet_3",
                    name = "Akyaka Kadın Azmağı Nehri & Tekne Turu",
                    subtitle = "Buz gibi berrak akvaryum suyu ve sazlıklar",
                    city = "Muğla / Ula",
                    category = WaypointCategory.NATURE,
                    lat = 37.0542,
                    lng = 28.3292,
                    kmFromStart = 230,
                    drivingMinutesFromStart = 170,
                    recommendedStayMinutes = 60,
                    openTime = "08:00",
                    closeTime = "20:00",
                    alwaysOpen = false,
                    feeDescription = "Nehir Tekne Turu: 100 ₺ / Kişi",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.9,
                    reviewCount = 17500,
                    amenities = listOf("Otopark", "Nehir Kenarı Restoranlar", "Tekne İskelesi", "WC", "Doğal Park"),
                    guideImportantNotes = listOf(
                        "Azmak nehrinde 15-20 dakikalık çevre dostu elektrikli tekne turuna mutlaka katılın; suyun altındaki bitki örtüsü ve ördekleri net görürsünüz.",
                        "Su sıcaklığı yıl boyu 8-10 derecedir, serinlemek için eşsizdir."
                    ),
                    description = "Denizle buluşan, dipten kaynayan sodalı ve mineralli berrak tatlı su nehri. Doğal bir akvaryum ekosistemi.",
                    audioGuideTranscript = "Cittaslow unvanlı Akyaka'dayız. Kadın Azmağı deresinin berrak sularında süzülürken su samurlarını, ördekleri ve metrelerce derinlikteki sualtı ormanlarını cam gibi seyredebilirsiniz.",
                    reviews = listOf(
                        UserReview("Aslıhan B.", "Google Maps", 5.0, "4 gün önce", "Su o kadar berrak ki akvaryum gibi balıkları sayabiliyorsunuz."),
                        UserReview("Okan K.", "Foursquare", 4.9, "1 hafta önce", "Nehir kenarında taze balık ekmek molası harika.")
                    ),
                    isSelectedForTrip = false
                ),
                WaypointStop(
                    id = "izm_fet_4",
                    name = "Ölüdeniz & Babadağ Teleferik Seyir Zirvesi",
                    subtitle = "1700 m yükseklikten turkuaz lagün ve yamaç paraşütleri",
                    city = "Muğla / Fethiye",
                    category = WaypointCategory.SCENIC,
                    lat = 36.5492,
                    lng = 29.1417,
                    kmFromStart = 335,
                    drivingMinutesFromStart = 250,
                    recommendedStayMinutes = 90,
                    openTime = "09:00",
                    closeTime = "21:00",
                    alwaysOpen = false,
                    feeDescription = "Teleferik Çıkış: 350 ₺ / Seyir Terası Ücretsiz",
                    isFree = false,
                    museumCardAccepted = false,
                    averageRating = 4.9,
                    reviewCount = 12900,
                    amenities = listOf("Otopark", "Teleferik İstasyonu", "Panoramik Restoran", "Fotoğraf Noktaları", "WC"),
                    guideImportantNotes = listOf(
                        "Gün batımından 1 saat önce zirveye çıkarsanız gökyüzündeki yüzlerce renkli paraşüt ve batan güneş unutulmaz kareler sunar.",
                        "Zirvede rüzgar olabileceğinden rüzgarlık önerilir."
                    ),
                    description = "Dünyanın en iyi yamaç paraşütü merkezlerinden biri olan Babadağ'dan Ölüdeniz lagününün ve Rodos adasının panoramik seyri.",
                    audioGuideTranscript = "Rotamızın son noktası olan Ölüdeniz ve Babadağ'a ulaştınız. Likya kıyılarının incisi olan lagünün turkuaz sularını ve gökyüzünde süzülen yamaç paraşütlerini izleyebilirsiniz.",
                    reviews = listOf(
                        UserReview("Engin T.", "Google Maps", 5.0, "Dün", "Manzara tek kelimeyle nefes kesici. Teleferikle çıkış çok konforlu.")
                    ),
                    isSelectedForTrip = false
                )
            )
        )
    )

    fun findPreset(origin: String, destination: String): RoadTripPreset? {
        val o = origin.trim().lowercase()
        val d = destination.trim().lowercase()
        return presets.find { preset ->
            (preset.origin.lowercase().contains(o) || o.contains(preset.origin.lowercase())) &&
            (preset.destination.lowercase().contains(d) || d.contains(preset.destination.lowercase()))
        }
    }
}

