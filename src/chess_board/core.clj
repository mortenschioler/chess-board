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

(def squares (range 64))

(def empty-board
  {:squares (mapv (fn [square] {:square-color (square-color square) :id square}) squares)})

(def square?
  (set squares))

(def compass
  {:north [0 -1]
   :northeast [1 -1]
   :east [1 0]
   :southeast [1 1]
   :south [0 1]
   :southwest [-1 1]
   :west [-1 0]
   :northwest [-1 -1]})

(def directions
  {:all (set (keys compass))
   :lateral #{:north :east :south :west}
   :diagonal #{:northeast :southeast :southwest :northwest}})

(defn file-index
  [square]
  (when square 
    (mod square 8)))

(defn rank-index
  [square]
  (when square 
    (quot square 8)))

(def coords (juxt file-index rank-index))

(def square->keyword
  [:a8 :b8 :c8 :d8 :e8 :f8 :g8 :h8
   :a7 :b7 :c7 :d7 :e7 :f7 :g7 :h7
   :a6 :b6 :c6 :d6 :e6 :f6 :g6 :h6
   :a5 :b5 :c5 :d5 :e5 :f5 :g5 :h5
   :a4 :b4 :c4 :d4 :e4 :f4 :g4 :h4
   :a3 :b3 :c3 :d3 :e3 :f3 :g3 :h3
   :a2 :b2 :c2 :d2 :e2 :f2 :g2 :h2
   :a1 :b1 :c1 :d1 :e1 :f1 :g1 :h1])

(def coords->square 
  (reduce
    (fn [acc s]
      (assoc acc (coords s) s))
    {}
    squares))

(defn- bearing->delta
  [bearing]
  (reduce-kv
    (fn [d direction distance]
      (map + d (map (partial * distance) (compass direction))))
    [0 0]
    (select-keys bearing (:all directions))))

(defn offset
  "Take a square and a bearing, and return the target square, or nil if not on the board.
  A bearing is a map of directions to distances representing the vector sum of 
  the distances traveled in each direction."
  [square bearing]
  (when square
    (let [origin (coords square)
          delta (bearing->delta bearing)]
      (coords->square (mapv + origin delta)))))
