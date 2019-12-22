(ns chess-board.pprint
  (:require [chess-board.square-orderings :as orderings]
            [chess-board.core :as core]))

(defn piece->char
  [piece]
  (if piece
    (let [utf8-base 0x265a
          utf8-offset (.indexOf [:king :queen :rook :bishop :knight :pawn] (:piece-type piece))]
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
         (ansi-escape-square-color (:square-color square)) 
         (when (:piece square)
           (ansi-escape-piece-color (get-in square [:piece :piece-color])))
         (if (:piece square)
           (piece->char (:piece square))
           " ")
         " "
         ansi-escape-reset)))

(defn print-board
  [board]
  (when board
    (doall
      (->> orderings/visual
           (map (partial core/get-square board))
           (map (fn [{:keys [file-index rank-index] :as square}]
                  (when (= 0 file-index) 
                    (print (inc rank-index)))
                  (print-square square)
                  (when (= 7 file-index) 
                    (println))))))
    (println " a b c d e f g h")))
