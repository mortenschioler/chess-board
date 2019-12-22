(ns chess-board.squares)

(defmacro ^:private defsquares
  []
  `(do 
     ~@(map
         (fn [square-name]
           (list 'def (symbol square-name) (keyword square-name)))
         (for [file [\a \b \c \d \e \f \g \h] rank [1 2 3 4 5 6 7 8]] (str file rank)))))

(defsquares)
