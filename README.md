# 📱 Kilo Takibi

Kulllanıcının manuel olarak girmiş olduğu kilo verileri ile kilosunu takip etmesini ve grafik yardımı ile kilo grafiğini günlük olarak görmesini amaçlayan basit bir uygulama.


[![Get it on Google Play Store](https://upload.wikimedia.org/wikipedia/commons/7/78/Google_Play_Store_badge_EN.svg)](https://play.google.com/store/apps/details?id=com.yusufcanmercan.weight_track_app&pcampaignid=web_share)

---

## 🛠️ Özellikler

- Manuel input ile kilo verisi ekleyip listelenmesi.
- Eklenen kilo verilerinin grafik yardımıyla gösterilmesi.
- Anasayfadaki tablo yardımı ile kilo değişim verilerinin gösterilmesi.

---

## 📸 Ekran Görüntüleri
![KiloTakibiLight](https://github.com/user-attachments/assets/27a134e9-0bbc-4112-a08e-84a6cee200eb)
![KiloTakibiDark](https://github.com/user-attachments/assets/e0c75c87-8a2f-4304-8671-328122fb3897)

---

## 🛠️ Kullanılan Teknolojiler

- **Kotlin Kapt**: Proje içinde veri bağlama veya DI (bağımlılık enjeksiyonu) işlemlerinde kullanılan, derleme zamanında anotasyonları işleyerek kod üretimini kolaylaştıran bir araçtır.
- **Kotlin Ksp**: Daha hızlı kod üretimi için kullanılan, özellikle Room gibi veri tabanı bileşenleriyle uyumlu bir sembol işleme aracıdır.
- **Hilt**: Uygulamanın modüler bir yapıda geliştirilmesi ve bağımlılıkların kolayca yönetilmesi için tercih edilen bir DI kütüphanesidir.
- **Jetpack Navigation**: Kilo Takibi uygulamasında farklı ekranlar (ör. kilo verisinin listesi, kilo grafiği) arasında kullanıcı dostu ve hatasız gezinmeyi sağlar.
- **Jetpack Navigation Safe Args**: Jetpack Navigation ile birlikte kullanılan, güvenli veri geçişini sağlayan bir araçtır.
- **Coroutines**: Ağ istekleri veya veritabanı işlemleri gibi asenkron görevlerin daha etkin bir şekilde yönetilmesine olanak tanır.
- **Lifecycle**: Uygulamanın yaşam döngüsüne bağlı bileşenlerin doğru yönetilmesi ve kaynakların optimize edilmesi için kullanılır.
- **Room**: Yerel SQLite veritabanıyla daha kolay ve güvenilir veri depolama işlemleri gerçekleştirilmesini sağlar.
- **MPAndroidChart**: Android uygulamalarında grafik ve veri görselleştirmesi yapmak için kullanılan bir kütüphanedir.

---

## 📢 Proje Yapısı

```
/app
  /core
  /data
    /local
    /model
    /repository
  /di
  /ui
    /adapter
    /state
    /view
        /add
        /edit
        /graph
        /home
        /main
    /viewmodel
  /util
    /graph
    /helper
    /view
```

---

## 🛡️ Lisans

Bu proje [MIT Lisansı](LICENSE) ile lisanslanmıştır. Daha fazla bilgi için `LICENSE` dosyasına
bakabilirsiniz.

---

## 📞 İletişim

Herhangi bir soru veya öneriniz varsa, benimle iletişime geçebilirsiniz:

- LinkedIn: [linkedin.com/in/cusufcan](https://linkedin.com/in/cusufcan)
- Email: [yusufcanmercan.info@gmail.com](mailto:yusufcanmercan.info@gmail.com)
