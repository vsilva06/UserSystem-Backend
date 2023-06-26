# Los guindos API de Usuarios

Archivo de pruebas con postman: [UserSystem_Backend API.postman_collection.json](UserSystem_Backend%20API.postman_collection.json)

- [X] Para iniciar sesión se requere del email y la contraseña
- [X] Para registrarse se requiere del email, nombre, apellido, contraseña y confirmación de contraseña
- [X] El rol se establece automaticamente al registrase, como ***USER***
- Cada usuario puede tener un rol de ***USER***, ***MODERATOR*** o ***ADMIN***, los cuales poseen diferentes permisos
  -     READ_PRIVILEGE WRITE_PRIVILEGE DELETE_PRIVILEGE UPDATE_PRIVILEGE


- Usuarios precargados
  - ***ADMIN***
    - UserName: v.silva06@ufromail.cl
    - Password: admin
  - ***Moderator***
    - UserName: moderator@moderator.com
    - Password: moderator
  - ***User***
    - UserName: user@user.com
    - Password: user