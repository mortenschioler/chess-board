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

(def empty-board
  {:squares (mapv (fn [square] {:piece nil :square-color (square-color square) :key square}) (range 64))})

(def square?
  (set (range 64)))

(def compass
  {:north -8
   :northeast -7
   :east 1
   :southeast 9
   :south 8
   :southwest 7
   :west -1
   :northwest -9})

(def directions
  {:all (set (keys compass))
   :lateral #{:north :east :south :west}
   :diagonal #{:northeast :southeast :southwest :northwest}})

(defn offset
  "Take a square and a bearing, and return the target square, or nil if not on the board.
  A bearing is a map of directions to distances representing the vector sum of 
  the distances traveled in each direction."
  [origin-square bearing]
  (square? 
    (reduce-kv
      (fn [target-square direction distance]
        (+ target-square (* (direction compass) distance)))
      origin-square
      (select-keys bearing (:all directions)))))
