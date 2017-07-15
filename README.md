# devml-api

# Tecnología Utilizada
$ Spring Boot
$ JUnit
$ RedisCache
$ Maven

# Ingreso al sistema

$ URL: https://devml-api.herokuapp.com/

### Para ejecutar los 3 primeros puntos se debe ingresar a la siguiente URL:

###### URL: https://devml-api.herokuapp.com/ejercicios
Los calculos de las primeras tres consignas estan calculadas a 10 años del planeta tierra por lo tanto a 3650 dias. En el enunciado no se especificaba los años respecto a que planeta. Se tomó un solo decimal para que el calculo de la alineación de los planetas.

### Bonus:

###### URL: https://devml-api.herokuapp.com/clima?dia=[dia_a_calcular]
Para el ejercicio bonus se calcularon 720 días, ya que el sistema API-REST es del planeta VULCANO. El mismo gira 5°/día por lo tanto demora 72 días en recorrer 360°.
 

# Explicación

Para comenzar a realizar el problema, debemos determinar los puntos x e y en el plano cartesiano de los planetas para cada uno de los dias.

Realizamos la trasformación de coordenadas polares a coordenadas cartesianas:

$ http://www.disfrutalasmatematicas.com/graficos/coordenadas-polares-cartesianas.html
$ http://www2.caminos.upm.es/Departamentos/matematicas/Fdistancia/PIE/Calculo/repaso/funcionestrig.pdf

x' = r * cos(α)
y' = r * sen(α)

Luego de tener los puntos en el eje cartesiano, debemos verificar si los tres puntos estan alineados, y de ser asi si tambíen estan alineados con el sol.

Para saber si tres puntos estan alineados, los valores de los puntos deben ser proporcionales y su pendiente debe ser la misma.

$ http://geometria-analitica-y-algebra.blogspot.com.ar/2012/11/puntos-alineados-sobre-una-recta.html

Si no estan alineados debemos ver si el sol esta dentro del triangulo formado por los puntos.

$ http://www.dma.fi.upm.es/personal/mabellanas/tfcs/kirkpatrick/Aplicacion/algoritmos.htm#puntoInteriorDefinicion

En caso de que el sol esta dentro del triangulo, debemos ver el perimetro de ese triangulo para ver en que dia es el maximo.

El perímetro de un triángulo es igual a la suma de sus tres lados.
Para calcular el perimetro, debemos saber la distancia entre los puntos, asi podemos obtener los tres lados y sumarlos.

Distancia entre dos puntos.
$ http://www.sectormatematica.cl/contenidos/distancia.htm
$ http://www.profesorenlinea.cl/geometria/Distancia_entre_dos_puntos.html