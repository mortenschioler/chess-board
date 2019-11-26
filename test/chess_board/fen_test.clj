(ns chess-board.fen-test
  (:require [clojure.test :refer :all]
            [chess-board.fen :as fen]
            [chess-board.core :as core]))

(deftest test-read
  (is 
    (= (fen/read fen/empty-board)
       core/empty-board)
    (= (fen/read fen/starting-position)
         core/starting-position)))
