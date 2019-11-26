(ns chess-board.core
  (:require [clojure.string :as str]
            [chess-board.squares :refer :all]))

(defn empty-square
  [color]
  {:square-color color
   :piece nil})

(def empty-board
  (map
    (fn [i]
      (empty-square 
        (if (even? (+ i (quot i 8)))
          :light
          :dark)))
    (range 64)))

(def starting-postion empty-board)

(defn place-piece
  [board square piece]
  (assoc-in board [square :piece] piece))

(defn remove-piece
  [board square]
  (assoc-in board [square :piece] nil))

(defn make-move
  "Move piece, or return nil if the move is invalid."
  [board square-from square-to]
  (when-let [moved-piece (get board square-from)]
    (-> board
        (remove-piece square-from)
        (place-piece square-to moved-piece))))
