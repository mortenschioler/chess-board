(ns chess-board.core)

(defn- square-locator
  [square]
  [:squares square :piece])

(defn place-piece
  [board square piece]
  (assoc-in board (square-locator square) piece))

(defn remove-piece
  [board square]
  (assoc-in board (square-locator square) nil))

(defn get-piece
  [board square]
  (get-in board (square-locator square)))

(defn move
  [board square-from square-to]
  (-> board
      (remove-piece square-from)
      (place-piece square-to (get-piece board square-from))))

(defn square-color
  [square]
  (if (even? (+ square (quot square 8)))
          :light
          :dark))

(def empty-board
  {:squares (mapv (fn [square] {:piece nil :square-color (square-color square) :key square}) (range 64))})


