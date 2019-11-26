(ns chess-board.squares)

(defmacro ^:private defsquares
  []
  `(do 
     ~@(map-indexed
         (fn [i [file rank]]
           (list 'def (symbol (str file rank)) i))
         (for [rank [8 7 6 5 4 3 2 1] file [\a \b \c \d \e \f \g \h]] [file rank]))))

(defsquares)
