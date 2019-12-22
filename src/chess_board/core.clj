(ns chess-board.core)

(defn get-square
  [board square-id]
  (get-in board [:squares square-id]))

(defn- piece-locator
  [square-id]
  [:squares square-id :piece])

(defn get-piece
  [board square-id]
  (get-in board (piece-locator square-id)))

(defn place-piece
  [board square-id piece]
  (when-let [occupant (get-piece board square-id)]
    (throw (ex-info "Cannot place a piece on an occupied square." {:reason :square-occupied :board board :square square-id})))
  (assoc-in board (piece-locator square-id) piece))

(defn remove-piece
  [board square-id]
  (when-not (get-piece board square-id)
    (throw (ex-info "Cannot remove a piece from an empty square." {:reason :no-piece-to-move :board board :square square-id})))
  (update-in board [:squares square-id] dissoc :piece))

(defn move
  [board square-from square-to]
  (-> board
      (remove-piece square-from)
      (place-piece square-to (get-piece board square-from))))

(def ^{:private true} squares-by-file-x-rank 
  [[:a1 :a2 :a3 :a4 :a5 :a6 :a7 :a8]
   [:b1 :b2 :b3 :b4 :b5 :b6 :b7 :b8]
   [:c1 :c2 :c3 :c4 :c5 :c6 :c7 :c8]
   [:d1 :d2 :d3 :d4 :d5 :d6 :d7 :d8]
   [:e1 :e2 :e3 :e4 :e5 :e6 :e7 :e8]
   [:f1 :f2 :f3 :f4 :f5 :f6 :f7 :f8]
   [:g1 :g2 :g3 :g4 :g5 :g6 :g7 :g8]
   [:h1 :h2 :h3 :h4 :h5 :h6 :h7 :h8]])

(defn- coords->square
  [[file-index rank-index]]
  (let [id (get-in squares-by-file-x-rank [file-index rank-index])
        color (if (even? (+ file-index rank-index))
                :dark
                :light)]
    {:id id
     :file-index file-index
     :rank-index rank-index
     :square-color color}))

(def empty-board
  {:squares (->> (for [file-index (range 8) rank-index (range 8)] [file-index rank-index])
                 (reduce (fn [acc coords] (let [s (coords->square coords)] (assoc acc (:id s) s))) {}))})
(def squares
  (set (flatten squares-by-file-x-rank)))

(def square? squares)

(def compass
  {:north [0 1]
   :northeast [1 1]
   :east [1 0]
   :southeast [1 -1]
   :south [0 -1]
   :southwest [-1 -1]
   :west [-1 0]
   :northwest [-1 1]})

(def directions
  {:all (set (keys compass))
   :lateral #{:north :east :south :west}
   :diagonal #{:northeast :southeast :southwest :northwest}})

(defn file-index
  [square-id]
  (:file-index (get-square empty-board square-id)))

(defn rank-index
  [square-id]
  (:rank-index (get-square empty-board square-id)))

(def coords (juxt file-index rank-index))

(defn- bearing->delta
  [bearing]
  (reduce-kv
    (fn [d direction distance]
      (map + d (map (partial * distance) (compass direction))))
    [0 0]
    (select-keys bearing (:all directions))))

(defn offset
  "Take a square ID and a bearing, and return the target square, or nil if not on the board.
  A bearing is a map of directions to distances representing the vector sum of 
  the distances traveled in each direction."
  [square-id bearing]
  (when square-id
    (let [origin (coords square-id)
          delta (bearing->delta bearing)]
      (get-in squares-by-file-x-rank (mapv + origin delta)))))
