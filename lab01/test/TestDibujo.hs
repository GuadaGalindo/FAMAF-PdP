import Test.HUnit
import Dibujo
import System.Exit (exitFailure, exitSuccess)

tests :: Test
tests = TestList 
    [
        -- Test de la función figura
        TestLabel "Prueba figura" $ TestCase $ 
            assertEqual "Crea una figura" (figura 5) (figura 5),

        -- Test de la función rotar
        TestLabel "Prueba rotar" $ TestCase $ 
            assertEqual "Rota una figura" (rotar (figura 5)) (rotar (figura 5)),

        -- Test de la función espejar
        TestLabel "Prueba espejar" $ TestCase $ 
            assertEqual "Espeja una figura" (espejar (figura 5)) (espejar (figura 5)),

        -- Test de la función rot45
        TestLabel "Prueba rot45" $ TestCase $ 
            assertEqual "Rota 45 grados una figura" (rot45 (figura 5)) (rot45 (figura 5)),

        -- Test de la función apilar
        TestLabel "Prueba apilar" $ TestCase $ 
            assertEqual "Apila dos figuras" (apilar 1 1 (figura 5) (figura 6)) (apilar 1 1 (figura 5) (figura 6)),

        -- Test de la función juntar
        TestLabel "Prueba juntar" $ TestCase $ 
            assertEqual "Junta dos figuras" (juntar 1 1 (figura 5) (figura 6)) (juntar 1 1 (figura 5) (figura 6)),

        -- Test de la función encimar
        TestLabel "Prueba encimar" $ TestCase $ 
            assertEqual "Encima dos figuras" (encimar (figura 5) (figura 6)) (encimar (figura 5) (figura 6)),

        -- Test de la función r180
        TestLabel "Prueba r180" $ TestCase $ 
            assertEqual "Rota 180 grados una figura" (r180 (figura 5)) (r180 (figura 5)),

        -- Test de la función r270
        TestLabel "Prueba r270" $ TestCase $ 
            assertEqual "Rota 270 grados una figura" (r270 (figura 5)) (r270 (figura 5)),

        -- Test de la función .-.
        TestLabel "Prueba .-." $ TestCase $ 
            assertEqual "Pone una figura sobre la otra" ((figura 5) .-. (figura 6)) (apilar 0.5 0.5 (figura 5) (figura 6)),

        -- Test de la función ///
        TestLabel "Prueba ///" $ TestCase $ 
            assertEqual "Pone una figura al lado de la otra" ((figura 5) /// (figura 6)) (juntar 0.5 0.5 (figura 5) (figura 6)),

        -- Test de la función ^^^
        TestLabel "Prueba ^^^" $ TestCase $ 
            assertEqual "Superpone una figura con otra" ((figura 5) ^^^ (figura 6)) (encimar (figura 5) (figura 6)),

        -- Test de la función cuarteto
        TestLabel "Prueba cuarteto" $ TestCase $ 
            assertEqual "Cuatro figuras en cuadrantes" (cuarteto (figura 5) (figura 6) (figura 7) (figura 8)) 
            (cuarteto (figura 5) (figura 6) (figura 7) (figura 8)),

        -- Test de la función encimar4
        TestLabel "Prueba encimar4" $ TestCase $ 
            assertEqual "Figura repetida con las cuatro rotaciones" 
            (encimar4 (figura 5)) (encimar4 (figura 5)),

        -- Test de la función ciclar
        TestLabel "Prueba ciclar" $ TestCase $ 
            assertEqual "Cuadrado con figura rotada i * 90" (ciclar (figura 5)) (ciclar (figura 5)),

        -- Test de la función foldDib
        TestLabel "Prueba foldDib con figura" $ TestCase $ 
            assertEqual "foldDib con figura" (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (figura 5))
            (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (figura 5)),

        TestLabel "Prueba foldDib con rotar" $ TestCase $ 
            assertEqual "foldDib con rotar" (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (rotar (figura 5)))
            (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (rotar (figura 5))),

        TestLabel "Prueba foldDib con espejar" $ TestCase $ 
            assertEqual "foldDib con espejar" (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (espejar (figura 5)))
            (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (espejar (figura 5))),

        TestLabel "Prueba foldDib con rot45" $ TestCase $ 
            assertEqual "foldDib con rot45" (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (rot45 (figura 5)))
            (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (rot45 (figura 5))),

        TestLabel "Prueba foldDib con apilar" $ TestCase $ 
            assertEqual "foldDib con apilar" (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (apilar 1 1 (figura 5) (figura 6)))
            (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (apilar 1 1 (figura 5) (figura 6))),

        TestLabel "Prueba foldDib con juntar" $ TestCase $ 
            assertEqual "foldDib con juntar" (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (juntar 1 1 (figura 5) (figura 6)))
            (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (juntar 1 1 (figura 5) (figura 6))),

        TestLabel "Prueba foldDib con encimar" $ TestCase $ 
            assertEqual "foldDib con encimar" (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (encimar (figura 5) (figura 6)))
            (foldDib id id id id (\_ _ a b -> a+b) (\_ _ a b -> a+b) (+) (encimar (figura 5) (figura 6)))
    ]

main :: IO ()
main = do
    counts <- runTestTT tests
    if (errors counts + failures counts > 0)
        then exitFailure
        else exitSuccess
