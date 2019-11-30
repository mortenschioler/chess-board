(ns chess-board.repl
  (:require 
    [clojure.repl :as repl]
    [chess-board.core :as board]
    [chess-board.squares :refer :all]
    [chess-board.fen :as fen]
    [chess-board.pprint :as pp]))

(def starting-position (fen/read board/empty-board fen/starting-position))

(def new-game-state {:history [starting-position]})

(defonce game (atom new-game-state))

(defn show
  []
  (pp/print-board (last (:history @game))))

(show)

(defn new-game!
  []
  (reset! game new-game-state)
  (show))

(defn change-board!
  [f]
  (swap! game
         (fn [game]
           (-> game
               (update :history conj (f (last (:history game)))))))
  (show))


(defn remove-piece!
  [square]
  (change-board! #(board/remove-piece % square)))

(defn place-piece!
  [square piece]
  (let [piece (cond-> piece
                (char? piece) (fen/char->piece))]
    (change-board! #(board/place-piece % square piece))))

(defn move!
  [from to]
  (change-board! #(board/move % from to)))

(defn take!
  [from to]
  (change-board!
    (fn [board]
      (-> board
          (board/remove-piece to)
          (board/move from to)))))

(defn clear-board!
  []
  (change-board! (constantly board/empty-board)))

(defn set-position!
  [fen]
  (change-board! (constantly (fen/read board/empty-board fen))))

(defn undo!
  []
  (swap! game update :history pop)
  (show))

;; aliases for the lazy typist
(def mv move!)
(def tk take!)
(def u undo!)
(def rm remove-piece!)
(def pl place-piece!)
