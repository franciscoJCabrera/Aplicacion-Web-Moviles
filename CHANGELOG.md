# Changelog

Más información sobre cómo rellenar el fichero en https://keepachangelog.com/es-ES/1.0.0/

## [0.5] Iteracion 5 Entrega 10-5-2024

### *Added*
- Poder buscar las subastas publicadas de un usuario (Alejandro Garrido Gómez)
- La ventana de subastas muestra la información del usuario que ha publicado la subasta (Alejandro Garrido Gómez) 
- Servicio REST para la entidad de movil (Alejandro Garrido Gómez)
- Nueva vista SPA que usa dicho servicio para la vista y el alta de movil (Alejandro Garrido Gómez)
- Servicio REST para la entidad Usuario (Francisco Jose Cabrera Bermejo)
- Creación de una vista SPA "api_usuario" controlada por JavaScript para dar de alta usuarios y mostrarlos en pantalla (Francisco Jose Cabrera Bermejo)
- Añadida la verificación de una nueva subasta en el lado del cliente mediante JS (Alejandro Garrido Gómez)

### *Changed*
- Ahora el formulario de crear/editar subasta muestra los errores en los campos y no recarga la página si no son correctos (Alejandro Garrido Gómez)
- Plantilla xhtml para mostrar los enlaces a los nuevos AJAX cuando la sesión se encuentra iniciada.

### *Fixed*
- Ahora la página de búsqueda de subastas si actualiza la lista de marcas si estas cambian durante la sesión del usuario (Alejandro Garrido Gómez)

## [0.4] Iteración 4 Entrega 26-4-2024

### *Added*
- Implementado metodo para obtener todos los moviles de un usuario (Francisco Jose Cabrera Bermejo)
- Tabla completada para ver datos de los moviles de un usuario, concretamente ID, Marca y Modelo y precio de compra (Francisco Jose Cabrera Bermejo)
- Añadida la variable de ContraseñaCifrada para el usuario (Francisco Jose Cabrera Bermejo)
- Creada la clase AuthService para realizar el cifrado de contraseña (Francisco Jose Cabrera Bermejo)
- Añadido un atributo a moviles para determinar si es una subasta o un movil comprado (Alejandro Garrido Gómez)
- Cuando se compra un movil, este se registra al correo del usuario identificado y se quita de la pool de subastas (Alejandro Garrido Gómez)
- Daos para gestionar las imagenes de una subasta, hay dos implementaciones, la local te guarda las imagenes en una carpeta en el home, y la database guarda las imagenes en la base de datos (Alejandro Garrido Gómez)

### *Changed*
- Modificacion de la tabla donde se muestran los datos de los moviles de un usuario (Francisco Jose Cabrera Bermejo)
- Modificacion del campo Contraseña de la base de datos, ahora mostramos la Contraseña cifrada en lugar de la de texto plano (Francisco Jose Cabrera Bermejo)
- Cambios necesarios para realizar la comprobacion ahora con la contraseña cifrada en lugar de con la contraseña de texto plano (Francisco Jose Cabrera Bermejo)
- Variable contraseña modificada como 'transient', hecho para que la base de datos H2 no pille dicho campo y pille unicamente la contraseña cifrada (Francisco Jose Cabrera Bermejo)
- Los resultados de la busqueda no te muestran tus subastas, solo las de los otros usuarios (Alejandro Garrido Gómez)
- Ahora la página de subastas no te deja entrar a tu propia subasta, aunque lo fuerces (Alejandro Garrido Gómez)
- Datos por defecto para que se reflejen todos los nuevos cambios a las entidades (los dos)
- Cuando se crea una nueva subasta, esta se registra al usuario que lo ha creado (Alejandro Garrido Gómez)
- Modificado el DAO de moviles para que no te de las subastas del usuario identificado y que no sean subastas (Alejandro Garrido Gómez)


## [0.3] Iteración 3 Entrega 12-4-2024

### *Added*

