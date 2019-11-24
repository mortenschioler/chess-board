(ns chess-board.pprint)

(defn piece->char
  "This function currently assumes that it prints to a black background,
  which means that the unicode characters use inverted piece colors."
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
