---
title: Paralelismo con Spark
author: Dianela Fernandez
        Felipe Jimenez
        Guadalupe Liz Galindo
---

Enunciado del laboratorio[este link](https://docs.google.com/document/d/1N6V8fjBbDrCGEHfxVkHVa5kZuO8i9pnSlU5cM3VUAQM/edit#heading=h.xe9t6iq9fo58).

# 1. TAREAS:
**Checklist de las tareas realizadas.**
`1.1 Modificaciones y mejoras al lab previo.`
- [x] Modularización de código (App.java, Heuristics.java).
- [x] Implementar la BigData.
`1.2 Agregar Spark.`
- [x] Estructurar la extraccion de entidades nombradas mediante spark.
`1.3 Limpieza de código.`
- [x] Formatear el codigo.
- [x] Revisar TODOs los codigos. 



# 2. TRAYECTO DEL PROYECTO:
1. **El primer paso consistió en implementar algunas de las modularizaciones y mejoras sugeridas por el profesor durante la corrección del laboratorio 2:**

    * En `App.java`, se realizaron modificaciones significativas transfiriendo la lógica de creación de los artículos a la clase Article.java y la lógica de impresión (feed, help, namedEntities) a la clase Config.java. Esto se hizo para mejorar la claridad y simplicidad del código principal (main).

    * Además, se llevó a cabo la implementación para escribir los feeds en un archivo `BigData.txt` dentro del directorio data. La lógica para esto se encuentra en el archivo Article.java, donde se utilizaron las bibliotecas correspondientes.


2. **Después de esto, el segundo paso fue implementar la lógica para emplear Spark. Este punto fue el que más dificultades nos presentó y requirió bastante tiempo de investigación:**

    * La idea detrás del uso de `Apache Spark` fue poder realizar el procesamiento de las entidades nombradas, utilizando grandes cantidades de datos (Big Data), de manera distribuida. Esto se hizo con el objetivo de asegurar que el tiempo de respuesta fuera razonable para el usuario. 
    La implementación de esta lógica se encuentra en el archivo `Config.java`. En resumen, creamos una instancia de SparkSession, leímos el archivo de texto, creamos un RDD de líneas y las distribuimos entre los nodos del clúster, aplicamos la heurística correspondiente para extraer las entidades y luego recolectamos los resultados en una lista.


3. **Finalmente, el tercer paso consistió en realizar una limpieza final de los códigos:**

    * La `limpieza final` involucró eliminar código comentado, asegurarnos de que solo quedaran las modularizaciones necesarias y aplicar un formateador de código para Java. 



# 3. CONCLUSIONES:
- El framework Apache Spark, como se explicó anteriormente, nos permite distribuir la carga de tareas en diferentes máquinas (o localmente en este caso) para acelerar los tiempos de cómputo para el usuario. Para evaluar su eficiencia, decidimos generar un archivo de 77MB a partir de BigData y realizar `pruebas` de rendimiento variando la cantidad de workers. Aunque la diferencia no fue tan significativa, creemos que se debe a que el archivo no es lo suficientemente grande para notar cambios importantes. Intentamos ejecutar el laboratorio con el archivo de 1GB proporcionado por la cátedra, pero la memoria en nuestras máquinas virtuales hizo que no fuera posible
    
    **Con 2 workers:**
    - ![alt text](<2workers.png>)

    **Con 4 workers:**
    - ![alt text](<4workers.png>)



# 4. EXPERIENCIA:
- Todo el laboratorio fue realizado de manera colaborativa con la extensión Live Share de VS y utilizando Discord como canal de comunicación.



# 5. COMPILACIÓN:

~$ mvn clean package

~$ mvn install

~$ $SPARK_HOME/bin/spark-submit --class App --master local[n] target/App-0.1.jar -pf -ne 'NameAndTitles'


`Donde SPARK_HOME es la carpeta donde esta instalado Spark, 'NameAndTItles' es la heuristica en este caso (se puede modificar viendo las opciones con la FLAG -h) y n es la cantidad de workers con los que queremos trabajar.`