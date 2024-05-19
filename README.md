# Gyógyszertár kezelő alkalmazás

## Pontozási segédlet:
A nem egyértelmű dolgokhoz, hogy ne kelljen sokat keresgélni!
- **Adatmodell:** A `Pharmacy` osztály megtalálható a `modell` mappában.
- **3 különböző activity:** 
  - `MainActivity`: ConstraintLayout
  - `AddPharmacyActivity`: LinearLayout
  - `ItemActivity`: RelativeLayout
- **Animáció:**
  1. `MainActivity.showAlertDialog`: `fade_in.xml`
  2. `AddPharmacyActivity.onCreate.onClick`
- **Lifecycle Hook:** `onStart` (spinner adatainak frissítése)
- **Permission:** Az engedély a `AndroidManifest.xml` 38. sorában található.
- **Értesítés (Notification):** `showAlertDialog` (több Activity-ben is)
- **CRUD műveletek:** 
  - C: `AddPharmacyActivity`
  - R: `PharmacyActivity`
  - U/D: `DeleteorupdateActivity`
<br>(Külön szálon futnak)
