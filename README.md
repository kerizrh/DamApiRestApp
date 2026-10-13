# DamApiRestApp - Aplicación Móvil Android (DAM235)

Cliente móvil desarrollado en **Android nativo (Java)** con **Retrofit 2** y **Gson** para el consumo de la API REST con autenticación JWT, correspondiente a la Evaluación Práctica (Punto 1, 40%) de la asignatura **Desarrollo de Aplicaciones Móviles (DAM235)** de la Universidad de El Salvador.

---

## 📱 Pantallas y Funcionalidades

1. **`LoginActivity`:**
   - Formulario de autenticación con Material Design.
   - Petición asíncrona a `POST /auth/login` enviando credenciales (`user` y `pass`).
   - Recepción y almacenamiento persistente del token JWT mediante `SessionManager` (`SharedPreferences`).

2. **`ProductsActivity`:**
   - Consumo del endpoint protegido `GET /products` enviando la cabecera `Authorization: Bearer <token>`.
   - Visualización del catálogo de productos en un `RecyclerView` mediante tarjetas `CardView`.
   - Botón de cierre de sesión con invalidación de token local y redirección al login.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 11
- **Cliente HTTP / REST:** Retrofit 2.11.0 + OkHttp Logging Interceptor
- **Conversor JSON:** Gson Converter 2.11.0
- **Interfaz de Usuario:** Material Components, ConstraintLayout, RecyclerView, CardView
- **Configuración de Red:** `network_security_config.xml` para permitir tráfico HTTP en entornos locales de desarrollo.

---

## 🚀 Cómo Ejecutar en Android Studio

1. **Abrir el proyecto:**
   - Abre **Android Studio**.
   - Selecciona **File -> Open...**
   - Elige la carpeta: `G:\UES\DAM APIREST\DamApiRestApp`

2. **Sincronizar Gradle:**
   - Deja que Android Studio descargue las dependencias y sincronice el proyecto.

3. **Verificar que la API REST esté corriendo:**
   - Asegúrate de que el backend Spring Boot esté ejecutándose en tu PC (`http://localhost:8085`).
   - *Nota:* En el emulador oficial de Android (AVD), la URL base configurada en `ApiClient.java` es `http://10.0.2.2:8085/api/v1/demoapirestdam235/`, la cual conecta directamente al servidor local de tu PC.

4. **Credenciales de Prueba:**
   - **Usuario:** `mmartinez@temp.com`
   - **Contraseña:** `Temp123.`