- Implementación mediante JPA del DAO de movil para asegurar la persistencia de datos (Alejandro Garrido Gómez)
- Qualifiers para cambiar entre la implementación del DAO de movil en la bbdd y map (Alejandro Garrido Gómez)
- Interfaz de DAO de movil para facilitar el cambio entre distintas implementaciones (Alejandro Garrido Gómez)
- Configuración de la unidad de persistencia h2 (Alejandro Garrido Gómez)
- Inclusión de datos de prueba de distintas subastas (Alejandro Garrido Gómez)
- Posibilidad de subir una imagen de una subasta, aunque aun no he conseguido que se muestre, se guarda correctamente (Alejandro Garrido Gómez)
- La busqueda de subastas es completamente funcional (Alejandro Garrido Gómez)
- Añadida la dependencia para la autenticacion basica de usuarios (Francisco Jose Cabrera Bermejo)
- Añadida la Cookie para el inicio de sesion (Francisco Jose Cabrera Bermejo)
- Interfaz del UsuarioDAO creada (Francisco Jose Cabrera Bermejo)
- Creada la tabla donde se van a almacenar los datos de los usuarios (Francisco Jose Cabrera Bermejo)
- Implementacion de la autentificacion basica de un usuario en la clase AppConfig (Francisco Jose Cabrera Bermejo)
- Roles de usuarios que se van a utilizar escritos en web.xml (Francisco Jose Cabrera Bermejo)
- Vista de InicioSesion actualizada para tener en cuenta la autenticacion  mediante formulario estandar (Francisco Jose Cabrera Bermejo)
- Metodos implementados en la clase UsuarioJpaDAO (Francisco Jose Cabrera Bermejo)
- Metodos creados de login y logout para usuarios (Francisco Jose Cabrera Bermejo)
- Se ha añadido al fichero web.xml los roles de usuarios que se van a utilizar (Francisco Jose Cabrera Bermejo)
- Se ha añadido las reglas de control de acceso, limitando el acceso a ciertas vistas deseadas (Francisco Jose Cabrera Bermejo)
- Metodo para buscar un usuario por su identificador, correo electronico, el cual es unico (Francisco Jose Cabrera Bermejo)
- Botones añadidos a la vista de ver perfil, por si queremos borrar un usuario o cambiarle los datos (Francisco Jose Cabrera Bermejo)

### *Changed*

- Ordenación de paquetes de java para facilitar la distinción (Alejandro Garrido Gómez)
- Página de búsqueda de subastas para también poder buscar por el precio de la puja y del móvil (Alejandro Garrido Gómez)
- Forma en la que se manejan las imágenes para que las rutas se las distintas imágenes de una subasta se guarden en la bbdd (Alejandro Garrido Gómez)
- Controlador actualizado con su correspondiente inyeccion y llamada a metodos (Francisco Jose Cabrera Bermejo)
- AppConfig configurado para tener una autenticacion configurada (Francisco Jose Cabrera Bermejo)
- Modificacion en la clase Usuario para cambiar la variable Contraseña (Francisco Jose Cabrera Bermejo)
- Los datos de la vista ver perfil ahora se pueden modificar (Francisco Jose Cabrera Bermejo)


## [0.2] Iteración 2 Entrega 22-3-2024

### **Added**

- Listado de los paises para seleccionar (Francisco Jose Cabrera Bermejo)
- Listado de ciudades diferentes dependiendo del pais seleccionado (Francisco Jose Cabrera Bermejo)
- Metodos generales de UsuarioDAO implementados (Francisco Jose Cabrera Bermejo)
- Bean validation realizado para el registro de un nuevo usuario (Francisco Jose Cabrera Bermejo)
- Inserccion de datos mediante el controlador (Francisco Jose Cabrera Bermejo)
- Bean validation de movil (Alejandro Garrido Gómez)
- Funcionalidad para qué las subastas ahora tengan visitas (Alejandro Garrido Gómez)

### **Changed**
- Cambios realizados en la vista PaginaInicioSesion.xhtml para mejorar la interfaz (Francisco Jose Cabrera Bermejo)
- Ficheros html organizados en carpeta creada dentro de carpeta "Vistas" (Francisco Jose Cabrera Bermejo)
- Página de inicio sesion con pequeña modificacion para la muestra de fallos en pantalla (Francisco Jose Cabrera Bermejo)
- Condicion de bean validation para el correo electronica con la longitud minima modificada (Francisco Jose Cabrera Bermejo)
- Arreglo de muestra de fallos dobles (Francisco Jose Cabrera Bermejo)
- Página para ver el perfil con tabla de historial de moviles del usuario insertada (Francisco Jose Cabrera Bermejo)
- Vista de subastas de un usuario para que pueda activar y desactivas las columnas que quiera (Alejandro Garrido Gómez)
- Vista de busqueda ahora usa los atributos de movil, salvo en aquellos que pueden tener el valor por defecto de 0 (Alejandro Garrido Gómez)


## [0.2] Iteración 1 Entrega 9-3-2024

### **Added**

