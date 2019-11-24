(ns chess-board.fen
  (:require 
    [clojure.string :as str])
  (:refer-clojure
    :exclude [read read-line])
  (:import (java.lang Character)))

(def starting-position
  "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")

(defn char-type
  [^Character c]
  (case (Character/getType c)
    1 :uppercase-letter
    2 :lowercase-letter
    9 :letter-number))

(defn as-number
  [c]
  (- (int c) (int \0)))

(defn color
  [c-type]
  ({:uppercase-letter :white
    :lowercase-letter :black}
   c-type))

(defn piece-type
  [c]
    ({\p :pawn \n :knight \b :bishop \r :rook \q :queen \k :king
      \P :pawn \N :knight \B :bishop \R :rook \Q :queen \K :king}
     c))

(defn read-piece
  [c]
  (let [c-type (char-type c)]
    (cond
      (#{:letter-number} c-type) (repeat (as-number c) nil)
      (#{:uppercase-letter :lowercase-letter} c-type) [{:type (piece-type c) :color (color c-type)}])))

(defn read-line
  [fen-line]
  (->> fen-line
       (mapcat read-piece)
       (vec)))

(defn read
  "Convert a FEN string to a board"
  [fen]
  (->> (str/split fen #"/")
       (mapv read-line)))
