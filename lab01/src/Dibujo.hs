module Dibujo (Dibujo (..), figura, encimar, apilar, juntar, rot45, rotar, espejar,
              (^^^), (.-.) ,(///), r90, r180, r270, encimar4, cuarteto,
              ciclar, mapDib, change, foldDib,
    ) where

-- Nuestro lenguaje 
data Dibujo a = Figura a 
              | Encimar (Dibujo  a) (Dibujo a) 
              | Apilar Float Float (Dibujo a) (Dibujo a) 
              | Rot45 (Dibujo a) 
              | Rotar (Dibujo a)
              | Juntar Float Float (Dibujo a) (Dibujo a)   
              | Espejar (Dibujo a) deriving (Show, Eq)

-- Combinadores
-- Infixr indica que es un operador asociativo
infixr 6 ^^^

infixr 7 .-.

infixr 8 ///

-- Composición n veces una funcion con si misma. N >= 0, recursion
comp :: Int -> (a -> a) -> a -> a
comp 0 _ x = x
comp n f x | n < 0 = error "No se puede aplicar una función un numero negativo de veces"
           | otherwise = f(comp (n-1) f x)

-- Funciones constructoras (SINTAXIS)
figura :: a -> Dibujo a
figura = Figura

encimar :: Dibujo a -> Dibujo a -> Dibujo a
encimar = Encimar

apilar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
apilar = Apilar

juntar  :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
juntar = Juntar

rot45 :: Dibujo a -> Dibujo a
rot45 = Rot45

rotar :: Dibujo a -> Dibujo a
rotar = Rotar

espejar :: Dibujo a -> Dibujo a
espejar = Espejar

-- Superponen un dibujo con otro 
(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) = Encimar

-- Pone el primer dibujo arriba del segundo, tienen el mismo tamaño
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) = Apilar 0.5 0.5

-- Pone un dibujo al lado de otro, ocupan el mismo espacio
(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) = Juntar 0.5 0.5

-- Rotaciones:
-- Rota 1 vez 90
r90 :: Dibujo a -> Dibujo a
r90 a = Rotar a

-- Rota 2 veces 90
r180 :: Dibujo a -> Dibujo a
r180 a = comp 2 Rotar a

-- Rota 3 veces 90
r270 :: Dibujo a -> Dibujo a
r270 a = comp 3 Rotar a 

-- Una figura repetida con las cuatro rotaciones, superimpuestas.
encimar4 :: Dibujo a -> Dibujo a
encimar4 a = (^^^) ((^^^) a (rotar a)) ((^^^) (r180 a) (r270 a))

-- Cuatro figuras en un cuadrante.
cuarteto :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto a b c d = (.-.) ((///) a b) ((///) c d)

-- Un cuarteto donde se repite la imagen, rotada (¡No confundir con encimar4!)
ciclar :: Dibujo a -> Dibujo a
ciclar a = cuarteto a (r90 a) (r180 a) (r270 a)

-- Map para nuestro lenguaje
-- mapDib cambia todas las figuras de acuerdo a la función proporcionada
mapDib :: (a -> b) -> Dibujo a -> Dibujo b
mapDib f (Figura a) = Figura (f a)          
mapDib f (Encimar a b) = Encimar (mapDib f a) (mapDib f b)
mapDib f (Apilar x y a b) = Apilar x y (mapDib f a) (mapDib f b)
mapDib f (Rot45 a) = Rot45 (mapDib f a)
mapDib f (Rotar a) = Rotar (mapDib f a)
mapDib f (Juntar x y a b) = Juntar x y (mapDib f a) (mapDib f b)
mapDib f (Espejar a)= Espejar (mapDib f a) 

-- Change cambia todas las figuras básicas de acuerdo a la función proporcionada
change :: (a -> Dibujo b) -> Dibujo a -> Dibujo b
change f (Figura a) = f a          
change f (Encimar a b) = Encimar (change f a) (change f b)
change f (Apilar x y a b) = Apilar x y (change f a) (change f b)
change f (Rot45 a) = Rot45 (change f a)
change f (Rotar a) = Rotar (change f a)
change f (Juntar x y a b) = Juntar x y (change f a) (change f b)
change f (Espejar a)= Espejar (change f a) 

-- Principio de recursión para Dibujos
foldDib ::
  (a -> b) ->
  (b -> b) ->
  (b -> b) ->
  (b -> b) ->
  (Float -> Float -> b -> b -> b) ->
  (Float -> Float -> b -> b -> b) ->
  (b -> b -> b) ->
  Dibujo a ->
  b
foldDib f _ _ _ _ _ _ (Figura a) = f a
foldDib f es r45 r ap j en (Espejar a) = es (foldDib f es r45 r ap j en a)
foldDib f es r45 r ap j en (Rot45 a) = r45 (foldDib f es r45 r ap j en a)
foldDib f es r45 r ap j en (Rotar a) = r (foldDib f es r45 r ap j en a)
foldDib f es r45 r ap j en (Apilar x y a b) = ap x y (foldDib f es r45 r ap j en a) 
                                                     (foldDib f es r45 r ap j en b)
foldDib f es r45 r ap j en (Juntar x y a b) = j x y (foldDib f es r45 r ap j en a) 
                                                    (foldDib f es r45 r ap j en b)
foldDib f es r45 r ap j en (Encimar a b) = en (foldDib f es r45 r ap j en a)
                                              (foldDib f es r45 r ap j en b)
