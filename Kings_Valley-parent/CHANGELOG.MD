"v 1.0.0 r 2025-07-12":



\*Primera version



"v 1.0.1 r 2025-10-24":

* Se agrego documentacion.
* Se mejoro claridad en metodos y borro codigo obsoleto.
* Se mejoro el comportamiento de las momias, agregando decisiones probabilisticas.
* Se corrigio error de disparo de espada saliendo de la puerta giratoria (chocaba con la puerta que estaba justo a nuestra espalda)
* Se corrigio error de recolecion de pico y espada coincidente (si ambos objetos estaban en el mismo lugar, se tomaba uno y el otro desaparecia)



"v 1.0.2 r 2025-10-25":

* Se corrigio error de colision de la momia, Si al saltar colisionaba con una giratoria no la detectaba y la atravesaba.
* Se corrigio error al lanzar la espada verticalemente. Si el tile donde supuestamente se clavaba la espada era un muro, la espada quedaba atrapada dentro de el. Ahora en ese caso no se permite lanzar la espada.
* Se agrego un archivo de configuracion para las reglas del juego.



"v 1.1.0 r 2026-01-02"



* Se agrego la versión extendida de 60 niveles en cuatro episodios.
* Se mejoro el movimiento de las momias.
* Se agregaron parametros en los archivos de configuracion para faciitar el debug del juego.
* Se corrigio un bug en la pantalla de inicio.
* Se agregaron las instrucciones del juego.



"v 1.1.1 r 2026-01-05"

* Se corrigio la cantidad inicial de vidas (5)
* Se corrigio un bug que no disparaba un sonido cuando la espada golpeaba una giratoria.



"v 1.1.2 r 2026-01-21"

* Se corrigio un bug crasheaba el juego al llegar al nivel de bonus por no tener momias (null pointer exception)

"v 1.1.3 r 2026-02-19"

* Se corrigio el codigo de colision de las momias para intentar corregir un bug por el cual a veces la espada atravesaba a las momias sin matarlas.


