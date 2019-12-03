(ns chess-board.fen
  (:require 
    [clojure.string :as str]
    [chess-board.core :as chess-board])
  (:refer-clojure
    :exclude [read read-line])
  (:import (java.lang Character)))

(def starting-position
  "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")

(def empty-board
  "8/8/8/8/8/8/8/8")

(defn as-number
  [^Character c]
  (- (int c) (int \0)))

(defn character-type
  [^Character c]
  (case (Character/getType c)
    (1 2) :piece
    9 :number
    24 :forward-slash))

(defn piece-type
  [^Character c]
  (case (Character/toLowerCase c)
    \k :king
    \q :queen
    \r :rook
    \b :bishop
    \n :knight
    \p :pawn))

(defn piece-color
  [^Character c]
  (case (Character/getType c)
    1 :white
    2 :black))

(defn char->piece
  [^Character c]
  {:piece-type (piece-type c)
   :piece-color (piece-color c)})

(defn read-square
  [[file-char rank-char]]
  (+ (* 8 ())
     (- 1 (as-number rank-char))))

(defn read
  [empty-board fen]
  "parse a position from a Forsyth-Edwards Notation"
  (let [[piece-positions] (str/split fen #" ")]
    (loop [board empty-board
           unplaced-pieces fen
           square 0]
      (let [[c & remaining] unplaced-pieces]
      (if c
        (case (character-type c)
          :piece (recur (chess-board/place-piece board square (char->piece c)) remaining (inc square))
          :number (recur board remaining (+ square (as-number c)))
          :forward-slash (recur board remaining square))
        board)))))

