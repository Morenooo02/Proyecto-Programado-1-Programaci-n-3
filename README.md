# Sistema de Reserva de Recursos

Proyecto 1 - EIF206 Programacion 3.

## Como abrirlo en IntelliJ
1. File - Open y seleccione esta carpeta (la que tiene `pom.xml`).
2. Confirme que se abre como proyecto Maven.
3. Ejecute la clase `cr.ac.una.reservas.Application`.

## Como editar las pantallas (GUI Designer)
IntelliJ exige el `.form` al lado de la clase ligada. Por eso `LoginController.form` vive junto a `LoginController.java` en `presentation/controller/`. En `presentation/view/` queda `VistaUtil`.

1. Cierre pestañas viejas de `.form` (o File - Invalidate Caches).
2. Abra por ejemplo `LoginController.form` (doble clic).
3. Use el diseñador visual para mover botones, cambiar textos y tamaños.
4. No borre ni renombre los componentes (`txtId`, `btnIngresar`, etc.).
5. La logica queda en el mismo `*Controller`.
6. En Settings - Editor - GUI Designer, use **Generate GUI into: Java source code**. Si queda en binary class files, IntelliJ choca con el `$$$setupUI$$$` del codigo.

Los formularios usan el layout nativo de IntelliJ: **GridLayoutManager**.

## Usuarios de prueba
- Administrador: `admin` / `admin`
- Funcionario: `100` / `100`

Los datos se guardan en `data/datos.xml`.
