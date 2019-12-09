(ns chess-board.core-test
  (:require 
    [clojure.test :refer :all]
    [chess-board.squares :refer :all]
    [chess-board.core :refer :all]))

(deftest offset-test
  (is (= b1
         (offset a1 {:east 1})))
  (is (= f3
         (offset g1 {:north 2 :west 1})))
  (is (= nil
         (offset a8 {:northwest 1}))))
