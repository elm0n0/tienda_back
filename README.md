# 🛍️ Tienda Online - Backend

Este proyecto es el backend de una tienda online desarrollado con Spring Boot y Java 17. Su objetivo es gestionar productos, usuarios y pedidos, proporcionando una API RESTful para interactuar con el frontend.

## 🚀 Tecnologías utilizadas

- ⚙️ Spring Boot: Framework para el desarrollo de aplicaciones Java.
- ☕ Java 17: Lenguaje de programación utilizado.
- 🛠️ Maven: Herramienta de construcción y gestión de dependencias.
- 🔐 Spring Security: Seguridad y autenticación de usuarios.
- 🗄️ JPA: Acceso a bases de datos con Java Persistence API.

---

## 📁 Estructura del proyecto

```bash
tienda/
├── src/
│   ├── main/
│   │   ├── java/           						# Código fuente de la aplicación
│   │   │   └── com/
│   │   │	   └── elm0n0
│   │   │		  └── tienda/
│   │   │           	  ├── auth/  					# carpeta que da nombre al servicio que controla
│   │   │           	  │	  ├── controller/			# Controlador de auth
│   │   │           	  │	  ├── dao/				# clases de acceso a bbdd de auth
│   │   │           	  │	  ├── dto/				# clases de transferencia del controllador auth al exterior
│   │   │           	  │	  ├── enums/				# clases enumeradas de auth
│   │   │           	  │	  ├── exception/			# excepciones lanzadas por el controlador de auth
│   │   │           	  │	  ├── mapper/				# mappers usados para convertir dao's en dto's y viceversa de auth
│   │   │           	  │	  ├── repository/			# repositorio de bbdd de auth
│   │   │           	  │	  ├── service/			# service de la logica de negocio de auth
│   │   │           	  │	  ├── validator/			# validaciones realizadas en la logica de auth
│   │   │           	  ├── security/ 				# logica de la seguridad aplicada a la api
│   │   │           	  ├── utils/					# clases de utilidad para todos los controladores
│   │   │           	  └── TiendaApplication.java  	# Clase principal de arranque
│   │   ├── resources/
│   │   │   ├── application.properties  			# Configuraciones de la aplicación (base de datos, seguridad, etc.)
│   │   │   └── static/                 			# Archivos estáticos
├── .gitignore          							# Archivos y directorios a ignorar en git
├── pom.xml             							# Dependencias y configuración de Maven
```

---

## 🛠️ Scripts disponibles
En el proyecto puedes usar los siguientes comandos para facilitar el desarrollo:

```bash
mvn install          # Instala todas las dependencias del proyecto
mvn spring-boot:run  # Inicia el servidor de desarrollo (Spring Boot)
mvn clean package    # Genera una build optimizada para producción
```

---

## 🧱 Estado del desarrollo
- Estructura básica con Spring Boot
- Seguridad implementada con Spring Security
- Base de datos configurada con MongoDB
- El proyecto está en constante desarrollo, con nuevas características planeadas para ser añadidas en el futuro.

---

## ⚙️ Configuración de la base de datos
Este proyecto usa **MongoDB** como base de datos. Para configurar tu entorno local:

1. Asegúrate de tener una BBDD de MongoDB disponible.
2. Modifica las credenciales y configuraciones de la base de datos en el archivo `application.properties`.
podria ser algo como esto:
```
spring.application.name= # nombre de la aplicacion
spring.data.mongodb.uri=mongodb+srv://<usuario>:<contraseña>@<cluster-url>/<nombre-bbdd>?retryWrites=true&w=majority&appName=<nombre-bbdd>

# JWT Configuration
jwt.secret.key=<tu-clave-secreta>  # Asegúrate de generar una clave secreta segura
jwt.expiration.time=<tiempo-en-ms>  # Expiración del token en milisegundos
jwt.refresh.token.expiration.time=<tiempo-en-ms>  # Expiración del token de refresco en milisegundos
```
Por ejemplo, si tu base de datos se llama tienda, y tu usuario es juanjo, la URL de conexión sería algo como:
spring.data.mongodb.uri=mongodb+srv://juanjo:miContraseña123@tiendaonline.svzd6.mongodb.net/tienda?retryWrites=true&w=majority&appName=tienda
   
---

## 🌐 Cómo contribuir
Si deseas contribuir al proyecto, sigue estos pasos:

Haz un fork del repositorio.
Crea una rama nueva (git checkout -b feature/nueva-funcionalidad).
Realiza tus cambios.
Haz un commit con tus cambios (git commit -m "feat: descripción de lo que hiciste").
Sube la rama a tu repositorio (git push origin feature/nueva-funcionalidad).
Abre un Pull Request en el repositorio original.
Contribuciones son bienvenidas 🚀

---

## 📄 Licencia
Este proyecto está bajo una licencia propietaria. No se permite su uso, distribución, modificación o adaptación sin el consentimiento expreso y por escrito del propietario. Para obtener más información, por favor contacta al propietario a través de [juanjo.perez.plaza@gmail.com].


Proyecto desarrollado por elm0n0
