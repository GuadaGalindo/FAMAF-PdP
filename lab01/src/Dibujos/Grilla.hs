module Dibujos.Grilla (
    interpTuple,
    grilla,
    grillaConf,
) where

import Dibujo (Dibujo, figura, juntar, apilar, rot45, rotar, encimar, espejar)
import FloatingPic(Conf(..), Output, half, zero)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Graphics.Gloss (Picture, blue, red, white, color, line, pictures, rectangleWire, translate, text, scale)

type Tuple = (Int, Int)
 
makeTuplesCol :: Int -> [[Dibujo Tuple]]
makeTuplesCol n = map makeTuplesRow [0,1..n]
  where 
      makeTuplesRow x = map (figura . (\y -> (x, y))) [0,1..n] 

makeGrilla :: Dibujo Tuple
makeGrilla = grilla (makeTuplesCol 7)

-- Desplaza el punto de referencia para dibujar la imagen a las coordenadas (x+40, y+40)
interpTuple :: Show a => a -> (Float, Float) -> p1 -> p2 -> Picture
interpTuple tuple (x, y) _ _ = translate (x+40) (y+40) $ scale 0.1 0.1 (text (show tuple))

-- Funciones auxiliares para crear la grilla
row :: [Dibujo a] -> Dibujo a
row [] = error "row: no puede ser vacío"
row [d] = d
row (d:ds) = juntar 1 (fromIntegral $ length ds) d (row ds)

column :: [Dibujo a] -> Dibujo a
column [] = error "column: no puede ser vacío"
column [d] = d
column (d:ds) = apilar 1 (fromIntegral $ length ds) d (column ds) --mal hecho 

grilla :: [[Dibujo a]] -> Dibujo a
grilla = column . map row

grillaConf :: Conf
grillaConf = Conf {
    name = "Grilla",
    pic = makeGrilla,
    bas = interpTuple
 }