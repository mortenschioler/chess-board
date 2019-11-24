(ns chess-board.pprint)

(defn piece->char
  "This function currently assumes that we print to a black background,
  which means that we actually swap white and black unicode characters."
  [piece]
  (if piece
    (let [base (case (:color piece)
                 :white 9818
                 :black 9812)
          offset (.indexOf [:king :queen :rook :bishop :knight :pawn] (:type piece))]
      (char (+ base offset)))
    \space))

(defn pprint
  [board]
  (doseq [file board]
    (apply println (map piece->char file))))
