module Dibujos.Escher 
    (escherConf,
    )
where

import Dibujo (Dibujo, encimar4, apilar, juntar, cuarteto, espejar, encimar, figura, rotar, rot45, r180, r270)
import FloatingPic (Output, half, zero)
import Graphics.Gloss (Picture (Blank), blue, color, line, pictures, red, white, polygon)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Dibujos.Grilla(grilla, interpTuple)
import Interp (Conf (..))
import Dibujos.Feo()

type Escher = Bool

-- Dibujos basicos 
blank:: Dibujo Escher
blank = figura False

fish:: Dibujo Escher
fish = figura True

fish2:: Dibujo Escher
fish2 = espejar (rot45 fish)

fish3:: Dibujo Escher 
fish3 = r270 fish2

-- Dibujo u
dibujoU :: Dibujo Escher -> Dibujo Escher
dibujoU _ = encimar4 fish2

-- Dibujo t
dibujoT :: Dibujo Escher -> Dibujo Escher
dibujoT _ = encimar fish (encimar fish2 fish3)

-- Esquina con nivel de detalle en base a la figura p
esquina :: Int -> Dibujo Escher -> Dibujo Escher
esquina 0 _ = blank
esquina n p = cuarteto (esquina (n-1) p) (lado (n-1) p) (rotar(lado (n-1) p)) (dibujoU p)

-- Lado con nivel de detalle
lado :: Int -> Dibujo Escher -> Dibujo Escher
lado 0 _ = blank
lado n p = cuarteto (lado (n-1) p) (lado (n-1) p) (rotar(dibujoT p)) (dibujoT p)

-- Por suerte no tenemos que poner el tipo!
noneto p q r s t u v w x = grilla [[p, q, r],
                                   [s, t, u],
                                   [v, w, x]]

-- El dibujo de Escher
escher :: Int -> Escher -> Dibujo Escher
escher _ False = blank
escher n True = noneto (esquina n fish) (lado n fish) (r270(esquina n fish)) 
                       (rotar(lado n fish)) (dibujoU fish) (r270 (lado n fish)) 
                       (rotar (esquina n fish)) (r180(lado n fish)) (r180(esquina n fish))


interpEsc :: Output Escher
interpEsc False _ _ _ = Blank
interpEsc True x y w = line $ map (x V.+) [(0,0), y V.+ half w, w, (0,0)]


escherConf :: Conf
escherConf = Conf {
    name = "Escher",
    pic = escher 10 True,
    bas = interpEsc
 }                   