# chess-board
Library for representation of a physical chess board and pieces with no game rules implied.

## Example
```clojure
  (require '[chess-board.core :refer [board starting-position square move]])
  (require '[chess-board.pprint :as pp])
  (pp/pprint (move starting-position (square :e2) (square :e4)))
```

## Todo
 - Add structural utilities, e.g. getting row, file, diagonal, and knight-move representations. Note that this is arguably at odds with the intention of making the library game rule-agnostic.
 - Add input validation on movement.

## License

Copyright © 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
