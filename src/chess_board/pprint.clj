(ns chess-board.pprint
  (:require [chess-board.core :as core]))

(defn piece->char
  [piece]
  (if piece
    (let [utf8-base 0x265a
          utf8-offset (.indexOf [:king :queen :rook :bishop :knight :pawn] (:type piece))]
      (str (char (+ utf8-base utf8-offset))))
    " "))

(defn ansi-escape-square-color
  [color]
  (case color
    :light "\u001b[48;5;242m"
    :dark  "\u001b[48;5;239m"))

(def ansi-escape-reset "\u001b[0m")

(defn ansi-escape-piece-color
  [color]
  ({:white "\u001b[37m"
    :black "\u001b[30m"}
   color))

(defn print-square
  [square]
  (print (str 
         (ansi-escape-square-color (:color square)) 
         (when (:piece square)
           (ansi-escape-piece-color (get-in square [:piece :color])))
         (if (:piece square)
           (piece->char (:piece square))
           " ")
         " "
         ansi-escape-reset)))

(defn assoc-square-color
  [board [rank file]]
  (assoc-in board [rank file :color]
    (if (even? (+ rank file))
      :light
      :dark)))

(defn assoc-piece
  [board [rank file]]
  (assoc-in board [rank file :piece] (get-in board [rank file])))

(defn pprint
  [board]
  (doseq [rank (-> board
                   (core/reduce-indexed assoc-piece)
                   (core/reduce-indexed assoc-square-color))]
    (doseq [square rank]
      (print-square square))
    (println)))
