# Proyecto final ciclo DAW Cristian Varela Casas

#### Instrucciones de instalación para entorno de desarrollo

0. Se entrega tanto la carpeta */src* para probar en entorno de desarrollo, así como el fichero *.war* para probar con despliege en servidor

1. Crea una base de datos con el nombre de **partes** en My SQL.

2. Establece tus credenciales de acceso a la base de datos, así como el string de conexion en los archivos **ClienteDao.java** y **ProductoDao.java** dentro del paquete _controllers_:


    user: your_data_base_user
    password: you_access_password_to_data_base
    url: connection_string_to_data_base

3. Ejecuta los ficheros adjuntos **partes_clientes.sql** y **partes_productos.sql** para crear e inicializar las tablas SQL.

4. Si estás en entorno de desarrollo, en los archivos **PagarServlet.java** y **DescripcionProductoServlet.java** del paquete servlets, establece el valor de la constante **PATH_TO_IMGS_DIR** con la ruta absoluta al directorio _/img_, que se encuentra dentro de la carpeta _/src/main/webapp_, y el valor de la constante **FILE_PATH** con la ruta absoluta al directorio donde la aplicación guardará los .pdfs (debes asegurarte previamente de que esta carpeta exista para que no dé error):


    private final String PATH_TO_IMGS_DIR = "C:\\example\\My_proyects_dir\\my_app_proyect\\src\\main\\webapp\\imgs";

    private final String FILE_PATH = "C:\\My_computer\\pdfs\\documento";

5. En el caso de que no estés en entorno de desarrollo, sino que hayas desplegado el fichero **.war** en el servidor de _Tomcat_, previamente a ello debes comentar la variable **documentFilePath** que estará descomentada, y descomentar la variable con el mismo nombre **documentFilePath** que estará comentada (líneas 76 y 77 del fichero **PagarServlet.java** y líneas 47 y 48 del fichero **DescripcionProductoServlet.java**).

6. Asegurate configurar el **Build Path** del proyecto con las dependencias necesarias (ficheros *.jar*). Estos se sitúan dentro de la carpeta */webapp/WEB-INF/lib* del */src*

7. Enjoy it !
