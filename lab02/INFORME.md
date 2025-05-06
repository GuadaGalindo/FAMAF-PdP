---
title: Laboratorio de Programación Orientada a Objetos
author: Dianela Fernandez
        Felipe Jimenez
        Guadalupe Liz Galindo
---

El enunciado del laboratorio se encuentra en [este link](https://docs.google.com/document/d/1wLhuEOjhdLwgZ4rlW0AftgKD4QIPPx37Dzs--P1gIU4/edit#heading=h.xe9t6iq9fo58).

# 1. Tareas
Pueden usar esta checklist para indicar el avance.

## Verificación de que pueden hacer las cosas.
- [x] Java 17 instalado. Deben poder compilar con `make` y correr con `make run` para obtener el mensaje de ayuda del programa.

## 1.1. Interfaz de usuario
- [x] Estructurar opciones
- [x] Construir el objeto de clase `Config`

## 1.2. FeedParser
- [x] `class Article`
    - [x] Atributos
    - [x] Constructor
    - [x] Método `print`
    - [x] _Accessors_
- [x] `parseXML`

## 1.3. Entidades nombradas
- [x] Pensar estructura y validarla con el docente
- [x] Implementarla
- [x] Extracción
    - [x] Implementación de heurísticas
- [x] Clasificación
    - [x] Por tópicos
    - [x] Por categorías
- Estadísticas
    - [x] Por tópicos
    - [x] Por categorías
    - [x] Impresión de estadísticas

## 1.4 Limpieza de código
- [x] Pasar un formateador de código
- [x] Revisar TODOs

# 2. Experiencia
Todo el laboratorio fue realizado de manera colaborativa con la extensión live share de VS. Algunas implementaciones fueron realizadas con la ayuda de Copilot

# 3. Preguntas
1. ### *Explicar brevemente la estructura de datos elegida para las entidades nombradas.*

1) Para implementar la clase NamedEntity, lo que hicimos fue crear dos tipos enumerados: `Category` y `Topics`, basándonos en las categorías y tópicos que aparecían en el diccionario proporcionado. Luego, construimos la clase `NamedEntity`, donde las instancias de esta se crean con un nombre, una categoría y una lista de tópicos.

A partir de la clase NamedEntity, extendimos `una clase hija para cada categoría`, permitiendo que cada una tuviera los métodos de la clase madre, además de variables (características) y métodos propios.

2. ### *Explicar brevemente cómo se implementaron las heurísticas de extracción.*  

2) Implementamos 2 heurísticas basandonos en la estructura proporcionada por la cátedra, estas son `LocsandOrgsHeuristic` y `NameandTitlesHeuristic`:

  **`LocsandOrgsHeuristic` es una heurística que extrae posibles localizaciones y organizaciones, para esto tiene su función principal (extractCandidates) donde se definen 4 expresiones regulares, la primera toma nombres completos compuestos por dos o mas palabras, la segunda busca entidades con sufijos comunes (Inc, Ldt, LCC, Corp y S.A), la tercera toma localizaciones utilizando proposiciones (de, en, por), la cuarta busca entidades que son compatibles con acrónimos mixtos. Todas las expresiones regulares ignoran los artículos (El, La, Los, Las)

  
  **`NamesandTitles` es una heurística que extrae posibles Nombres y títulos, para esto tiene su función principal (extractCandidates) donde se definen 2 expresiones regulares la primera busca nombres completos compuestos por 2 o más palabras, la segunda busca entidades con títulos honoríficos. Ambas expresiones regulares ignoran los artículos (El, La, Los, Las)

# 4. Extras
Completar si hacen algo.