# 🚴 ProyectoCiclistas — Red Social para Ciclistas

> **FH1037** · Proyecto universitario desarrollado en Java (JavaSE-17)

---

## 📋 Descripción

**ProyectoCiclistas** es una red social orientada a la comunidad ciclista. Permite a los usuarios registrarse, conectar con otros ciclistas y compartir rutas, fomentando la interacción y el intercambio de experiencias en el mundo del ciclismo.

---

## ✨ Funcionalidades

- **Gestión de usuarios** — Registro y administración de perfiles de ciclistas.
- **Red de amistades** — Los usuarios pueden conectarse entre sí formando una red de amigos.
- **Registro de rutas** — Almacenamiento y consulta de rutas ciclísticas.
- **Persistencia en CSV** — Los datos se guardan y cargan desde archivos `.csv` (`usuarios.csv`, `amistades.csv`, `rutas.csv`).

---

## 🗂️ Estructura del proyecto
---

## 🛠️ Tecnologías

| Tecnología | Detalle |
|---|---|
| Lenguaje | Java |
| Versión JDK | JavaSE-17 |
| IDE | Eclipse IDE |
| Persistencia | Archivos CSV |

---

## 🚀 Cómo ejecutar

### Requisitos previos

- Java 17 o superior instalado ([Descargar JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))
- Eclipse IDE (opcional, para desarrollo)

### Desde Eclipse

1. Clona el repositorio:
```bash
   git clone https://github.com/tu-usuario/ProyectoCiclistas.git
```
2. Abre Eclipse → `File` → `Import` → `Existing Projects into Workspace`.
3. Selecciona la carpeta del proyecto y haz clic en `Finish`.
4. Ejecuta la clase principal desde el explorador de paquetes con clic derecho → `Run As` → `Java Application`.

### Desde línea de comandos

```bash
# Compilar
javac -d bin src/*.java

# Ejecutar (reemplaza 'Main' con el nombre de tu clase principal)
java -cp bin Main
```

---

## 📄 Formato de los archivos CSV

### `usuarios.csv`
### `amistades.csv`
### `rutas.csv`

> Los archivos se generan automáticamente si no existen al iniciar la aplicación.

---

## 👤 Autores
Andrés Copete, Jerónimo Cortés


---

## 📝 Licencia

Este proyecto es de uso académico. 
