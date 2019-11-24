(ns chess-board.core-test
  (:require [clojure.test :refer :all]
            [chess-board.core :refer :all]))

(deftest test-square
  (is (= (get-in starting-position (square :e1))
         {:type :king :color :white}))

  (is (= (get-in starting-position (square :e4))
         nil))
  (is (= (get-in starting-position (square :a2))
         {:type :pawn :color :white}))
  (is (= (get-in starting-position (square :h7))
         {:type :pawn :color :black})))
