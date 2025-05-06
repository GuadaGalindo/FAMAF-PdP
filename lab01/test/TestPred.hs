module Main where

import Test.HUnit
import Pred
import Dibujo

-- Test suite for Pred.hs
main :: IO Counts
main = runTestTT $ TestList
  [ TestLabel "Test de cambiar" testCambiar
  , TestLabel "Test de anyDib" testAnyDib
  , TestLabel "Test de allDib" testAllDib
  , TestLabel "Test de andP" testAndP
  , TestLabel "Test de orP" testOrP
  ]

-- Test para cambiar
testCambiar :: Test
testCambiar = TestCase $ do
  let p :: String -> Bool
      p x = length x < 10
      fun :: String -> Dibujo String
      fun _ = figura "menos_de_10"
      dib = apilar 1 1 (figura "corto") (figura "muy_largo")
      r = apilar 1 1 (figura "menos_de_10") (figura "menos_de_10")
  assertEqual "Prueba cambiar para dibujos con básicos menores de 10" r (cambiar p fun dib)

-- Test para anyDib
testAnyDib :: Test
testAnyDib = TestCase $ do
  let p :: String -> Bool
      p x = length x < 5
      dib = apilar 1 1 (figura "corto") (figura "muy_largo")
  assertEqual "Prueba anyDib para algún básico con longitud menor a 5" (False) (anyDib p dib)

-- Test para allDib
testAllDib :: Test
testAllDib = TestCase $ do
  let p :: String -> Bool
      p x = length x < 5
      dib = apilar 1 1 (figura "corto") (figura "muy_largo")
  assertEqual "Prueba allDib para todos los básicos con longitud menor a 5" (False) (allDib p dib)

-- Test para andP
testAndP :: Test
testAndP = TestCase $ do
  let p1 :: String -> Bool
      p1 x = length x < 10
      p2 :: String -> Bool
      p2 x = 1 < length x
      p = andP p1 p2
      ss = "corto"
  assertEqual "Prueba andP para dos predicados que se cumplen" (True) (p ss)

-- Test para orP
testOrP :: Test
testOrP = TestCase $ do
  let p1 :: String -> Bool
      p1 x = length x < 3
      p2 :: String -> Bool
      p2 x = 1 < length x
      p = orP p1 p2
      ss = "corto"
  assertEqual "Prueba orP para dos predicados donde uno se cumple" (True) (p ss)
