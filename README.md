# NIT3213 Android Final Assignment

A simple 3-screen Android app (**Login → Dashboard → Details**) that logs into the
`vu-nit3213-api`, fetches a list of entities, and shows the full detail of any item.

Built with the standard modern Android stack so each marked component is easy to point to.

## How to build & run

1. Open **Android Studio** → **Open** → select this `NIT3213App` folder.
2. Wait for Gradle to sync (it downloads Gradle 8.7 and the libraries the first time).
3. Pick an emulator or a connected device and press **Run ▶**.
4. On the **Login** screen:
   - Choose your **campus location** (footscray / sydney / ort).
   - **Username** = student id-> s8171018, **Password** = first_name-> Rajan.
   - Press **Login**.

Run the unit tests from the terminal with:

```bash
./gradlew test
```

## Where each required component lives

| Requirement | Where it is | File |
|---|---|---|
| **Retrofit** (networking) | API interface | `data/remote/ApiService.kt` |
| **Moshi** (JSON parsing) | response models + DI | `data/model/`, `di/AppModule.kt` |
| **Coroutines / `viewModelScope`** | async API calls | the two ViewModels |
| **Kotlin Flow (`StateFlow`)** | screen state | the two ViewModels |
| **RecyclerView + Adapter + ViewHolder** | list of entities | `ui/dashboard/EntityAdapter.kt` |
| **Repository pattern** | middle layer | `data/repository/` |
| **Dependency Injection (Hilt)** | wiring + Singletons | `di/AppModule.kt`, `NitApplication.kt` |
| **Singleton pattern** | shared Retrofit/Repository | `@Singleton` in `di/AppModule.kt` |
| **Unit tests (JUnit + Mockito)** | ViewModel tests | `app/src/test/...` |

## How the app flows

```
LoginActivity ──(keypass)──> DashboardActivity ──(Entity)──> DetailsActivity
     │                            │                              │
LoginViewModel              DashboardViewModel              (shows all fields
     │                            │                           incl. description)
     └────────── NitRepository (interface) ──────────┘
                        │
                  ApiService (Retrofit) ──> https://nit3213api.onrender.com/
```

## Project structure

```
app/src/main/java/com/example/nit3213/
├── NitApplication.kt          # @HiltAndroidApp
├── data/
│   ├── model/                 # LoginRequest, LoginResponse, DashboardResponse, Entity
│   ├── remote/ApiService.kt   # Retrofit endpoints
│   └── repository/            # NitRepository (interface) + NitRepositoryImpl
├── di/AppModule.kt            # Hilt: Retrofit, OkHttp, Moshi, Repository (Singletons)
└── ui/
    ├── login/                 # LoginActivity + LoginViewModel
    ├── dashboard/             # DashboardActivity + DashboardViewModel + EntityAdapter
    └── details/               # DetailsActivity
```
