EVADERTEST — APK Qoriqchi himoyasini sinash ilovasi
====================================================
LOYIHA IKKI QISMDAN IBORAT:
  - EvaderTest (ildiz papka)  -> 4 ta evasion vektorini sinovchi ilova
  - target/                   -> o'rnatiladigan target ilova
                                 (package: com.example.targetapk,
                                 ochilganda "TEST MUVAFFAQIYATLI" ko'rsatadi)

ANDROID STUDIOSIZ APK YIG'ISH (bepul GitHub orqali, ~10 daqiqa):
   1. github.com da bepul akkaunt oching.
   2. "New repository" yarating (nom: EvaderTest, Public).
   3. Zip ichidagi BARCHA fayl va papkalarni (target/ va yashirin
      .github/ papkasini ham!) repozitoriyga yuklang.
      Hech qanday APK qo'lda qo'yish SHART EMAS - workflow o'zi target
      APK ni yig'ib assets ga joylaydi.
   4. "Actions" yorlig'ida "Build APKs" ishga tushadi. 5-10 daqiqa
      kutiling (yashil belgi).
   5. "Artifacts" bo'limidan EvaderTest-barcha-apk ni yuklab oling.
      Ichida:
        app-debug.apk         <- EvaderTest (telefonga o'rnatiladigan)
        target/app-debug.apk  <- target (faqat zarur bo'lsa)

TEST PROTOKOLI:
   1. EvaderTest (app-debug.apk) ni telefonga o'rnating, "Noma'lum
      ilovalarni o'rnatish" ruxsatini bering.
   2. APK Qoriqchi himoyasini yoqing.
   3. EvaderTest da 4 ta tugmani navbat bilan bosing, "O'rnatish" ni
      tasdiqlang.
   4. Natija:
      - "✅✅✅ N-zaiflik: TARGET O'RNATILDI" -> himoyangizda N-zaiflikdan
        himoya YO'Q - tuzatish kerak.
      - "⛔ N-zaiflik: target o'rnatilmadi"   -> himoya ushbu oqimni blokladi.
      - 3-zaiflik ishga tushmasa -> avval 1-zaiflik bilan targetni o'rnating.
   5. Target o'rnatilsa, u ochilganda "TEST MUVAFFAQIYATLI" ekrani
      ko'rinadi - bu himoya yengilganini tasdiqlaydi.

ESLATMA: Bu ilovalar faqat o'zingiz ishlab chiqayotgan himoya
ilovasini sinash uchun mo'ljallangan.
