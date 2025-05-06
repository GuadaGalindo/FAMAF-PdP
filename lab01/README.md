---
title: Laboratorio de Funcional
author: Jimenez Felipe
        Galindo Guadalupe
        Fernandez Dianela
        
La consigna del laboratorio está en https://tinyurl.com/funcional-2024-famaf

# 1. Tareas
Pueden usar esta checklist para indicar el avance.

## Verificación de que pueden hacer las cosas.
- [*] Haskell instalado y testeos provistos funcionando. (En Install.md están las instrucciones para instalar.)

## 1.1. Lenguaje
- [*] Módulo `Dibujo.hs` con el tipo `Dibujo` y combinadores. Puntos 1 a 3 de la consigna.
- [*] Definición de funciones (esquemas) para la manipulación de dibujos.
- [*] Módulo `Pred.hs`. Punto extra si definen predicados para transformaciones innecesarias (por ejemplo, espejar dos veces es la identidad).

## 1.2. Interpretación geométrica
- [*] Módulo `Interp.hs`.

## 1.3. Expresión artística (Utilizar el lenguaje)
- [*] El dibujo de `Dibujos/Feo.hs` se ve lindo.
- [*] Módulo `Dibujos/Grilla.hs`.
- [*] Módulo `Dibujos/Escher.hs`.
- [*] Listado de dibujos en `Main.hs`.

## 1.4 Tests
- [*] Tests para `Dibujo.hs`.
- [*] Tests para `Pred.hs`.

# 2. Experiencia
°°Todo el laboratorio fue realizado de manera colaborativa con la extensión live share de VS
°°Probamos nuestro achivo Escher.hs con las distintas figuras también utilizadas en Feo.hs, en el siguiente link guardamos imagenes de como se ve cada caso (ctrl+click para acceder)
https://drive.google.com/drive/folders/1GIw8IrA5NazfnhZN7o4Dm-4mKQHTOr8j?usp=sharing

# 3. Preguntas
Al responder tranformar cada pregunta en una subsección para que sea más fácil de leer.

- [*] 1. ¿Por qué están separadas las funcionalidades en los módulos indicados? Explicar detalladamente la responsabilidad de cada módulo.
- [*] 2. ¿Por qué las figuras básicas no están incluidas en la definición del lenguaje, y en vez de eso, es un parámetro del tipo?
- [*] 3. ¿Qué ventaja tiene utilizar una función de `fold` sobre hacer pattern-matching directo?
- [ ] 4. ¿Cuál es la diferencia entre los predicados definidos en Pred.hs y los tests?

#--------------------------------------Respuestas-----------------------------------# 
(1) ¿Por qué están separadas las funcionalidades en los módulos indicados? Explicar detalladamente la responsabilidad de cada módulo:

    Están separadas las funcionalidades en los módulos indicados porque nos permite una mejor legibilidad, dividir el trabajo en partes más sencillas y testear cada módulo de manera independiente, lo que hace más simple resolver problemas.

        Dibujos.hs: en este módulo definimos la sintaxis de nuestro lenguaje, donde proporcionamos el tipo principal para realizar los dibujos, las funciones básicas para poder construirlos y utilizarlos.

        FloatingPic.hs: en este módulo se define un tipo de datos (FloatingPic) que sirve para representar ciertas figuras básicas para facilitar su procesamiento en Gloss. Gracias al tipo FloatingPic y Output, podemos transformar los dibujos en imágenes flotantes que pueden ser manipuladas y procesadas por la librería Gloss.

        Interp.hs: este módulo es el encargado de interpretar (no dibujar) los dibujos realizados en Dibujo.hs como FloatingPics para luego, con Gloss, realizar gráficos. Posee funciones que sirven para manipular Pictures (rotar, r180, espejar, etc.). Y también es el encargado de mostrar en una ventana el dibujo utilizando la función InWindow.

        Pred.hs: este módulo recibe dibujos sin interpretar, y evalúa sobre alguna propiedad a los dibujos básicos que lo constituyen.

        Dibujos/Grilla, Dibujos/Feo: Estos módulos utilizan la implementación realizada, ya que usan las herramientas proporcionadas por el resto de módulos para mostrar un dibujo específico en pantalla.

(2) ¿Por qué las figuras básicas no están incluidas en la definición del lenguaje, y en vez de eso, es un parámetro del tipo?:

    Hacer que las figuras básicas sean un parámetro del tipo Dibujo, en vez de estar incluidas en la definición del lenguaje, permite extender el lenguaje a nuevas figuras básicas en un futuro sin preocupaciones. Esto es posible ya que se pueden usar las mismas funciones (apilar, juntar, encimar, rotar, etc.) para cualquier figura básica.

    Para poder hacer esto utilizamos el tipo de dato FloatingPic, el cual nos permite manipular las Pictures sin que nos interese lo que se desea dibujar.

(3) ¿Qué ventaja tiene utilizar una función de `fold` sobre hacer pattern-matching directo?:

    Utilizar una función de fold nos facilita mantener y modificar el código. Esto se debe a que la misma nos permite trabajar recursivamente sobre todos los casos de la estructura Dibujo, y nos ahorra tener que modificar múltiples funciones que hacen pattern-matching directo.

(4) ¿Cuál es la diferencia entre los predicados definidos en Pred.hs y los tests?:

    En resumen, los predicados en Pred.hs son funciones genéricas que operan sobre elementos de tipo Dibujo a, mientras que los predicados en los tests son funciones específicas utilizadas para verificar el comportamiento de las funciones en Pred.hs en situaciones controladas durante las pruebas.
    La diferencia principal entre los predicados definidos en Pred.hs y los tests radica en cómo se utilizan y en qué contexto se aplican.

    El archivo Pred.hs define una serie de funciones relacionadas con "predicados", que son funciones que toman un valor de entrada y devuelven un valor booleano. Estas funciones operan en estructuras de datos tipo "Dibujo", que parecen representar composiciones visuales o diagramas. Aquí está el resumen de las funciones definidas en Pred.hs

    Los tests para Pred.hs son un conjunto de pruebas unitarias que verifican el funcionamiento correcto de las funciones definidas en Pred.hs. Estas pruebas están diseñadas para asegurar que las funciones se comportan como se espera, usando ejemplos concretos. Incluyen

    Los tests para Dibujo.hs son un conjunto de pruebas que aseguran que estas operaciones funcionan correctamente. Estos tests incluyen

    En Pred.hs los predicados devuelven valores booleanos

Predicados en Pred.hs:
Están definidos como funciones del tipo Pred a, es decir, funciones que toman un valor de tipo a y devuelven un valor booleano (True o False).
Estos predicados se utilizan para operaciones sobre estructuras de datos del tipo Dibujo a, donde a puede ser cualquier tipo.
Cada función predicado (cambiar, anyDib, allDib, andP, orP) opera sobre elementos de tipo Dibujo a, aplicando la lógica definida en el predicado para transformar, filtrar o combinar dichos elementos.
Predicados en los tests:
En los tests, se definen predicados específicos para probar las funciones y verificar que su comportamiento sea el esperado.
Cada test define predicados simples que son utilizados como entrada para las funciones de Pred.hs en el contexto de las pruebas.
Estos predicados de prueba se usan para verificar que las funciones en Pred.hs estén funcionando correctamente según las condiciones establecidas en los tests.

# 4. Extras
-
