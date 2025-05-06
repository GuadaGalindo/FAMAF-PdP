module Interp
  ( Conf(..),
    interp,
    initial,
  )
where

import Dibujo
import FloatingPic
import Graphics.Gloss (Display (InWindow), color, display, makeColorI, pictures, translate, white, Picture)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

-- Dada una computación que construye una configuración, mostramos por
-- pantalla la figura de la misma de acuerdo a la interpretación para
-- las figuras básicas. Permitimos una computación para poder leer
-- archivos, tomar argumentos, etc.
initial :: Conf -> Float -> IO ()
initial (Conf n dib intBas) size = display win white $ withGrid fig size
  where
    win = InWindow n (ceiling size, ceiling size) (0, 0)
    fig = interp intBas dib (0, 0) (size, 0) (0, size)
    desp = -(size / 2)
    withGrid p x = translate desp desp $ pictures [p, color grey $ grid (ceiling $ size / 10) (0, 0) x 10]
    grey = makeColorI 100 100 100 100

-- Interpretación de (^^^) (Encimar)
-- ov(f,g)(d, w, h) = f(d, w, h) ∪ g(d, w, h)
ov :: Picture -> Picture -> Picture
ov p q = pictures [p, q] 

-- r45(f)(d w h) = f(d+(w + h)/2, (w+h)/2, (h-w)/2)
r45 :: FloatingPic -> FloatingPic
r45 f d w h = f (d V.+half(w V.+h)) (half (w V.+h)) (half (h V.-w)) 

-- rot(f)(d w h) = f(d+w,h,-w) 
rot :: FloatingPic -> FloatingPic
rot f d w h = f (d V.+ w) h (V.negate w)

-- esp(f)(d, w, h) = f(d+w, -w, h)
esp :: FloatingPic -> FloatingPic
esp f d w h = f (d V.+ w) (V.negate w) h

-- sup(f, g)(d, w, h) = f(d, w, h) u g(d, w, h)
sup :: FloatingPic -> FloatingPic -> FloatingPic
sup f1 f2 d w h = pictures [f1 d w h, f2 d w h]

-- jun(m, n, f, g)(d, w, h) = f(d, w', h) ∪ g(d+w', r'*w, h) con r'=n/(m+n), r=m/(m+n), w'=r*w
jun :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
jun x y f1 f2 d w h = pictures [f1 d w' h , f2 (d V.+w') (r' V.*w) h]
                  where
                     r'= y/(x+y) 
                     r = x/(x+y)    
                     w'= r V.*w

-- api(m, n, f, g)(d, w, h) = f(d + h', w, r*h) ∪ g(d, w, h') con r'=n/(m+n), r=m/(m+n), h'=r'*h
api :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
api x y f1 f2 d w h = pictures [f1 (d V.+h') w (r V.*h), f2 d w h']
                  where 
                     r'= y/(x+y) 
                     r = x/(x+y)
                     h'= r' V.*h

-- Output a = a -> FLoatingPic
-- Interpreta las funcones de Dibujo.hs con las que hicimos para los vectores
interp :: Output a -> Output (Dibujo a)
interp f = foldDib f esp r45 rot api jun sup
  