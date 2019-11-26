(ns chess-board.api)

(defprotocol ChessBoard
  (place-piece [board square piece])
  (remove-piece [board square])
  (make-move [boarde square-from square-to]))
