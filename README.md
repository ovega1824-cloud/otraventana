# Otra Ventana (Fabric 1.21.1)

Mod de cliente que muestra el texto **ESTAS EN OTRA VENTANA** en la esquina inferior derecha
cuando Minecraft pierde el foco (minimizas, haces Alt+Tab o cambias de aplicación).
El texto desaparece solo cuando regresas al juego.

## Compilar

Requiere JDK 21 y Gradle (o el wrapper de Gradle).

```
gradle build
```

El archivo `.jar` queda en `build/libs/otraventana-1.0.0.jar`.

## Instalar

1. Instala Fabric Loader para 1.21.1.
2. Copia `Fabric API` y `otraventana-1.0.0.jar` a la carpeta `mods`.
