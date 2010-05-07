(ns string-fn.core
  (:refer-clojure :exclude [fn]))

(defmacro fn [& sigs]
  `(with-meta (clojure.core/fn ~@sigs)
     {:type ::serializable-fn
      ::source (quote (~'fn ~@sigs))}))

(defmethod print-method ::serializable-fn [o ^Writer w]
  (print-method (::source (meta o)) w))
