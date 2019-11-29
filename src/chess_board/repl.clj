(ns chess-board.repl
  (:require 
    [clojure.repl :as repl]
    [chess-board.core :refer :all]
    [chess-board.squares :refer :all]
    [chess-board.fen :as fen]
    [chess-board.pprint :as pp]))

(def start-pos (fen/read empty-board fen/starting-position))
