# Todroid 

Una aplicación de lista de tareas (To-Do) para Android, desarrollada de forma nativa utilizando **Jetpack Compose** y **Kotlin**. Este proyecto está diseñado siguiendo las mejores prácticas de arquitectura moderna de Android (MVVM) e implementa almacenamiento local robusto con **Room Database**.

## Características Principales

* **Gestión de Tareas:** Crea, edita, elimina y visualiza tus tareas de forma sencilla.
* **Persistencia de Datos:** Los datos se guardan localmente en el dispositivo utilizando Room Database, asegurando que no pierdas información al cerrar la app.
* **Filtros Dinámicos:** Capacidad de filtrar las tareas entre estados "Completadas" y "Pendientes" para una mejor organización.
* **Interfaz de Usuario Moderna:** Interfaz construida íntegramente con Jetpack Compose y Material Design 3.
* **Modo Claro / Oscuro:** Soporte nativo y automático para los temas claro y oscuro del sistema.
* **Arquitectura Escalable:** Estructura basada en MVVM (Model-View-ViewModel) para separar la lógica de negocio de la interfaz gráfica.

## Tecnologías y Herramientas

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Diseño:** [Material Design 3 (Material You)](https://m3.material.io/)
* **Arquitectura:** MVVM (Model-View-ViewModel)
* **Base de Datos:** [Room](https://developer.android.com/training/data-storage/room) con KSP (Kotlin Symbol Processing)
* **Gestión de Estado:** StateFlow / LiveData & ViewModel Compose
* **Versiones del SDK:** Min SDK 24 / Target SDK 36

##  Estructura del Proyecto

El código fuente principal se encuentra en `app/src/main/java/com/plataformas/todo/` y está organizado de la siguiente manera:

```text
com.plataformas.todo
│
├── data/                  # Capa de datos y persistencia
│   ├── Todo.kt            # Entidad (Modelo de datos)
│   ├── TodoDao.kt         # Data Access Object para operaciones en la BD
│   ├── TodoDatabase.kt    # Configuración de Room Database
│   └── TodoRepository.kt  # Repositorio (única fuente de verdad)
│
└── ui/                    # Capa de presentación (UI)
    ├── TodoViewModel.kt   # ViewModel para gestionar el estado de la UI
    ├── components/        # Componentes reutilizables de Jetpack Compose
    ├── screens/           # Pantallas principales de la aplicación
    └── theme/             # Configuración de colores, tipografía y formas (Material 3)
