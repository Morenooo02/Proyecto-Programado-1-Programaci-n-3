# Sistema de Reserva de Recursos

Proyecto 1 - EIF206 Programacion 3.

## Como abrirlo en IntelliJ
1. File - Open y seleccione esta carpeta (la que tiene `pom.xml`).
2. Confirme que se abre como proyecto Maven.
3. Ejecute la clase `cr.ac.una.reservas.Application`.

## Como editar las vistas (GUI Designer)
Cada pantalla tiene un archivo `.form` al lado de su `.java` en `src/main/java/cr/ac/una/reservas/presentation/view/`.

1. Cierre las pestañas de `.form` abiertas y vuelva a abrirlas (o File - Invalidate Caches).
2. Abra por ejemplo `LoginView.form` (doble clic).
3. Use el diseñador visual para mover botones, cambiar textos y tamaños.
4. No borre ni renombre los componentes (`txtId`, `btnIngresar`, etc.), porque los controladores los usan.
5. La logica (guardar, buscar, reservas) se queda en las clases `*Controller`.

Los formularios usan el layout nativo de IntelliJ: **GridLayoutManager**.

## Usuarios de prueba
- Administrador: `admin` / `admin`
- Funcionario: `100` / `100`

Los datos se guardan en `data/datos.xml`.
