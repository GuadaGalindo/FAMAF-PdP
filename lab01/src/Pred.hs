module Pred (
  Pred,
  cambiar, anyDib, allDib, orP, andP
) where

import Dibujo

type Pred a = a -> Bool

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por la figura básica indicada por el segundo argumento
cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar pred_f f = change g
                   where
                    g b = if pred_f b then f b else Figura b

-- Alguna básica satisface el predicado
anyDib :: Pred a -> Dibujo a -> Bool
anyDib pred_f = foldDib pred_f id id id (\ _ _ a b -> a||b) (\ _ _ a b -> a||b)  (||)

-- Todas las básicas satisfacen el predicado
allDib :: Pred a -> Dibujo a -> Bool
allDib pred_f = foldDib pred_f id id id (\ _ _ a b -> a&&b) (\ _ _ a b -> a&&b) (&&) 

-- Los dos predicados se cumplen para el elemento recibido
andP :: Pred a -> Pred a -> Pred a
andP pred_f1 pred_f2 a = pred_f1 a && pred_f2 a

-- Algún predicado se cumple para el elemento recibido
orP :: Pred a -> Pred a -> Pred a
orP pred_f1 pred_f2 a = pred_f1 a || pred_f2 a




