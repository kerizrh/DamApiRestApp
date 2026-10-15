# DamApiRestApp - Aplicación Móvil Android (DAM235)

Cliente móvil nativo desarrollado en **Android (Java)** con **Retrofit 2** y **Gson** para el consumo de la API REST con autenticación JWT, correspondiente a la Evaluación Práctica de la cátedra **Desarrollo de Aplicaciones Móviles (DAM235)** de la Universidad de El Salvador.

---

## 📱 Pantallas y Funcionalidades de la Evaluación

### 1. `LoginActivity` (Punto 1 - 40%)
- Formulario de autenticación con Material Design.
- Consumo asíncrono de `POST /auth/login` enviando credenciales (`user` y `pass`).
- Almacenamiento persistente del token JWT mediante `SessionManager` (`SharedPreferences`).
- Enlace directo a la pantalla de registro para usuarios nuevos.

### 2. `RegisterActivity` (Punto 3 - 10%)
- Formulario de creación de nuevas cuentas: Nombres, Apellidos, Correo Electrónico y Contraseña.
- Consumo asíncrono de `POST /auth/register` vía Retrofit.
- Validaciones en tiempo de ejecución de campos requeridos y control de progreso visual (`ProgressBar`).
- Notificación Toast y redirección al Login tras el registro exitoso.

### 3. `ProductsActivity` (Puntos 1 y 4 - 80% total)
- **Consulta de Catálogo (Punto 1):** Consumo del endpoint protegido `GET /products` enviando la cabecera `Authorization: Bearer <token>` y renderizado en `RecyclerView` con tarjetas `CardView`.
- **Creación de Productos (Punto 4):** Botón flotante `FloatingActionButton` (+) que despliega un diálogo modal para registrar un nuevo producto (`POST /products`).
- **Modificación de Productos (Punto 4):** Botón de edición en cada tarjeta que permite actualizar el nombre y estado activo/inactivo del producto (`PUT /products/{id}`).
- **Eliminación de Productos (Punto 4):** Botón de papelera en cada tarjeta con diálogo de confirmación previo a eliminar (`DELETE /products/{id}`).
- **Cierre de Sesión (Punto 2):** Botón `Cerrar Sesión` que notifica a `POST /auth/logout`, destruye el token en `SharedPreferences` y redirige al inicio de sesión.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 11
- **Cliente HTTP / REST:** Retrofit 2.11.0 + OkHttp Logging Interceptor
- **Conversor JSON:** Gson Converter 2.11.0
- **Interfaz de Usuario:** Material Components, ConstraintLayout, RecyclerView, CardView, FloatingActionButton, SwitchMaterial
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
   - **Usuario por defecto:** `mmartinez@temp.com`
   - **Contraseña:** `Temp123.`
   - O bien, crea una nueva cuenta desde el botón **"Regístrate aquí"**.
