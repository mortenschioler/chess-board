(ns chess-board.fen
  (:require 
    [clojure.string :as str]
    [chess-board.api :as api])
  (:refer-clojure
    :exclude [read read-line])
  (:import (java.lang Character)))

(def empty-board
  "8/8/8/8/8/8/8/8")

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

(defn lexicalize
  "Lexicalize the FEN into a data structure."
  [fen]
  (let [[piece-placement] (str/split #" " fen)]
    {:piece-placement (remove #{\/} piece-placement)}))

(defn read-fen
  [empty-board fen]
  "parse a position from a Forsyth-Edwards Notation"
  (-> empty-board))
