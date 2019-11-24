(ns chess-board.core
  (:require [clojure.string :as str]))

(def starting-position
  [
   [{:color :black :type :rook} {:color :black :type :knight} {:color :black :type :bishop} {:color :black :type :queen} {:color :black :type :king} {:color :black :type :bishop} {:color :black :type :knight} {:color :black :type :rook}]
   [{:color :black :type :pawn} {:color :black :type :pawn} {:color :black :type :pawn} {:color :black :type :pawn} {:color :black :type :pawn} {:color :black :type :pawn} {:color :black :type :pawn} {:color :black :type :pawn}]
   [nil nil nil nil nil nil nil nil]
   [nil nil nil nil nil nil nil nil]
   [nil nil nil nil nil nil nil nil]
   [nil nil nil nil nil nil nil nil]
   [{:color :white :type :pawn} {:color :white :type :pawn} {:color :white :type :pawn} {:color :white :type :pawn} {:color :white :type :pawn} {:color :white :type :pawn} {:color :white :type :pawn} {:color :white :type :pawn}]
   [{:color :white :type :rook} {:color :white :type :knight} {:color :white :type :bishop} {:color :white :type :queen} {:color :white :type :king} {:color :white :type :bishop} {:color :white :type :knight} {:color :white :type :rook}]
  ])

(defn square
  "Return the address for the square named by the descriptor."
  [descriptor]
  (let [[file rank] (name descriptor)]
    [(- (int \8) (int rank)) (- (int file) (int \a))]))

(defn make-move
  "Move piece, or return nil if the move is invalid."
  [board square-from square-to]
  (when-let [moved-piece (get-in board square-from)]
    (-> board
        (assoc-in square-from nil)
        (assoc-in square-to moved-piece))))
