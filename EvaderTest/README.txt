EVADERTEST — APK Qoriqchi himoyasini sinash ilovasi
====================================================

A) ANDROID STUDIOSIZ APK YIG'ISH (be pul GitHub orqali, 10 daqiqa):
   1. github.com da bepul akkaunt oching.
   2. "New repository" yarating (nom: EvaderTest, Public).
   3. Repozitoriyga YUKLASHDAN OLDIN:
      app/src/main/assets/ ichiga qo'ying:
      - target.apk  (package nomi com.example.targetapk bo'lsin,
        yoki MainActivity.kt dagi TARGET_PKG ni o'zgartiring)
      - split.apk   (3-zaiflik uchun istalgan kichik APK)
   4. EvaderTest ichidagi BARCHA fayllarni (shuningdek .github papkasini,
      yashirin fayl - yuklashda e'tibor bering) repozitoriyga yuklang.
      Eng osoni: "uploading an existing file" orqali har bir faylni qo'lda
      tashlash, yoki git buyruqlari bilan.
   5. Yuklashdan keyin "Actions" yorlig'iga o'ting — "Build APK" ishga tushadi.
      3-5 daqiqa kutiling (yashil belgi chiqadi).
   6. "Build APK" ishiga kiring, pastdagi "Artifacts" bo'limidan
      EvaderTest-apk ni yuklab oling — ichida app-debug.apk bor.

B) TEST PROTOKOLI:
   1. APK Qoriqchi himoyasini yoqing.
   2. Har bir tugmani navbat bilan bosing, "O'rnatish" ni tasdiqlang.
   3. Natija:
      - "✅✅✅ N-zaiflik: TARGET O'RNATILDI" -> himoyangizda N-zaiflikdan himoya YO'Q
      - "⛔ N-zaiflik: target o'rnatilmadi"   -> himoya ushbu oqimni blokladi
      - 3-zaiflik ishga tushmasa -> avval 1-zaiflik bilan targetni o'rnating
   4. Ilova birinchi ochilishda "Noma'lum ilovalarni o'rnatish" ruxsatini so'raydi — bering.

ESLATMA: Bu ilova faqat o'zingiz ishlab chiqayotgan himoya ilovasini sinash uchun.
