# 🔐 Sistema de Autenticación con Spring Boot

Sistema completo de registro de usuarios con activación por token, recuperación de contraseña y autenticación JWT.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos Previos](#-requisitos-previos)
- [Configuración](#️-configuración)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Documentación API](#-documentación-api)
- [Frontend](#️-frontend)
- [Flujo de Uso](#-flujo-de-uso)
- [Notas Importantes](#-notas-importantes)

## ✨ Características

- ✅ Registro de usuarios (Nombre completo + Email)
- ✅ Activación de cuenta mediante token de un solo uso
- ✅ Asignación de contraseña segura con validación de token
- ✅ Inicio de sesión con JWT (JSON Web Token)
- ✅ Recuperación de contraseña mediante token temporal
- ✅ Validación de usuarios activos
- ✅ Envío de correos en modo mock (listo para SMTP real)
- ✅ Documentación automática con Swagger/OpenAPI
- ✅ Arquitectura en capas (Controllers, Services, Repositories)
- ✅ Frontend estático con Bootstrap

## 🧰 Tecnologías

| Tecnología | Versión |
|-----------|---------|
| Java | 21 (compatible con 17+) |
| Spring Boot | Latest |
| Spring Security | Latest |
| Spring Data JPA | Latest |
| MySQL | 8.0+ |
| Hibernate | Latest |
| JJWT | Latest |
| Swagger/OpenAPI | springdoc-openapi |
| Bootstrap | 5.x |

## 📦 Requisitos Previos

- **Java JDK 17+** instalado
- **MySQL 8.0+** corriendo en `localhost:3306`
- **Maven 3.6+** (o usar el wrapper incluido)
- Git (opcional)

## ⚙️ Configuración

### 1. Base de Datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE prueba_backend;
```

### 2. Archivos de Configuración

El proyecto utiliza perfiles de Spring para separar la configuración general de los secretos:

#### `application.yaml` (configuración general)

```yaml
spring:
  application:
    name: prueba-backend
  datasource:
    url: jdbc:mysql://localhost:3306/prueba_backend?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080

app:
  base-url: "http://localhost:8080"
  jwt:
    expiration-seconds: 3600
```

#### `application-local.yaml` (secretos - **NO SUBIR A GIT**)

```yaml
spring:
  datasource:
    password: TU_PASSWORD_MYSQL

app:
  jwt:
    secret: "PON_UN_SECRET_LARGO_Y_ALEATORIO_DE_AL_MENOS_32_CARACTERES"
```

> ⚠️ **Importante:** Agrega `application-local.yaml` a tu `.gitignore`

### 3. Activar Perfil Local

**IntelliJ IDEA:**
```
VM Options: -Dspring.profiles.active=local
```

**Terminal/Command Line:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## 🚀 Instalación y Ejecución

### Compilar el proyecto

```bash
mvn clean install
```

### Ejecutar la aplicación

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

La aplicación estará disponible en:
```
http://localhost:8080
```

## 📚 Documentación API

### Swagger UI (Interfaz Interactiva)
```
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI JSON
```
http://localhost:8080/v3/api-docs
```

### Endpoints Principales

#### UserController
- `POST /users/register` - Registrar nuevo usuario
- `POST /users/activate` - Activar cuenta con token
- `POST /users/recover-password` - Solicitar recuperación de contraseña
- `POST /users/reset-password` - Restablecer contraseña con token
- `GET /users/me` - Obtener información del usuario autenticado (requiere JWT)

#### SessionController
- `POST /session/login` - Iniciar sesión (retorna JWT)

## 🖥️ Frontend

El frontend está incluido en el proyecto como recursos estáticos:

```
src/main/resources/static/
```

### Páginas Disponibles

| Ruta | Descripción |
|------|-------------|
| `/index.html` | Página de inicio de sesión |
| `/register.html` | Registro de nuevos usuarios |
| `/recovery.html` | Solicitud de recuperación de contraseña |
| `/set-password.html` | Asignación de contraseña (activación y recovery) |
| `/dashboard.html` | Panel protegido (requiere JWT) |

## 📌 Notas Importantes

### Seguridad

- ✅ Los tokens de activación y recuperación son de **un solo uso** (se marcan como `usedAt` al utilizarse)
- ✅ Los tokens tienen **fecha de expiración** configurable
- ✅ Las contraseñas se almacenan con **BCrypt**
- ✅ Solo usuarios activos pueden iniciar sesión
- ✅ El JWT tiene tiempo de expiración (default: 1 hora)

### Correos Electrónicos

El sistema actualmente usa un **servicio mock** que imprime los enlaces en la consola del servidor. Está preparado para reemplazarse fácilmente por:
- SMTP (Gmail, SendGrid, etc.)
- Servicios de email transaccional (Mailgun, AWS SES, etc.)

## 👤 Autor

- [**Samuel López Marín**](https://github.com/SamKarsa)

