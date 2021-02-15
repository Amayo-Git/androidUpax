# androidUpax

    la soluion del problemas:
    en la lista principal del primer modulo se encarga de realizar la consulta del webservis  mientras que al mismo tiempose ejecuta en segundo plano la consulta local 
    para posterior realizar compracion de datos, en caso que la lista local este vacia, los datos del webservis se insertan ala base de datos local permitiendo al usuario poder realizar operaciones sin necesidad de conexion a internet, en caso que un elemento fue editado o creado  se indentifica por el estado de la linea, por ejemplo los estados edit o add fueron modificados por el usuario son datos que se envian al servidor, esta opcion se ejecuta"realiza una consulta en la base local " en la vista principal del modulo. Para funcionalidad de la de galeria,  se crea una tabla Firebase, se hace el envio desde el fragmento add en donde se consumira el servico, y para la ubicacion se crea un servicio que se ejecutar cada 30 minutos,'cuando se destruya la aplicacion el servicio seguira activo' y en un fragmento se consumira el servicio de google maps obteniendo los datos de firebase.      
 

   Estructura MVC
   

   AndroidManifest 
   |           |--permisos de Ubicacion, camara, internet de escritura 
   |           |----- aqui se agregaria el servicio en segundo plano "la ubicación del usuario cada 30 minutos. "<service
   |         											                                                                                  android:name=".google.iu.SyncService">
   |     										                                                                                     </service>"
   | 
   SplashActivity
   | 
   MainActivity 
   |             actividad principal donde contiene NavigationView
   |
   | ---------MainFragment
   |                 Muestra las opciones que se eligio dentro del MainFragment  NavigationView
   |
   |--------movies---Modulo de la opcion clientes 
   |                   |
   |                   |---- modelo
   |                   |              |
   |                   |              |-- Movie
   |                   |              |-- MovieResponse
   |                   |
   |                   |---addedit
   |                   |             |  
   |                   |             |--AddEditActivity 
   |                   |             |         |
   |                   |             |         |--AddEditFragment 'los fragmentos los utilice como controlador'
   |                   |             |                            'en esta parte se puede editar la linea o crea una,se puede agregar image o foto y subirla a la nube'
   |                   |                                           'cuando se finaliza una operacion manda el estado Activity.RESULT_CANCELED o RESULT_OK '
   |                   |---detail
   |                   |             |--DetailActivity
   |                   |             |          |
   |                   |             |          |----DetailFragment 'Muestra la informacion del item seleccionado, en caso de editar preciona la opcion de lapiz o        |                   |             |          |                     botecito para borra'
   |                   |             |          |              'cuando se finaliza una operacion manda el estado Activity.RESULT_CANCELED o RESULT_OK  ' 
   |                   |             
   |                   |---adapter
   |                   |          |
   |                   |          |---movieAdapter 'encargado de mostrar lo elemento en la vista principal asi como tambien mostra la actividad DetailActivity
   |                   |                       
   |                   |---MovieFragment 'controlador principla, consume el webservis, comparacion de datos, recibe los estados que finaliza de addedit y detail '
   |
   | 
   |-----------Google- aqui se agregaria un fragmento para mostra los datos de ubicacion en un mapa  
   
   