- Representación de un teléfono con su clase Java (Alejandro Garrido Gómez)
- DAO de móvil, con movies de ejemplo para probar la búsqueda, basado en el ejemplo de Balsas (Alejandro Garrido Gómez)
- Faces de búsqueda (Alejandro Garrido Gómez)
- Controlador de búsqueda (Alejandro Garrido Gómez)
- Faces de visualización de resultados de búsqueda (Alejandro Garrido Gómez)
- Faces para hacer operaciones CRUD a las subastas subidas por un usuario (Alejandro Garrido Gómez)
- Controlador para usar correctamente el componente de PrimeFaces para la tabla de operaciones CRUD (Alejandro Garrido Gómez)
- Layout general de la página web (Alejandro Garrido Gómez)
- Vista para realizar el registro de un usuario en formato JSF (Francisco Jose Cabrera Bermejo)
- Controlador de la página para registrarse (Francisco Jose Cabrera Bermejo)
- Página para ver el perfil en formato JSF (Francisco Jose Cabrera Bermejo)
- Controlador de la vista para el ver el perfil (Francisco Jose Cabrera Bermejo)
- Página de inicio de sesión en formato JSF creada (Francisco Jose Cabrera Bermejo)
- Controlador de la vista Iniciar Sesión (Francisco Jose Cabrera Bermejo)
- Usuario y UsuarioDAO creado y empezado (Francisco Jose Cabrera Bermejo)
- JSP para la página de subasta y compra con su correpondiente servlet (Alejandro Garrido Gómez)
- Cambios realizados para notificar la falta de datos en el formulario de Registro de Usuario (Francisco Jose Cabrera Bermejo)
- Fotos al directorio Imagenes (Francisco Jose Cabrera Bermejo)
- Fotos al directorio Imagenes (Francisco Jose Cabrera Bermejo)
- Pagina de registro de usuario creada (Francisco Jose Cabrera Bermejo)
- Pagina de registro realizada como un formulario (Francisco Jose Cabrera Bermejo)
- Pagina para ver el perfil empezada (Francisco Jose Cabrera Bermejo)
### **Changed**

- Varias de las rutas para colocarlas dentro de webapp (Alejandro Garrido Gómez y Francisco Jose Cabrera Bermejo)
- Pomp.xml para incluir varias de las nuevas dependencias (Alejandro Garrido Gómez y Francisco Jose Cabrera Bermejo)
- Readme.md para arreglar el problema de las imágenes no mostrándose
- Índice para mostrar las subastas más destacadas (por ahora solo muestra subastas fijas, esto se implementará en otra iteración) (Alejandro Garrido Gómez)
- Foto añadida al directorio Imagenes (Francisco Jose Cabrera Bermejo)
- Foto mostrada en la vista dedicada para ver nuestro perfil (Francisco Jose Cabrera Bermejo)
- Especificacion de ``README.md`` (Alejandro Garrido Gómez)
- Pagina principal de la página (Alejandro Garrido Gómez)
- Página de busqueda (Alejandro Garrido Gómez)
- Página para la muestra de resultados (Alejandro Garrido Gómez)
- Página de compra de un dispositivo (Alejandro Garrido Gómez)
- Pagina para iniciar sesion creada (Francisco Jose Cabrera Bermejo)
- Imagenes añadidas del proyecto individual (Francisco Jose Cabrera Bermejo)
- Estilo de nuestra aplicacion web insertado en la vista de Iniciar Sesion (Francisco Jose Cabrera Bermejo)
- Estilo de nuestra aplicacion web insertado en la vista de Registrarse (Francisco Jose Cabrera Bermejo)
- Pagina de Registrarse actualizada, solicita mas datos y pone ejemplos del formato a seguir (Francisco Jose Cabrera Bermejo)
- Diagrama .pump donde se resumen las entidades que interactúan en el proyecto (Alejandro Garrido Gómez)
- Mockup de la página de búsqueda (Alejandro Garrido Gómez)
- Mockup de la página de visualización de resultados (Alejandro Garrido Gómez)
- Mockup de la página de publicación de subasta (Alejandro Garrido Gómez)
- Mockup de la página de acceso a la subasta activa de un teléfono (Alejandro Garrido Gómez)
- Vista de publicación de una subasta con sus estilos (Alejandro Garrido Gómez)
- Imagenes insertadas en el README, de mockups que faltaban y del diagrama de entidad (Francisco Jose Cabrera Bermejo)
- Descripcion de que alumno se encarga de que entidad en README (Francisco Jose Cabrera Bermejo)
- Link para visitar nuestro perfil desde la pagina de bienvenida (Francisco Jose Cabrera Bermejo)
- Pagina para ver el perfil de usuario con la interfaz de nuestra aplicacion web (Francisco Jose Cabrera Bermejo)
- Pagina para ver perfil acabada (Francisco Jose Cabrera Bermejo)
- Mockups insertado de las vistas: IniciarSesion, Registrarse, VerPerfil (Francisco Jose Cabrera Bermejo)










