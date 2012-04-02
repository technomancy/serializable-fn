# serializable fn

This simple little tweak lets you create functions which show nice
representations when printed rather than just their compiled class.

Which is nicer, be honest:

    ;; raw Clojure behaviour
    user> (fn [x] (inc (inc x)))
    #<user$eval__1750$fn__1751 user$eval__1750$fn__1751@927e4be>

    ;; with serializable-fn
    user> (require '[serializable.fn :as s])

    user> (def dinc (s/fn [x] (inc (inc x))))
    (fn [x] (inc (inc x)))
    user> ((eval (read-string (pr-str dinc))) 0)
    2
    user> ^ niiiiiiiiiiiiice!

## License

Copyright © 2010-2012 Seajure, The Seattle Clojure group and contributors

Distributed under the EPL, the same license as Clojure.
