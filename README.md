# serializable fn

This simple little tweak lets you create functions which show nice
representations when printed rather than just their compiled class.

Which is nicer, be honest:

    ;; raw Clojure behaviour
    user> (fn [x] (inc (inc x)))
    #<user$eval__1750$fn__1751 user$eval__1750$fn__1751@927e4be>

    ;; with serializable-fn
    user> (use 'serializable.fn)
    ;; (:refer-clojure :exclude [fn]) to avoid the warning
    WARNING: fn already refers to: #'clojure.core/fn in namespace: user, being replaced by: #'serializable.fn/fn
    nil

    user> (def dinc (fn [x] (inc (inc x))))
    (fn [x] (inc (inc x)))
    user> ((eval (read-string (pr-str dinc))) 0)
    2
    user> ^ niiiiiiiiiiiiice!

## License

(c) 2010 Seajure, The Seattle Clojure group

Distributed under the EPL, the same license as Clojure.
