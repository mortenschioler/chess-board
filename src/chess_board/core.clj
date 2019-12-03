(ns chess-board.core)

(defn- piece-locator
  [square]
  [:squares square :piece])

(defn get-piece
  [board square]
  (get-in board (piece-locator square)))

(defn place-piece
  [board square piece]
  (when-let [occupant (get-piece board square)]
    (throw (ex-info "Cannot place a piece on an occupied square." {:reason :square-occupied :board board :square square})))
  (assoc-in board (piece-locator square) piece))

(defn remove-piece
  [board square]
  (when-not (get-piece board square)
    (throw (ex-info "Cannot remove a piece from an empty square." {:reason :no-piece-to-move :board board :square square})))
  (assoc-in board (piece-locator square) nil))

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

(defn square?
  [?square]
  (<= 0 ?square 64))

(def empty-board
  {:squares (mapv (fn [square] {:piece nil :square-color (square-color square) :key square}) (range 64))})


