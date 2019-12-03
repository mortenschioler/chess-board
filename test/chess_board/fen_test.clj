(ns chess-board.fen-test
  (:require [clojure.test :refer :all]
            [chess-board.fen :as fen]
            [chess-board.core :as core]))

(deftest test-read
  (is 
    (= (fen/read core/empty-board fen/empty-board)
       core/empty-board)))
